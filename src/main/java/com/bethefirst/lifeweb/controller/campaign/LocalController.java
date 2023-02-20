package com.bethefirst.lifeweb.controller.campaign;

import com.bethefirst.lifeweb.dto.campaign.request.LocalNameDto;
import com.bethefirst.lifeweb.service.campaign.interfaces.LocalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("locals")
@RequiredArgsConstructor
@Slf4j
public class LocalController {

	private final LocalService localService;

	/** 지역 생성 */
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody LocalNameDto localNameDto) {

		// 지역 생성
		localService.createLocal(localNameDto.getLocalName());

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/locals"));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 지역 리스트 조회 */
	@GetMapping
	public Map<String, ?> readAll() {
		return Map.of("content", localService.getLocalDtoList());
	}

	/** 지역 수정 */
	@PutMapping("/{localId}")
	public ResponseEntity<?> update(@PathVariable Long localId,
									@Valid @RequestBody LocalNameDto localNameDto) {

		// 지역 수정
		localService.updateLocal(localId, localNameDto.getLocalName());

		// Location 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/locals"));

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/** 지역 삭제 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{localId}")
	public void delete(@PathVariable Long localId) {
		localService.deleteLocal(localId);
	}

}
