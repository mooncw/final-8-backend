package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.AdCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdCategoryRepository extends JpaRepository<AdCategory, Integer> {
    List<AdCategory> findByCategoryContaining(String keyword);
}
