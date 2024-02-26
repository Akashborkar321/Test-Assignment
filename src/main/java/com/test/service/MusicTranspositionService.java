package com.test.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface MusicTranspositionService {

	
	 List<int[]> transpose(MultipartFile file, int semitones) throws IOException;
}
