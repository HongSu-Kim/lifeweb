package com.bethefirst.lifeweb.service.member.interfaces;

import com.bethefirst.lifeweb.dto.member.request.SnsCreateDto;
import com.bethefirst.lifeweb.dto.member.request.SnsUpdateDto;

public interface SnsService {

    /** SNS 등록 */
    void createSns(SnsCreateDto snsCreateDto);

    /** SNS 수정 */
    void updateSns(SnsUpdateDto snsUpdateDto, Long snsId);

    /** SNS 삭제 */

    /** SNS 단건조회 */

    /** SNS 전체조회 */
}
