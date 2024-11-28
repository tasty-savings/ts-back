package com.example.testysavingsbe.domain.user.repository;

import com.example.testysavingsbe.domain.user.entity.PreferType;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.domain.user.entity.UserPreferType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPreferTypeRepository extends JpaRepository<UserPreferType, Long> {
    List<UserPreferType> findAllByUser(User user);
    @Modifying
    @Query("DELETE FROM UserPreferType upt WHERE upt.user = :user AND upt.type IN :types")
    void deleteAllByUserAndType(@Param("user") User user, @Param("types") List<PreferType> types);
}
