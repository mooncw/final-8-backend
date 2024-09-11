package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {
}
