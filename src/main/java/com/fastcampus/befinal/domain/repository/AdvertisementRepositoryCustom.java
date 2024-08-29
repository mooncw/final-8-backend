package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.QAdvertisement;
import com.fastcampus.befinal.domain.info.DashBoardInfo;
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
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QAdvertisement ad = QAdvertisement.advertisement;

    public DashBoardInfo.AdCount getAdCount(String id) {
        return queryFactory
                .select(Projections.constructor(DashBoardInfo.AdCount.class,
                        ad.count().intValue().coalesce(0),
                        new CaseBuilder()
                                .when(userIdEq(id)).then(1)
                                .otherwise(0).sum().coalesce(0),
                        new CaseBuilder()
                                .when(isCompleted()).then(1)
                                .otherwise(0).sum().coalesce(0),
                        new CaseBuilder()
                                .when(isCompleted().and(userIdEq(id))).then(1)
                                .otherwise(0).sum().coalesce(0),
                        new CaseBuilder()
                                .when(isNotCompleted()).then(1)
                                .otherwise(0).sum().coalesce(0),
                        new CaseBuilder()
                                .when(isNotCompleted().and(userIdEq(id))).then(1)
                                .otherwise(0).sum().coalesce(0)
                ))
                .from(ad)
                .where(isInCurrentPeriod())
                .fetchOne();
    }

    public List<DashBoardInfo.DailyDone> getDailyDoneList(String id) {
        DateTemplate<LocalDate> taskDate = Expressions.dateTemplate(LocalDate.class, "DATE({0})", ad.taskDateTime);
        LocalDate date = LocalDate.now();
        LocalDate startOfPeriod = date.getDayOfMonth() <= 15 ? date.withDayOfMonth(1) : date.withDayOfMonth(16);

        List<Tuple> results = queryFactory
                .select(taskDate, ad.count().intValue())
                .from(ad)
                .where(userIdEq(id)
                        .and(isCompleted())
                        .and(isInCurrentPeriod())
                        .and(taskDate.goe(startOfPeriod))
                        .and(taskDate.loe(date)))
                .groupBy(taskDate)
                .orderBy(taskDate.asc())
                .fetch();

        return results.stream()
                .map(tuple -> new DashBoardInfo.DailyDone(
                        tuple.get(0, Date.class).toLocalDate(),
                        tuple.get(1, Integer.class)
                ))
                .collect(Collectors.toList());
    }

    public List<DashBoardInfo.RecentDone> getRecentDoneList(String id) {
        return queryFactory
                .select(Projections.constructor(DashBoardInfo.RecentDone.class,
                        ad.id.stringValue().as("adId"),
                        ad.product.as("adName"),
                        ad.taskDateTime.as("adModifiedDate")
                ))
                .from(ad)
                .where(userIdEq(id)
                        .and(isCompleted())
                        .and(isInCurrentPeriod()))
                .orderBy(ad.taskDateTime.desc())
                .limit(5)
                .fetch();
    }

    private BooleanExpression userIdEq(String id) {
        return ad.assignee.id.eq(id);
    }

    private BooleanExpression isCompleted() {
        return ad.state.isTrue();
    }

    private BooleanExpression isNotCompleted() {
        return ad.state.isFalse();
    }

    private BooleanExpression isInCurrentPeriod() {
        LocalDate date = LocalDate.now();
        int dayOfMonth = date.getDayOfMonth();
        LocalDate startOfPeriod = dayOfMonth <= 15 ? date.withDayOfMonth(1) : date.withDayOfMonth(16);
        LocalDate endOfPeriod = dayOfMonth <= 15 ? date.withDayOfMonth(15) : date.with(TemporalAdjusters.lastDayOfMonth());

        return Expressions.dateTemplate(LocalDate.class, "DATE({0})", ad.postDateTime)
                .between(startOfPeriod, endOfPeriod)
                .and(ad.postDateTime.month().eq(date.getMonthValue()));
    }
}










