package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.JoinDto;
import com.bethefirst.lifeweb.dto.member.MemberUpdateDto;
import com.bethefirst.lifeweb.entity.member.Member;
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
	public void update(MemberUpdateDto memberUpdateDto, Long memberId) {

		//회원 유효성 검사
		Member member = memberRepository.findById(memberId).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

		//DB에 수정 된 회원정보 저장
		member.updateMemberByUpdateDto(memberUpdateDto);


	}



//	private void updateMemberAfterSavingFile(MemberUpdateDto memberUpdateDto, Member member) {
//		try {
//			String storeFileName = null;
//			if(memberUpdateDto.getFileName() != null) {
//				storeFileName = imageUtil.store(memberUpdateDto.getFileName(), imageFolder);
//			}
//			member.updateMemberByUpdateDto(memberUpdateDto,storeFileName);
//		} catch (IOException ex) {
//			throw new RuntimeException("이미지 저장에 실패했습니다.");
//		}
//	}



//	private void updateMemberSnsList(MemberUpdateDto memberUpdateDto, Member member) {
//
//		if(!CollectionUtils.isEmpty(member.getMemberSnsList())){
//
//			if(!CollectionUtils.isEmpty(memberUpdateDto.getMemberSnsDtoList())) {
//				memberUpdateDto.getMemberSnsDtoList().forEach(memberSnsDto -> {
//					member.getMemberSnsList()
//							.stream()
//							.filter(memberSns
//									-> memberSns.getSns().getName().equals(memberSnsDto.getSnsName()))
//							.forEach(memberSns
//									-> memberSns.updateMemberSnsByUpdateDto(memberSnsDto.getSnsUrl()));
//				});
//			}else{
//				memberUpdateDto.getMemberSnsDtoList().forEach(memberSnsDto -> {
//					member.getMemberSnsList()
//							.stream()
//							.filter(memberSns ->
//						memberSns.getSns().getName().equals(memberSnsDto.getSnsName())
//					).forEach(memberSnsRepository::delete);
//				});
//
//			}
//		}else{
//			memberUpdateDto.getMemberSnsDtoList().forEach(memberSnsDto -> {
//
//				Sns findSns = snsRepository.findByName(memberSnsDto.getSnsName()).orElseThrow(() -> {
//					throw new IllegalArgumentException("잘못된 SNS 이름 입니다.");
//				});
//				MemberSns memberSns = MemberSns.createMemberSns(member, findSns);
//				memberSnsRepository.save(memberSns);
//			});
//		}
//	}
}
