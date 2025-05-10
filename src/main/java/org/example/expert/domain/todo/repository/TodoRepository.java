package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoQueryDSLRepositoryCustom {

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN FETCH t.user u " +
            "WHERE (t.weather = :weather OR :weather IS NULL) AND " +
            "((:startDate IS NULL AND :endDate IS NULL) OR" +
            " (t.modifiedAt BETWEEN COALESCE(:startDate, t.modifiedAt) AND" +
            " COALESCE(:endDate, t.modifiedAt))) ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByWeatherAndPeriodRangeOrderByModifiedAtDesc(Pageable pageable,
                                              @Param("weather") String weather,
                                              @Param("startDate")LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

//    @Query("SELECT t FROM Todo t " +
//            "LEFT JOIN t.user " +
//            "WHERE t.id = :todoId")
//    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
