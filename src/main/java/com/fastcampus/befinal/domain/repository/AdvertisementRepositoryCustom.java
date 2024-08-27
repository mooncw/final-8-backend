package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.QAdvertisement;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QAdvertisement ad = QAdvertisement.advertisement;

    public DashBoardInfo.AdCount getAdCount(String userId, LocalDateTime today) {
        return queryFactory
                .select(Projections.constructor(DashBoardInfo.AdCount.class,
                        ad.count(),
                        new CaseBuilder()
                                .when(userIdEq(userId)).then(1L)
                                .otherwise(0L).sum(),
                        new CaseBuilder()
                                .when(isCompleted()).then(1L)
                                .otherwise(0L).sum(),
                        new CaseBuilder()
                                .when(isCompleted().and(userIdEq(userId))).then(1L)
                                .otherwise(0L).sum(),
                        new CaseBuilder()
                                .when(isNotCompleted()).then(1L)
                                .otherwise(0L).sum(),
                        new CaseBuilder()
                                .when(isNotCompleted().and(userIdEq(userId))).then(1L)
                                .otherwise(0L).sum()
                ))
                .from(ad)
                .where(isInCurrentPeriod(today))
                .fetchOne();
    }

    public List<DashBoardInfo.DailyDone> getDailyDoneList(String userId, LocalDateTime today) {
        return queryFactory
                .select(Projections.constructor(DashBoardInfo.DailyDone.class,
                        ad.taskDateTime,
                        ad.count()
                ))
                .from(ad)
                .where(userIdEq(userId)
                        .and(isCompleted())
                        .and(ad.taskDateTime.between(today.withDayOfMonth(1), today)))
                .groupBy(ad.taskDateTime)
                .orderBy(ad.taskDateTime.asc())
                .fetch();
    }

    public List<DashBoardInfo.RecentDone> getRecentDoneList(String userId, LocalDateTime today) {
        return queryFactory
                .select(Projections.constructor(DashBoardInfo.RecentDone.class,
                        ad.id,
                        ad.product,
                        ad.taskDateTime
                ))
                .from(ad)
                .where(userIdEq(userId).and(isCompleted()))
                .orderBy(ad.taskDateTime.desc())
                .limit(5)
                .fetch();
    }

    private BooleanExpression userIdEq(String userId) {
        return userId != null ? ad.assignee.id.eq(userId) : null;
    }

    private BooleanExpression isCompleted() {
        return ad.state.isTrue();
    }

    private BooleanExpression isNotCompleted() {
        return ad.state.isFalse();
    }

    private BooleanExpression isInCurrentPeriod(LocalDateTime today) {
        int dayOfMonth = today.getDayOfMonth();
        LocalDateTime startOfPeriod = dayOfMonth <= 15 ? today.withDayOfMonth(1) : today.withDayOfMonth(16);
        LocalDateTime endOfPeriod = dayOfMonth <= 15 ? today.withDayOfMonth(15) : today.with(TemporalAdjusters.lastDayOfMonth());

        return ad.postDateTime.between(startOfPeriod, endOfPeriod)
                .and(ad.postDateTime.month().eq(today.getMonthValue()));
    }
}










