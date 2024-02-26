package com.test.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.service.MusicTranspositionService;

@RestController
@RequestMapping("/api/music")
public class MusicTranspositionController {

	@Autowired
	private MusicTranspositionService musicTranspositionServiceImpl;

	@PostMapping("/transpose")
	public ResponseEntity<?> transposeMusicPiece(@RequestParam("file") MultipartFile file,
			@RequestParam("semitones") int semitones) {
		try {
			List<int[]> transposedNotes = musicTranspositionServiceImpl.transpose(file, semitones);

			ObjectMapper objectMapper = new ObjectMapper();
			File outputFile = new File("output.json");
			objectMapper.writeValue(outputFile, transposedNotes);

			byte[] fileContent = Files.readAllBytes(outputFile.toPath());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", "output.json");

			return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to transpose music piece: " + e.getMessage());
		}
	}
}
