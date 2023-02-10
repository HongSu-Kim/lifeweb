package com.bethefirst.lifeweb.initDto.application;

import com.bethefirst.lifeweb.dto.application.ApplicationDto;
import com.bethefirst.lifeweb.dto.application.ApplicationSearchRequirements;
import com.bethefirst.lifeweb.dto.application.CreateApplicationDto;
import com.bethefirst.lifeweb.dto.application.UpdateApplicationDto;
import com.bethefirst.lifeweb.dto.member.MemberDto;
import com.bethefirst.lifeweb.entity.member.Role;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitApplicationDto {

	public CreateApplicationDto getCreateApplicationDto() {
		return new CreateApplicationDto(1L, "메모", Arrays.asList(1L, 2L), Arrays.asList("대답1", "대답2"));
	}

	public ApplicationDto getApplicationDto() {
		return getApplicationDtoList().get(0);
	}

	public ApplicationSearchRequirements getSearchRequirements() {
		return ApplicationSearchRequirements.builder()
				.pageable(pageable)
				.build();
	}
	public Page<ApplicationDto> getApplicationDtoPage() {
		return new PageImpl(getSubList(), pageable, getApplicationDtoList().size());
	}

	private List<ApplicationDto> getSubList() {
		if (getApplicationDtoList().size() < getPage() * getSize()) {
			return new ArrayList<>();
		} else if (getApplicationDtoList().size() - getPage() * getSize() < getSize()) {
			return getApplicationDtoList().subList(getPage() * getSize(), getApplicationDtoList().size());
		} else {
			return getApplicationDtoList().subList(getPage() * getSize(), getSize());
		}
	}

	public List<ApplicationDto> getApplicationDtoList() {

		List<ApplicationDto> list = new ArrayList<>();

		for (int i = 0; i < 15; i++) {
			list.add(new ApplicationDto());
		}

		return list;
	}

	public UpdateApplicationDto getUpdateApplicationDto() {
		return new UpdateApplicationDto();
	}

	private Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "id"));

	private int getPage() {
		return pageable.getPageNumber();
	}

	private int getSize() {
		return pageable.getPageSize();
	}

}
