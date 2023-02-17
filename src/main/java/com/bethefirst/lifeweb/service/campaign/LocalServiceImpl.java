package com.bethefirst.lifeweb.service.campaign;

import com.bethefirst.lifeweb.dto.campaign.response.LocalDto;
import com.bethefirst.lifeweb.entity.campaign.Local;
import com.bethefirst.lifeweb.repository.campaign.LocalRepository;
import com.bethefirst.lifeweb.service.campaign.interfaces.LocalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LocalServiceImpl implements LocalService {

	private final LocalRepository localRepository;

	/** 지역 생성 */
	@Override
	public void createLocal(String localName) {
		localRepository.save(Local.createLocal(localName));
	}

	/** 지역 리스트 조회 */
	@Transactional(readOnly = true)
	@Override
	public List<LocalDto> getLocalDtoList() {
		return localRepository.findAll().stream().map(LocalDto::new).collect(Collectors.toList());
	}

	/** 지역 수정 */
	@Override
	public void updateLocal(Long localId, String localName) {
		localRepository.findById(localId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다. " + localId))
				.updateLocal(localName);
	}

	/** 지역 삭제 */
	@Override
	public void deleteLocal(Long localId) {
		localRepository.deleteById(localId);
	}

}
