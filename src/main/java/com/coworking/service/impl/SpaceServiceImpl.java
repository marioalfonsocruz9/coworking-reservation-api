package com.coworking.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coworking.dto.space.SpaceRequest;
import com.coworking.dto.space.SpaceResponse;
import com.coworking.exception.BusinessException;
import com.coworking.exception.ResourceNotFoundException;
import com.coworking.mapper.SpaceMapper;
import com.coworking.model.Space;
import com.coworking.repository.SpaceRepository;
import com.coworking.service.SpaceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceServiceImpl implements SpaceService {

	private final SpaceRepository spaceRepository;

	private final SpaceMapper spaceMapper;

	@Override
	public SpaceResponse create(SpaceRequest request) {
		
		validateUniqueName(request.name());
		
		Space entity = spaceMapper.toEntity(request);
		
		entity = spaceRepository.save(entity);
		
		return spaceMapper.toResponse(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public SpaceResponse findById(Long id) {
		
		return spaceMapper.toResponse(findSpace(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<SpaceResponse> findAll() {

		return spaceRepository.findAll().stream().map(spaceMapper::toResponse).toList();
	}

	@Override
	public SpaceResponse update(Long id, SpaceRequest request) {

	    Space space = findSpace(id);

	    if (!space.getName().equalsIgnoreCase(request.name())) {
	        validateUniqueName(request.name());
	    }

	    spaceMapper.updateEntity(request, space);

	    Space updatedSpace = spaceRepository.save(space);

	    return spaceMapper.toResponse(updatedSpace);
	}

	@Override
	public void delete(Long id) {

		Space entity = findSpace(id);

		spaceRepository.delete(entity);
	}

	private Space findSpace(Long id) {

		return spaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Space", id));
	}
	
	private void validateUniqueName(String name) {

	    if (spaceRepository.existsByName(name)) {
	        throw new BusinessException(
	                "A space with the name '%s' already exists."
	                        .formatted(name));
	    }

	}
	
}
