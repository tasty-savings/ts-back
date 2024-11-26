package com.example.testysavingsbe.domain.user.repository;

import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.domain.user.entity.UserPreferType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferTypeRepository extends JpaRepository<UserPreferType, Long> {
    List<UserPreferType> findAllByUser(User user);
}
