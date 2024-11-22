package com.example.testysavingsbe.domain.user.repository;

import com.example.testysavingsbe.domain.user.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergy, Long> {
}
