package com.coworking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coworking.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findBySpaceId(Long spaceId);
    
    @Query("""
    		SELECT COUNT(r) > 0
    		FROM Reservation r
    		WHERE r.space.id = :spaceId
    		  AND r.status <> com.coworking.enums.ReservationStatus.CANCELLED
    		  AND r.startTime < :endTime
    		  AND r.endTime > :startTime
    		""")
    		boolean existsOverlappingReservation(
    		        @Param("spaceId") Long spaceId,
    		        @Param("startTime") LocalDateTime startTime,
    		        @Param("endTime") LocalDateTime endTime);
    
    List<Reservation> findAllByOrderByStartTimeDesc();

    List<Reservation> findByUserIdOrderByStartTimeDesc(Long userId);
    
    @Query(value = """
    	    SELECT COALESCE(
    	        SUM(
    	            EXTRACT(EPOCH FROM (r.end_time - r.start_time))
    	        ),
    	        0
    	    )
    	    FROM reservations r
    	    WHERE r.space_id = :spaceId
    	      AND r.status = 'CONFIRMED'
    	      AND r.start_time >= :start
    	      AND r.end_time <= :end
    	    """, nativeQuery = true)
    	Double getReservedSeconds(
    	        @Param("spaceId") Long spaceId,
    	        @Param("start") LocalDateTime start,
    	        @Param("end") LocalDateTime end);

}
