package com.bzerok.server.domain.liquor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {

    @Query("select l from Liquor l where l.userId = :userId")
    List<Liquor> findByUserId(@Param("userId") Long userId);

}
