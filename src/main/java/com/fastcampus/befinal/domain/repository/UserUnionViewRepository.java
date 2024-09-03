package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.UserUnionView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUnionViewRepository extends JpaRepository<UserUnionView, String> {
    boolean existsByUserId(String userId);

    boolean existsByUserIdOrPhoneNumberOrEmpNumberOrEmail(String id, String phoneNumber, String empNumber, String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
