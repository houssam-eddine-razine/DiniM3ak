package com.example.DeniM3ak.repository;

import com.example.DeniM3ak.model.Carpool;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarpoolRepository extends MongoRepository<Carpool, String> {
    @Query("{'departure': ?0, 'arrival': ?1, 'date': {$gte: ?2}, 'seats': {$gte: ?3}}")
    List<Carpool> findByDepartureAndArrivalAndDateAndSeats(String departure, String arrival, Date date, int seats);

}
