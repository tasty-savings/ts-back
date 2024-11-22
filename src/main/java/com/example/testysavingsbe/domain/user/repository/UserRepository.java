package com.example.testysavingsbe.domain.user.repository;

import com.example.testysavingsbe.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"allergy"})
    User findBySocialId(Long socialId);
}
