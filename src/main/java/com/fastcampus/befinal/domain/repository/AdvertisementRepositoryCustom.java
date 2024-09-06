package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.QAdvertisement;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QAdvertisement ad = QAdvertisement.advertisement;

    public DashboardInfo.AdCount getAdCount(String id) {
        return queryFactory
                .select(Projections.constructor(DashboardInfo.AdCount.class,
                        ad.count().intValue(),
                        new CaseBuilder()
                                .when(userIdEq(id)).then(1)
                                .otherwise(0).sum(),
                        new CaseBuilder()
                                .when(isCompleted()).then(1)
                                .otherwise(0).sum(),
                        new CaseBuilder()
                                .when(isCompleted().and(userIdEq(id))).then(1)
                                .otherwise(0).sum(),
                        new CaseBuilder()
                                .when(isNotCompleted()).then(1)
                                .otherwise(0).sum(),
                        new CaseBuilder()
                                .when(isNotCompleted().and(userIdEq(id))).then(1)
                                .otherwise(0).sum()
                ))
                .from(ad)
                .where(isInCurrentPeriod())
                .fetchOne();
    }

    public List<DashboardInfo.DailyDone> getDailyDoneList(String id) {
        // `taskDateTime`을 LocalDateTime으로 받아서 한국 시간으로 변환
        DateTimeExpression<LocalDate> kstTaskDateTime = Expressions.dateTimeTemplate(LocalDate.class,
                "DATE(CONVERT_TZ({0}, '+00:00', '+09:00'))", ad.taskDateTime);

        LocalDate todayDate = LocalDate.now();
        LocalDate startOfPeriod = todayDate.getDayOfMonth() <= 15 ? todayDate.withDayOfMonth(1) : todayDate.withDayOfMonth(16);

        List<Tuple> results = queryFactory
                .select(kstTaskDateTime, ad.count().intValue())
                .from(ad)
                .where(userIdEq(id)
                    .and(isCompleted())
                    .and(isInCurrentPeriod())
                    .and(kstTaskDateTime.goe(startOfPeriod))
                    .and(kstTaskDateTime.loe(todayDate)))
                .groupBy(kstTaskDateTime)
                .orderBy(kstTaskDateTime.asc())
                .fetch();

        return results.stream()
                .map(tuple -> DashboardInfo.DailyDone.of(
                        tuple.get(0, Date.class).toLocalDate(),
                        tuple.get(1, Integer.class)
                ))
                .collect(Collectors.toList());
    }

    public List<DashboardInfo.RecentDone> getRecentDoneList(String id) {
        return queryFactory
                .select(Projections.constructor(DashboardInfo.RecentDone.class,
                        ad.id.stringValue().as("adId"),
                        ad.product.as("adName"),
                        ad.taskDateTime.as("adTaskDateTime")
                ))
                .from(ad)
                .where(userIdEq(id)
                        .and(isCompleted())
                        .and(isInCurrentPeriod()))
                .orderBy(ad.taskDateTime.desc())
                .limit(5)
                .fetch();
    }

    public Optional<IssueAdInfo.IssueAdDetailInfo> findIssueAdDetail(String advertisementId){
        return Optional.ofNullable(queryFactory
            .select(Projections.constructor(IssueAdInfo.IssueAdDetailInfo.class,
                ad.id,
                ad.product,
                ad.advertiser,
                ad.adCategory.category,
                ad.postDateTime,
                ad.assignee.name,
                ad.modifier.name,
                ad.adContent.content
                ))
            .from(ad)
            .where(idEq(advertisementId))
            .fetchOne());
    }

    private BooleanExpression idEq(String id){ return ad.id.eq(id); }

    private BooleanExpression userIdEq(String id) {
        return ad.assignee.userId.eq(id);
    }

    private BooleanExpression isCompleted() {
        return ad.state.isTrue();
    }

    private BooleanExpression isNotCompleted() {
        return ad.state.isFalse();
    }

    private BooleanExpression isInCurrentPeriod() {
        LocalDate todayDate = LocalDate.now();
        int dayOfMonth = todayDate.getDayOfMonth();
        LocalDate startOfPeriod = dayOfMonth <= 15 ? todayDate.withDayOfMonth(1) : todayDate.withDayOfMonth(16);
        LocalDate endOfPeriod = dayOfMonth <= 15 ? todayDate.withDayOfMonth(15) : todayDate.with(TemporalAdjusters.lastDayOfMonth());

        // `ad.assignDateTime`을 LocalDateTime으로 변환하고 한국 시간으로 변환
        DateTimeExpression<LocalDate> kstPostDateTime = Expressions.dateTimeTemplate(LocalDate.class,
                "DATE(CONVERT_TZ({0}, '+00:00', '+09:00'))", ad.assignDateTime);

        // 한국 시간 기준으로 날짜 범위와 비교
        return kstPostDateTime.between(startOfPeriod, endOfPeriod)
                .and(ad.assignDateTime.month().eq(todayDate.getMonthValue()));
    }
}