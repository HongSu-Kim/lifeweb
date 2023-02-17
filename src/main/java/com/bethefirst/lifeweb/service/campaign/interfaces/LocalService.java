package com.bethefirst.lifeweb.service.campaign.interfaces;

import com.bethefirst.lifeweb.dto.campaign.response.LocalDto;

import java.util.List;

public interface LocalService {

	/** 지역 생성 */
	void createLocal(String localName);

	/** 지역 리스트 조회 */
	List<LocalDto> getLocalDtoList();

	/** 지역 수정 */
	void updateLocal(Long localId, String localName);

	/** 지역 삭제 */
	void deleteLocal(Long localId);

}
