package com.thiago.demoproject.repository;

import com.thiago.demoproject.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("SELECT p FROM Person p WHERE p.email LIKE LOWER(CONCAT('%',:email,'%'))")
    Page<Person> findPeopleByEmail(@Param("email") String email, Pageable pageable);

    Optional<Person> findByEmail(String email);
}
