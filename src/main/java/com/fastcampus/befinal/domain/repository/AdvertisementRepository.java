package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.AdCategory;
import com.fastcampus.befinal.domain.entity.AdMedia;
import com.fastcampus.befinal.domain.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, String> {
    int countByAdMedia(AdMedia adMedia);
    int countByAdCategory(AdCategory adCategory);
}
