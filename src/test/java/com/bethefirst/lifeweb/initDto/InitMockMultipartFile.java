package com.bethefirst.lifeweb.initDto;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
public class InitMockMultipartFile {

	public MultipartFile getMockMultipartFile() {
		return new MockMultipartFile("uploadFile", "imageFile.jpeg", MediaType.IMAGE_JPEG_VALUE, "<<jpeg data>>".getBytes());
	}

	public List<MultipartFile> getMultipartFileList() {
		return Arrays.asList(
				new MockMultipartFile("uploadFileList", "imageFile1.jpeg", MediaType.IMAGE_JPEG_VALUE, "<<jpeg data>>".getBytes()),
				new MockMultipartFile("uploadFileList", "imageFile2.jpeg", MediaType.IMAGE_JPEG_VALUE, "<<jpeg data>>".getBytes()),
				new MockMultipartFile("uploadFileList", "imageFile3.jpeg", MediaType.IMAGE_JPEG_VALUE, "<<jpeg data>>".getBytes())
		);
	}

}
