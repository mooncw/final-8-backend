package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.dataprovider.AdReviewReader;
import com.fastcampus.befinal.domain.dataprovider.AdvertisementReader;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("IssueAdService 테스트")
@ExtendWith(MockitoExtension.class)
public class IssueAdServiceImplTest {
    @InjectMocks
    private IssueAdServiceImpl issueAdService;

    @Mock
    private AdvertisementReader advertisementReader;

    @Mock
    private AdReviewReader adReviewReader;

    @Test
    @DisplayName("지적광고 상세조회 성공 테스트")
    void getIssueAdDetailTest(){
        //given
        String advertisementId ="A0001";
        IssueAdInfo.IssueAdDetailInfo detailInfo = IssueAdInfo.IssueAdDetailInfo.builder().build();
        List<IssueAdInfo.IssueAdReviewInfo> reviewInfoList = List.of(IssueAdInfo.IssueAdReviewInfo.builder().build());

        doReturn(detailInfo)
            .when(advertisementReader)
            .findIssueAdDetail(advertisementId);

        doReturn(reviewInfoList)
            .when(adReviewReader)
            .findIssueAdReviewList(advertisementId);

        //when
        issueAdService.findIssueAdDetail(advertisementId);

        //verify
        verify(advertisementReader, times(1)).findIssueAdDetail(advertisementId);
        verify(adReviewReader, times(1)).findIssueAdReviewList(advertisementId);

    }
}
