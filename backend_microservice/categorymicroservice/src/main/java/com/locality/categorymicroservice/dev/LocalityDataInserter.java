package com.locality.categorymicroservice.dev;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.locality.categorymicroservice.entity.Locality;
import com.locality.categorymicroservice.repository.LocalityRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/localityutil")
@Transactional
public class LocalityDataInserter {

	@Autowired
	private LocalityRepository localityRepository;

	@PostMapping("/")
	public ResponseEntity<?> saveLocalities() throws RuntimeException {

		this.localityRepository.resetAutoIncrement();

		List<Locality> listOfLocalities = Arrays.asList(Locality.buildLocality(1L, "Locality-A", "voluptatibus",
				"laudantium", 2,
				"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."),
				Locality.buildLocality(2L, "Locality-B", "Lorem", "ipsum", 1,
						"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."),
				Locality.buildLocality(3L, "Locality-C", "dolor", "laudantium", 4,
						"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."),
				Locality.buildLocality(4L, "Locality-D", "sit", "laudantium", 4,
						"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."),
				Locality.buildLocality(5L, "Locality-E", "elit", "consectetur", 3,
						"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."),
				Locality.buildLocality(6L, "Locality-F", "Sequi", "laudantium", 2,
						"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."),
				Locality.buildLocality(7L, "Locality-G", "illum", "consectetur", 1,
						"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."),
				Locality.buildLocality(8L, "Locality-H", "illum", "laudantium", 3,
						"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sequi voluptates illum a repellendus nobis voluptatibus in laudantium ipsa consequuntur laboriosam."));

		try {
			return new ResponseEntity<>(this.localityRepository.saveAll(listOfLocalities), HttpStatus.CREATED);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
