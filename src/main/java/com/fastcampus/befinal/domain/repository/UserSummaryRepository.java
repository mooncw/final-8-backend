package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.UserSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSummaryRepository extends JpaRepository<UserSummary, Long> {
}
