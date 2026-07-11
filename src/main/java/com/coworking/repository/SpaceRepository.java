package com.coworking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.model.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    boolean existsByName(String name);

    List<Space> findByEnabledTrue();

}
