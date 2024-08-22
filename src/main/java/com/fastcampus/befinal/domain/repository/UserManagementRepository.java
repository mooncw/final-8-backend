package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.UserManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<UserManagement, String> {
}
