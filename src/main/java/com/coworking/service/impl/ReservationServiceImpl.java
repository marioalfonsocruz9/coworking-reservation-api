package com.coworking.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coworking.dto.reservation.ReservationRequest;
import com.coworking.dto.reservation.ReservationResponse;
import com.coworking.enums.ReservationStatus;
import com.coworking.enums.Role;
import com.coworking.event.ReservationCreatedEvent;
import com.coworking.exception.BusinessException;
import com.coworking.exception.OverlappingReservationException;
import com.coworking.exception.ResourceNotFoundException;
import com.coworking.mapper.ReservationMapper;
import com.coworking.model.Reservation;
import com.coworking.model.Space;
import com.coworking.model.User;
import com.coworking.repository.ReservationRepository;
import com.coworking.repository.SpaceRepository;
import com.coworking.service.ReservationService;
import com.coworking.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {
	
	private final ReservationRepository reservationRepository;

	private final SpaceRepository spaceRepository;

	private final UserService userService;

	private final ReservationMapper reservationMapper;
	
	private final ApplicationEventPublisher eventPublisher;
	
	@Override
	public ReservationResponse create(ReservationRequest request) {

	    User user = userService.getCurrentUser();

	    Space space = spaceRepository.findById(request.spaceId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Space", request.spaceId()));

	    validateReservation(request, space);
	    
	    Reservation reservation = Reservation.builder()
	            .user(user)
	            .space(space)
	            .startTime(request.startTime())
	            .endTime(request.endTime())
	            .totalAmount(
	                    calculateAmount(
	                            space,
	                            request.startTime(),
	                            request.endTime()))
	            .status(ReservationStatus.CONFIRMED)
	            .build();

	    Reservation saved =
	            reservationRepository.save(reservation);
	    
	    eventPublisher.publishEvent(
	            new ReservationCreatedEvent(saved));

	    return reservationMapper.toResponse(saved);

	}

	@Override
	@Transactional(readOnly = true)
	public List<ReservationResponse> findMyReservations() {

	    User user = userService.getCurrentUser();

	    return reservationRepository
	            .findByUserIdOrderByStartTimeDesc(user.getId())
	            .stream()
	            .map(reservationMapper::toResponse)
	            .toList();

	}

	@Override
	@Transactional(readOnly = true)
	public List<ReservationResponse> findAll() {

	    return reservationRepository
	            .findAllByOrderByStartTimeDesc()
	            .stream()
	            .map(reservationMapper::toResponse)
	            .toList();

	}

	@Override
	public void cancel(Long reservationId) {

	    User currentUser = userService.getCurrentUser();

	    Reservation reservation = reservationRepository.findById(reservationId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Reservation",
	                            reservationId));

	    boolean admin =
	            currentUser.getRole() == Role.ROLE_ADMIN;

	    boolean owner =  reservation.getUser().getId()
	                    .equals(currentUser.getId());

	    if (!admin && !owner) {

	        throw new BusinessException(
	                "You cannot cancel this reservation.");

	    }

	    reservation.setStatus(ReservationStatus.CANCELLED);

	}
	
	private void validateReservation(
	        ReservationRequest request,
	        Space space) {

	    if (!space.isAvailable()) {

	        throw new BusinessException(
	                "Space is disabled.");

	    }

	    if (!request.endTime()
	            .isAfter(request.startTime())) {

	        throw new BusinessException(
	                "End time must be after start time.");

	    }

	    boolean overlap =
	            reservationRepository
	                    .existsOverlappingReservation(
	                            space.getId(),
	                            request.startTime(),
	                            request.endTime());

	    if (overlap) {

	        throw new OverlappingReservationException();

	    }

	}
	
	private BigDecimal calculateAmount(
	        Space space,
	        LocalDateTime start,
	        LocalDateTime end) {
	    
	    long minutes = ChronoUnit.MINUTES.between(start, end);

	    BigDecimal hours = BigDecimal.valueOf(minutes)
	            .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

	    return space.getHourlyRate().multiply(hours);

	}
	
}
