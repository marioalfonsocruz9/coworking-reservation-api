package com.coworking.service;

import java.util.List;

import com.coworking.dto.space.SpaceRequest;
import com.coworking.dto.space.SpaceResponse;

public interface SpaceService {

    SpaceResponse create(SpaceRequest request);

    SpaceResponse findById(Long id);

    List<SpaceResponse> findAll();

    SpaceResponse update(Long id, SpaceRequest request);

    void delete(Long id);

}