package com.coworking.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coworking.dto.space.SpaceRequest;
import com.coworking.dto.space.SpaceResponse;
import com.coworking.service.SpaceService;
import com.coworking.util.ApiPaths;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPaths.SPACES)
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<SpaceResponse> create(
            @Valid @RequestBody SpaceRequest request) {

        SpaceResponse response = spaceService.create(request);

        return ResponseEntity
        		.created(URI.create(ApiPaths.SPACES + "/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponse> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(spaceService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SpaceResponse>> findAll() {

        return ResponseEntity.ok(spaceService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SpaceRequest request) {

        return ResponseEntity.ok(spaceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        spaceService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
