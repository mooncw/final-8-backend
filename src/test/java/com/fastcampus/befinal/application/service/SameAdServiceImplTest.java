package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdSimilarityReader;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.SameAdInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@DisplayName("SameAdService 테스트")
@ExtendWith(MockitoExtension.class)
public class SameAdServiceImplTest {
    @InjectMocks
    private SameAdServiceImpl sameAdService;

    @Mock
    private AdvertisementReader advertisementReader;

    @Mock
    private AdSimilarityReader adSimilarityReader;

    @Test
    @DisplayName("동일 광고 유사율 리스트 조회 성공 테스트")
    void findSimilarityListTest() {
        //given
        String inspectionAdvertisementId = "202409A00001";

        SameAdInfo.InspectionAdInfo inspectionAdInfo = SameAdInfo.InspectionAdInfo.builder()
            .id(inspectionAdvertisementId)
            .product("상품명_1")
            .advertiser("광고주_1")
            .category("의류")
            .postDateTime(LocalDateTime.of(2024,8,20,11,20,1))
            .content("어쩌구. 저쩌구.")
            .build();

        SameAdInfo.AdSimilarityInfo adSimilarityInfo = SameAdInfo.AdSimilarityInfo.builder()
            .id("202312A00001")
            .product("상품명_2")
            .advertiser("광고주_2")
            .category("의류")
            .postDateTime(LocalDateTime.of(2023,11,11,11,20,1))
            .similarity(new BigDecimal("0.80"))
            .sameSentenceCount(7)
            .build();

        SameAdInfo.FindSimilarityListInfo info = SameAdInfo.FindSimilarityListInfo.of(inspectionAdInfo, List.of(adSimilarityInfo));

        doReturn(inspectionAdInfo)
            .when(advertisementReader)
            .findInspectionAdInfo(anyString());

        doReturn(List.of(adSimilarityInfo))
            .when(adSimilarityReader)
            .findAdSimilarityInfoList(anyString());

        //when
        SameAdInfo.FindSimilarityListInfo result = sameAdService.findSimilarityList(inspectionAdvertisementId);

        //then
        assertThat(result.inspectionAdInfo()).isEqualTo(info.inspectionAdInfo());
        assertThat(result.adSimilarityInfoList()).isEqualTo(info.adSimilarityInfoList());
    }

    @Test
    @DisplayName("동일 광고 유사율 상세보기 조회 성공 테스트")
    void findSimilarityDetailTest() {
        //given
        String inspectionAdvertisementId = "202409A00001";
        String comparisonAdvertisementId = "202312A00001";

        SameAdInfo.FindSimilarityDetailInfo info = SameAdInfo.FindSimilarityDetailInfo.builder()
            .content("어쩌구. 저쩌구.")
            .sameSentence("어쩌구.")
            .build();

        doReturn(info)
            .when(adSimilarityReader)
            .findSimilarityDetailInfo(anyString(), anyString());

        //when
        SameAdInfo.FindSimilarityDetailInfo result = sameAdService.findSimilarityDetail(inspectionAdvertisementId, comparisonAdvertisementId);

        //then
        assertThat(result.content()).isEqualTo(info.content());
        assertThat(result.sameSentence()).isEqualTo(info.sameSentence());
    }
}
