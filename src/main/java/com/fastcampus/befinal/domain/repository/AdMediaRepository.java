package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.AdMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdMediaRepository extends JpaRepository<AdMedia, Integer> {
    List<AdMedia> findByNameContaining(String keyword);
}
