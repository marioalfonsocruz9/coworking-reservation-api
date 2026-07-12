package com.coworking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.util.ApiPaths;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPaths.SPACES)
@RequiredArgsConstructor
@Validated
public class SpaceController {
	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
	    return ResponseEntity.ok("Authenticated");
	}
	
}
