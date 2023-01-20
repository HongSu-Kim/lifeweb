package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.JoinDto;
import com.bethefirst.lifeweb.dto.member.MemberSnsDto;
import com.bethefirst.lifeweb.dto.member.MemberUpdateDto;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.entity.member.MemberSns;
import com.bethefirst.lifeweb.entity.member.Sns;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.repository.member.MemberSnsRepository;
import com.bethefirst.lifeweb.repository.member.SnsRepository;
import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
import com.bethefirst.lifeweb.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


	@Value("${image_folder.member}")
	private String imageFolder = "member";
	private final MemberRepository memberRepository;
	private final SnsRepository snsRepository;
	private final MemberSnsRepository memberSnsRepository;
	private final PasswordEncoder passwordEncoder;
	private final ImageUtil imageUtil;

	/** 회원 가입 */
	@Override
	public void join(JoinDto joinDto) {

		//이메일 유효성 검사
		memberRepository.findByEmail(joinDto.getEmail()).ifPresent(member -> {
			throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
		});

		//회원 Entity 생성
		Member member = Member.createMember(passwordEncoder,joinDto.getEmail(),
				joinDto.getPwd(),
				joinDto.getNickname());

		//DB에 회원 저장
		memberRepository.save(member);

	}

	/** 회원 수정 */
	@Override
	public void updateMemberInfo(MemberUpdateDto memberUpdateDto, Long memberId) {

		//회원 유효성 검사
		Member member = memberRepository.findById(memberId).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

		//DB에 수정 된 회원정보 저장
		member.updateMemberByUpdateDto(memberUpdateDto);


	}

	/** 회원 이미지 수정 */
	@Override
	public void updateMemberImage(MemberUpdateDto memberUpdateDto, Long memberId) {
		//회원 유효성 검사
		Member member = memberRepository.findById(memberId).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

		//파일이 넘어온 경우 파일을 저장후 DB를 업데이트 합니다.
		getSavedFileNameAfterSaveFile(memberUpdateDto).ifPresent(savedFileName ->
				member.updateFileName(savedFileName));

	}

	/** 회원 SNS 수정 */
	@Override
	public void updateMemberSnsList(MemberUpdateDto memberUpdateDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

		updateMemberSnsList(memberUpdateDto, member);
	}


	/** DTO에 파일 이름이 있으면 파일을 저장후 저장된 파일이름을 반환합니다 */
	private Optional<String> getSavedFileNameAfterSaveFile(MemberUpdateDto memberUpdateDto) {
		String storeFileName = null;
		try {
			if(memberUpdateDto.getFileName() != null) {
				storeFileName = imageUtil.store(memberUpdateDto.getFileName(), imageFolder);
			}
		} catch (IOException ex) {
			throw new RuntimeException("이미지 저장에 실패했습니다.");
		}

		return Optional.ofNullable(storeFileName);
	}


	/** 넘어온 snsDto를 확인 후 DB에 수정,삭제,등록합니다. */
	private void updateMemberSnsList(MemberUpdateDto memberUpdateDto, Member member) {
		List<MemberSnsDto> memberSnsDtoList = memberUpdateDto.getMemberSnsDtoList();

		if(memberSnsDtoList.isEmpty()){
			throw new IllegalStateException("잘못된 요청입니다.");
		}

		memberSnsDtoList.forEach(memberSnsDto -> {
			if(memberSnsDto.getId() == 0){
				Sns sns = snsRepository.findByName(memberSnsDto.getSnsName()).orElseThrow(() -> {
					throw new IllegalArgumentException("잘못된 SNS 이름 입니다.");
				});
				MemberSns createMemberSns = MemberSns.createMemberSns(member, sns);
				memberSnsRepository.save(createMemberSns);
			}else{
				member.getMemberSnsList().forEach(memberSns -> {
					if(memberSns.getSns().getName() == memberSnsDto.getSnsName()){
						memberSns.updateMemberSnsByUpdateDto(memberSnsDto.getSnsUrl());
					}
					memberSnsRepository.delete(memberSns);
				});
			}
		});
	}
}
