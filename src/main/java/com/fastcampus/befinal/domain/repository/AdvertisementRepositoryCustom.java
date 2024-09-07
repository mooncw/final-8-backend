package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.entity.QAdvertisement;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.querydsl.core.BooleanBuilder;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fastcampus.befinal.common.contant.TaskConstant.MY_TASK_LIST_SCROLL_SIZE;

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

    public TaskInfo.AdCountInfo getMyTaskCount(String id) {
        return queryFactory
                .select(Projections.constructor(TaskInfo.AdCountInfo.class,
                        new CaseBuilder()
                                .when(userIdEq(id)).then(1)
                                .otherwise(0).sum(),
                        new CaseBuilder()
                                .when(isCompleted().and(userIdEq(id))).then(1)
                                .otherwise(0).sum(),
                        new CaseBuilder()
                                .when(isNotCompleted().and(userIdEq(id))).then(1)
                                .otherwise(0).sum()
                ))
                .from(ad)
                .where(isInCurrentPeriod())
                .fetchOne();
    }


    public ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> getScrollByCursorInfo(String userId, TaskCommand.CursorInfo cursorInfo, String keyword, String period,
                                                                                                        Boolean state, Boolean issue, List<String> media, List<String> adCategory) {

        BooleanExpression filterExpression = createFilterCondition(keyword, period, state, issue, media, adCategory);
        BooleanExpression cursorExpression = createCursorCondition(cursorInfo);

        List<TaskInfo.AdvertisementListInfo> contents = queryFactory
                .select(Projections.constructor(TaskInfo.AdvertisementListInfo.class,
                        ad.id.substring(6),
                        ad.media,
                        ad.adCategory.category,
                        ad.product,
                        ad.advertiser,
                        ad.state,
                        ad.issue
                ))
                .from(ad)
                .where(filterExpression.and(cursorExpression).and(userIdEq(userId)))
                .orderBy(
                        ad.state.asc(),
                        ad.id.asc()
                )
                .limit(MY_TASK_LIST_SCROLL_SIZE)
                .fetch();

        TaskInfo.CursorInfo nextCursorInfo = getNextCursorInfo(contents);

        Long totalElements = queryFactory
                .select(ad.count())
                .from(ad)
                .where(filterExpression.and(userIdEq(userId)))
                .fetchOne();

        return ScrollPagination.of(totalElements, nextCursorInfo, contents);
    }

    private BooleanExpression createCursorCondition(TaskCommand.CursorInfo cursorInfo) {
        // 첫 페이지인 경우(조건 없음)
        if(cursorInfo == null) {
            return null;
        }

        // 현재 커서가 false(검수 전)인 경우
        if(!cursorInfo.cursorState()) {
            return ad.state.eq(false).and(ad.id.substring(6).gt(cursorInfo.cursorId()))
                    .or(ad.state.eq(true));
        }
        // 현재 커서가 true(검수 완료)인 경우
        else {
            return ad.state.eq(true).and(ad.id.substring(6).gt(cursorInfo.cursorId()));
        }
    }

    private TaskInfo.CursorInfo getNextCursorInfo(List<TaskInfo.AdvertisementListInfo> contents) {
        if(!contents.isEmpty()) {
            TaskInfo.AdvertisementListInfo lastData = contents.get(contents.size() - 1);
            return new TaskInfo.CursorInfo(lastData.state(), lastData.adId());
        }

        return null;
    }

    private BooleanExpression createFilterCondition(String keyword, String period, Boolean state, Boolean issue, List<String> media, List<String> adCategory) {
        BooleanExpression expression;

        if(StringUtils.hasText(period)) {
            expression = getByPeriod(period);
        } else {
            expression = isInCurrentPeriod();
        }

        if(StringUtils.hasText(keyword)) {
            expression = expression.and(searchKeyword(keyword));
        }

        if(state != null) {
            expression = expression.and(filterState(state));
        }

        if(issue != null) {
            expression = expression.and(filterIssue(issue));
        }

        if(media != null) {
            expression = expression.and(filterMedia(media));
        }

        if(adCategory != null) {
            expression = expression.and(filterAdCategory(adCategory));
        }

        return expression;
    }

    // 검수전/검수완료 상태
    private BooleanExpression filterState(Boolean state) {
        return ad.state.eq(state);
    }

    // 지적/비지적 상태
    private BooleanExpression filterIssue(Boolean issue) {
        return ad.issue.eq(issue);
    }

    // 키워드로 고유번호, 상품명, 광고주에 포함되는 광고 select
    private BooleanExpression searchKeyword(String keyword) {
        return ad.id.containsIgnoreCase(keyword)
                .or(ad.product.containsIgnoreCase(keyword))
                .or(ad.advertiser.containsIgnoreCase(keyword));
    }

    // 선택한 매체명이 포함되는 광고 select
    private BooleanBuilder filterMedia(List<String> media) {
        BooleanBuilder builder = new BooleanBuilder();
        for(String m : media) {
            builder.or(ad.media.eq(m));
        }
        return builder;
    }

    // 선택한 업종명이 포함되는 광고 select
    private BooleanBuilder filterAdCategory(List<String> adCategory) {
        BooleanBuilder builder = new BooleanBuilder();
        for(String ac : adCategory) {
            builder.or(ad.adCategory.category.eq(ac));
        }
        return builder;
    }

    // 지정한 차수에 포함되는 광고 내용
    private BooleanExpression getByPeriod(String period) {
        String[] parts = period.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int term = Integer.parseInt(parts[2].substring(0, 1));

        LocalDate startDate, endDate;
        if (term == 1) {
            startDate = LocalDate.of(year, month, 1);
            endDate = LocalDate.of(year, month, 15);
        } else {
            startDate = LocalDate.of(year, month, 16);
            endDate = LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth());
        }
        DateTimeExpression<LocalDate> kstAssignDateTime = Expressions.dateTimeTemplate(LocalDate.class,
                "DATE(CONVERT_TZ({0}, '+00:00', '+09:00'))", ad.assignDateTime);
        return kstAssignDateTime.between(startDate, endDate);
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
        DateTimeExpression<LocalDate> kstAssignDateTime = Expressions.dateTimeTemplate(LocalDate.class,
                "DATE(CONVERT_TZ({0}, '+00:00', '+09:00'))", ad.assignDateTime);

        // 한국 시간 기준으로 날짜 범위와 비교
        return kstAssignDateTime.between(startOfPeriod, endOfPeriod)
                .and(ad.assignDateTime.month().eq(todayDate.getMonthValue()));
    }
}