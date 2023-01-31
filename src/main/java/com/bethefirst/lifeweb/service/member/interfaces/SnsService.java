package com.bethefirst.lifeweb.service.member.interfaces;

import com.bethefirst.lifeweb.dto.member.SnsDto;
import com.bethefirst.lifeweb.dto.member.request.SnsCreateDto;
import com.bethefirst.lifeweb.dto.member.request.SnsUpdateDto;

public interface SnsService {

    /** SNS 등록 */
    void createSns(SnsCreateDto snsCreateDto);

    /** SNS 수정 */
    void updateSns(SnsUpdateDto snsUpdateDto, Long snsId);

    /** SNS 삭제 */
    void deleteSns(Long snsId);

    /** SNS 단건조회 */
    SnsDto getSns(Long snsId);

    /** SNS 전체조회 */
}
