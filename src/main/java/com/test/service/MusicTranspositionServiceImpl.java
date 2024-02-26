package com.test.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service("musicTranspositionServiceImpl")
@Service
public class MusicTranspositionServiceImpl implements MusicTranspositionService  {

	 public List<int[]> transpose(MultipartFile file, int semitones) throws IOException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        List<int[]> notes = objectMapper.readValue(file.getBytes(), new TypeReference<List<int[]>>() {});
	        List<int[]> transposedNotes = new ArrayList<>();	
	        for (int[] note : notes) {
	        		int octave = note[0];
	        	    int noteNumber = note[1];
	        	    
					int newNoteNumber = noteNumber + semitones;
					int newOctave = octave;
					if (newNoteNumber < 0) {
						newNoteNumber = 12 + newNoteNumber;
						newOctave = octave - 1;

					} else if (newNoteNumber > 12) {
						newNoteNumber = newNoteNumber - 12;
						newOctave = octave + 1;
					} else if (newNoteNumber == 0) {
						newNoteNumber = 12;
						newOctave = octave - 1;
					}
					// Applying conditions valid range (-3, 10) to (5, 1)

					if (newOctave < -3 || newOctave > 5 || (newOctave == -3 && newNoteNumber < 10)
							|| (newOctave == 5 && newNoteNumber > 1)) {
						throw new IllegalArgumentException("Transposed note is out of the piano's range. For Input ["
								+ octave + "," + noteNumber + "]");
					}

					int[] transposedNote = new int[] { newOctave, newNoteNumber };
					transposedNotes.add(transposedNote);
	        }
	        return transposedNotes;
	    }

}