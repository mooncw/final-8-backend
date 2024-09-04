package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.UserManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserManagementRepository extends JpaRepository<UserManagement, String> {
    Optional<UserManagement> findByEmpNumber(String empNo);

    void deleteByEmpNumber(String empNumber);
}
