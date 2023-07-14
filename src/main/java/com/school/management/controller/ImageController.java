package com.school.management.controller;

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@RequestParam("path") String imagePath) throws IOException {

		var imgFile = new ClassPathResource("static" + imagePath);
		byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(bytes);
	}

}
