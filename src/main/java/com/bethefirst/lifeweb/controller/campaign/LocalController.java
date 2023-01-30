package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.LocalDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.LocalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locals")
@RequiredArgsConstructor
@Slf4j
public class LocalController {

	private final LocalService localService;

	/** 지역 생성 */
	@PostMapping
	public void create(@Valid @NotEmpty(message = "지역명은 필수 입력 값입니다.") String localName) {
		localService.createLocal(localName);
	}

	/** 지역 리스트 조회 */
	@GetMapping
	public List<LocalDto> list() {
		return localService.getLocalDtoList();
	}

	/** 지역 수정 */
	@PutMapping("/{localId}")
	public void update(@PathVariable Long localId, @Valid @NotEmpty(message = "지역명은 필수 입력 값입니다.") String localName) {
		localService.updateLocal(localId, localName);
	}

	/** 지역 삭제 */
	@DeleteMapping("/{localId}")
	public void delete(@PathVariable Long localId) {
		localService.deleteLocal(localId);
	}

}
