package com.bethefirst.lifeweb.service.member;

import com.bethefirst.lifeweb.dto.member.request.JoinDto;
import com.bethefirst.lifeweb.dto.member.request.PasswordDto;
import com.bethefirst.lifeweb.dto.member.request.UpdateMemberDto;
import com.bethefirst.lifeweb.entity.member.Member;
import com.bethefirst.lifeweb.repository.member.MemberRepository;
import com.bethefirst.lifeweb.service.member.interfaces.MemberService;
import com.bethefirst.lifeweb.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


	@Value("${image-folder.member}")
	private String imageFolder;
	private final MemberRepository memberRepository;
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
	public void updateMemberInfo(UpdateMemberDto updateMemberDto, Long memberId) {

		//회원 유효성 검사
		Member member = memberRepository.findById(memberId).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

		//DB에 수정 된 회원정보 저장
		updateMemberDto.updateMember(member);


	}

	/** 회원 이미지 수정 */
	@Override
	public void updateMemberImage(MultipartFile memberFileName, Long memberId) {
		//회원 유효성 검사
		Member member = memberRepository.findById(memberId).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

		//파일을 파일저장소에 저장 후 저장된 파일명을 받환 받습니다.
		String storeName = imageUtil.store(memberFileName, imageFolder);

		//기존에 파일저장소에 있던 파일을 삭제합니다.
		if(storeName == null){
			imageUtil.delete(member.getFileName(), imageFolder);
		}

		//DB에 파일 이름을 저장합니다.
		member.updateFileName(storeName);

	}

	/** 회원 비밀번호 변경 */
	@Override
	public void updatePassword(PasswordDto passwordDto, Long memberId) {

		// DTO의 새 비밀번호와 확인용 비밀번호 일치하는지 검사
		if(!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())){
			throw new IllegalArgumentException("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		}

		//회원 유효성 검사
		Member member = memberRepository.findById(memberId).orElseThrow(()
				-> new IllegalArgumentException("존재하지 않는 회원입니다. " + memberId));

		//새로운 비밀번호를 DB에 저장
		passwordDto.updatePassword(passwordEncoder, member);

	}

	/** 회원 탈퇴 */
	@Override
	public void withdraw(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() ->
				new IllegalArgumentException("존재하지 않는 회원입니다."));

		memberRepository.delete(member);
	}

	/** 닉네임 중복 체크 */
	@Override
	public void existsNickname(String nickname) {
		if(memberRepository.existsByNickname(nickname))
			throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
	}

}
