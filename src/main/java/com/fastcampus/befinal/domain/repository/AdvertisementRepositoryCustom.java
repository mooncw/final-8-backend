package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.entity.AdCategory;
import com.fastcampus.befinal.domain.entity.AdMedia;
import com.fastcampus.befinal.domain.entity.QAdvertisement;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.info.TaskInfo;
import com.querydsl.core.BooleanBuilder;
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
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static com.fastcampus.befinal.common.contant.ScrollConstant.*;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QAdvertisement ad = QAdvertisement.advertisement;

    // 유저 대시보드 기능
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

    // 어드민 대시보드 기능
    public DashboardInfo.AdminAdCountInfo getAdminAdCountInfo(){
        return queryFactory
            .select(Projections.constructor(DashboardInfo.AdminAdCountInfo.class,
                new CaseBuilder()
                    .when(isAssigneeNull()).then(1)
                    .otherwise(0).sum(),
                ad.count().intValue(),
                new CaseBuilder()
                    .when(isCompleted()).then(1)
                    .otherwise(0).sum(),
                new CaseBuilder()
                    .when(isNotCompleted()).then(1)
                    .otherwise(0).sum()
                ))
            .from(ad)
            .where(isInCurrentPeriod())
            .fetchOne();
    }

    public List<DashboardInfo.TodayWork> getTodayWorkList() {
        DateTimeExpression<LocalDate> kstTaskDateTime = Expressions.dateTimeTemplate(LocalDate.class,
            "DATE(CONVERT_TZ({0}, '+00:00', '+09:00'))", ad.taskDateTime);
        LocalDate todayDate = LocalDate.now();
        LocalDate startOfPeriod = todayDate.minusDays(4);

        // 쿼리에서 평균값을 계산
        List<Tuple> results = queryFactory
            .select(kstTaskDateTime,ad.count().intValue())
            .from(ad)
            .where(isCompleted()
                .and(isInCurrentPeriod())
                .and(kstTaskDateTime.goe(startOfPeriod))  // 4일 전부터
                .and(kstTaskDateTime.loe(todayDate)))    // 오늘까지
            .groupBy(kstTaskDateTime)                    // 날짜별로 그룹화
            .orderBy(kstTaskDateTime.asc())              // 날짜 오름차순 정렬
            .fetch();

        return results.stream()
            .map(tuple -> DashboardInfo.TodayWork.of(
                tuple.get(0, Date.class).toLocalDate(),
                tuple.get(1, Integer.class)
            ))
            .collect(Collectors.toList());
    }

    public List<DashboardInfo.DailyAvgDone> getDailyAvgDoneList() {
        // `taskDateTime`을 LocalDateTime으로 받아서 한국 시간으로 변환
        DateTimeExpression<LocalDate> kstTaskDateTime = Expressions.dateTimeTemplate(LocalDate.class,
            "DATE(CONVERT_TZ({0}, '+00:00', '+09:00'))", ad.taskDateTime);

        LocalDate todayDate = LocalDate.now();
        LocalDate startOfPeriod = todayDate.getDayOfMonth() <= 15 ? todayDate.withDayOfMonth(1) : todayDate.withDayOfMonth(16);

        List<Tuple> results = queryFactory
            .select(kstTaskDateTime,                           // 날짜
                ad.count().intValue(),                     // 광고 작업 수 (advertise count)
                ad.assignee.id.countDistinct().intValue())         // 해당 날짜에 작업한 유저 수)
            .from(ad)
            .where(isCompleted()
                .and(isInCurrentPeriod())
                .and(kstTaskDateTime.goe(startOfPeriod))
                .and(kstTaskDateTime.loe(todayDate)))
            .groupBy(kstTaskDateTime)
            .orderBy(kstTaskDateTime.asc())
            .fetch();

        // 결과를 DTO로 변환하면서 평균값 계산
        return results.stream()
            .map(tuple -> {
                // null 체크 추가: 값이 null이면 기본값 0 사용
                Integer adCount = tuple.get(1, Integer.class);
                Integer userCount = tuple.get(2, Integer.class);

                // null 체크 후 언박싱
                int adCountValue = (adCount != null) ? adCount : 0;
                int userCountValue = (userCount != null) ? userCount : 0;

                // 유저 수로 나누어 평균 계산
                double average = userCountValue > 0 ? (double) adCountValue / userCountValue : 0.0;

                return DashboardInfo.DailyAvgDone.of(
                    tuple.get(0, Date.class).toLocalDate(),        // 날짜
                    average                              // 유저당 광고 작업 평균
                );
            }).collect(Collectors.toList());
    }

    public List<DashboardInfo.PersonalTask> getPersonalTaskList() {
        // 쿼리 결과 가져오기
        List<DashboardInfo.PersonalTask> assigneeTasks = getAssigneeTaskList();
        List<DashboardInfo.PersonalModifierTask> modifierTasks = getModifierTaskList();

        // 이름을 키로 하여 작업량과 완료된 작업 수를 병합
        Map<String, DashboardInfo.PersonalTask> taskMap = new HashMap<>();

        // assignee 작업 추가
        for (DashboardInfo.PersonalTask task : assigneeTasks) {
            taskMap.put(task.userName(), task);
        }

        // modifier 작업 병합
        for (DashboardInfo.PersonalModifierTask modifierTask : modifierTasks) {
            String modifierName = modifierTask.modifierName();
            String assigneeName = modifierTask.assigneeName();

            // modifier가 이미 map에 있으면 작업량을 환산
            if (taskMap.containsKey(modifierName)) {
                DashboardInfo.PersonalTask existingTask = taskMap.get(modifierName);
                taskMap.put(modifierName,
                    DashboardInfo.PersonalTask.of(
                        modifierName,
                        existingTask.doneAd() + modifierTask.doneAd(),
                        existingTask.totalAd() + modifierTask.doneAd()
                    )
                );
            } else {
                // modifier가 없으면 새로운 작업 추가
                taskMap.put(modifierName,
                    DashboardInfo.PersonalTask.of(
                        modifierName,
                        modifierTask.doneAd(),
                        modifierTask.doneAd()
                    )
                );
            }

            // 동시에 assignee의 작업량에서 해당 작업을 빼기
            if (taskMap.containsKey(assigneeName)) {
                DashboardInfo.PersonalTask assigneeTask = taskMap.get(assigneeName);
                taskMap.put(assigneeName,
                    DashboardInfo.PersonalTask.of(
                        assigneeName,
                        assigneeTask.doneAd() - modifierTask.doneAd(),
                        assigneeTask.totalAd() - modifierTask.doneAd()
                    )
                );
            }
        }

        // 병합된 결과를 리스트로 반환
        return new ArrayList<>(taskMap.values());
    }

    private List<DashboardInfo.PersonalTask> getAssigneeTaskList() {
        return queryFactory
            .select(Projections.constructor(DashboardInfo.PersonalTask.class,
                ad.assignee.name,
                new CaseBuilder()
                    .when(isCompleted()).then(1)
                    .otherwise(0).sum(),
                ad.count().intValue()
            ))
            .from(ad)
            .where(isInCurrentPeriod())
            .groupBy(ad.assignee)
            .fetch();
    }

    private List<DashboardInfo.PersonalModifierTask> getModifierTaskList() {
        return queryFactory
            .select(Projections.constructor(DashboardInfo.PersonalModifierTask.class,
                ad.assignee.name,
                ad.modifier.name,
                new CaseBuilder()
                    .when(isCompleted().and(ad.assignee.ne(ad.modifier))).then(1)
                    .otherwise(0).sum()))
            .from(ad)
            .where(isInCurrentPeriod())
            .groupBy(ad.modifier, ad.assignee)
            .fetch();
    }


    // 나의 작업 기능
    public ScrollPagination<TaskInfo.CursorInfo, TaskInfo.AdvertisementListInfo> getScrollByCursorInfo(String userId, TaskCommand.FilterConditionRequest taskCommand) {

        BooleanExpression filterExpression = createFilterCondition(taskCommand);
        BooleanExpression cursorExpression = createCursorCondition(taskCommand.cursorInfo());

        List<TaskInfo.AdvertisementListInfo> contents = queryFactory
            .select(Projections.constructor(TaskInfo.AdvertisementListInfo.class,
                ad.id.substring(6),
                ad.adMedia.name,
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

    // 지적 광고 기능
    public ScrollPagination<TaskInfo.CursorInfo, TaskInfo.IssueAdvertisementListInfo> findIssueAdListScrollByCursorInfo(TaskCommand.FilterConditionRequest taskCommand) {

        BooleanExpression filterExpression = createFilterCondition(taskCommand);
        BooleanExpression cursorExpression = createCursorCondition(taskCommand.cursorInfo());

        List<TaskInfo.IssueAdvertisementListInfo> contents = queryFactory
            .select(Projections.constructor(TaskInfo.IssueAdvertisementListInfo.class,
                ad.id.substring(6),
                ad.adMedia.name,
                ad.adCategory.category,
                ad.product,
                ad.advertiser,
                ad.state,
                ad.issue,
                new CaseBuilder()
                    .when(ad.assignee.isNotNull())
                    .then(ad.assignee.name)
                    .otherwise("")
            ))
            .from(ad)
            .leftJoin(ad.assignee)
            .where(filterExpression.and(cursorExpression))
            .orderBy(
                ad.state.asc(),
                ad.id.asc()
            )
            .limit(ISSUE_AD_LIST_SCROLL_SIZE)
            .fetch();

        TaskInfo.CursorInfo nextCursorInfo = getNextCursorIssueAdInfo(contents);

        Long totalElements = queryFactory
            .select(ad.count())
            .from(ad)
            .where(filterExpression)
            .fetchOne();

        return ScrollPagination.of(totalElements, nextCursorInfo, contents);
    }

    public Optional<IssueAdInfo.IssueAdDetailInfo> findIssueAdDetail(String advertisementId) {
        return Optional.ofNullable(queryFactory
            .select(Projections.constructor(IssueAdInfo.IssueAdDetailInfo.class,
                ad.id,
                ad.product,
                ad.advertiser,
                ad.adCategory.category,
                ad.postDateTime,
                new CaseBuilder()
                    .when(ad.assignee.isNotNull())
                    .then(ad.assignee.name)
                    .otherwise(""),
                new CaseBuilder()
                    .when(ad.modifier.isNotNull())
                    .then(ad.modifier.name)
                    .otherwise(""),
                ad.adContent.content
            ))
            .from(ad)
            .leftJoin(ad.modifier)
            .leftJoin(ad.assignee)
            .where(idEq(advertisementId))
            .fetchOne());
    }

    // 조건 생성
    public Long countMediaByPeriod(AdMedia media, String period) {
        BooleanExpression expression;
        if(StringUtils.hasText(period)) {
            expression = getByPeriod(period);
        } else {
            expression = isInCurrentPeriod();
        }

        return queryFactory
            .select(ad.count())
            .from(ad)
            .where(expression.and(ad.adMedia.name.eq(media.getName())))
            .fetchOne();
    }

    public Long countCategoryByPeriod(AdCategory category, String period) {
        BooleanExpression expression;
        if(StringUtils.hasText(period)) {
            expression = getByPeriod(period);
        } else {
            expression = isInCurrentPeriod();
        }

        return queryFactory
            .select(ad.count())
            .from(ad)
            .where(expression.and(ad.adCategory.category.eq(category.getCategory())))
            .fetchOne();
    }

    // ScrollPagination 다음 페이지 조건 생성
    private BooleanExpression createCursorCondition(TaskCommand.CursorInfo cursorInfo) {
        // 첫 페이지인 경우(조건 없음)
        if (cursorInfo == null) {
            return null;
        }

        // 현재 커서가 false(검수 전)인 경우
        if (!cursorInfo.cursorState()) {
            return ad.state.eq(false).and(ad.id.substring(6).gt(cursorInfo.cursorId()))
                .or(ad.state.eq(true));
        }
        // 현재 커서가 true(검수 완료)인 경우
        else {
            return ad.state.eq(true).and(ad.id.substring(6).gt(cursorInfo.cursorId()));
        }
    }

    // 현재 페이지 마지막 데이터 커서 정보
    private TaskInfo.CursorInfo getNextCursorInfo(List<TaskInfo.AdvertisementListInfo> contents) {
        if (!contents.isEmpty()) {
            TaskInfo.AdvertisementListInfo lastData = contents.get(contents.size() - 1);
            return new TaskInfo.CursorInfo(lastData.state(), lastData.adId());
        }

        return null;
    }

    private TaskInfo.CursorInfo getNextCursorIssueAdInfo(List<TaskInfo.IssueAdvertisementListInfo> contents) {
        if (!contents.isEmpty()) {
            TaskInfo.IssueAdvertisementListInfo lastData = contents.get(contents.size() - 1);
            return new TaskInfo.CursorInfo(lastData.state(), lastData.adId());
        }

        return null;
    }

    // 광고 리스트 필터 조건 메서드
    private BooleanExpression createFilterCondition(TaskCommand.FilterConditionRequest command) {
        BooleanExpression expression;

        if (StringUtils.hasText(command.period())) {
            expression = getByPeriod(command.period());
        } else {
            expression = isInCurrentPeriod();
        }

        if (StringUtils.hasText(command.keyword())) {
            expression = expression.and(searchKeyword(command.keyword()));
        }

        if (command.state() != null) {
            expression = expression.and(filterState(command.state()));
        }

        if (command.issue() != null) {
            expression = expression.and(filterIssue(command.issue()));
        }

        if (command.media() != null) {
            expression = expression.and(filterMedia(command.media()));
        }

        if (command.category() != null) {
            expression = expression.and(filterCategory(command.category()));
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
        for (String m : media) {
            builder.or(ad.adMedia.name.eq(m));
        }
        return builder;
    }

    // 선택한 업종명이 포함되는 광고 select
    private BooleanBuilder filterCategory(List<String> category) {
        BooleanBuilder builder = new BooleanBuilder();
        for (String c : category) {
            builder.or(ad.adCategory.category.eq(c));
        }
        return builder;
    }

    // 지정한 차수에 포함되는 광고
    private BooleanExpression getByPeriod(String period) {
        String[] parts = period.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int term = Integer.parseInt(parts[2]);

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

    private BooleanExpression idEq(String id) {
        return ad.id.eq(id);
    }

    private BooleanExpression userIdEq(String id) {
        return ad.assignee.id.eq(Long.valueOf(id));
    }

    private BooleanExpression isCompleted() {
        return ad.state.isTrue();
    }

    private BooleanExpression isNotCompleted() {
        return ad.state.isFalse();
    }

    // 담당자가 아직 정해지지 않았을 경우
    private BooleanExpression isAssigneeNull() {
        return ad.assignee.isNull();
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

    public ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId) {
        List<AdminInfo.UnassignedAdInfo> contents = queryFactory
            .select(Projections.constructor(AdminInfo.UnassignedAdInfo.class,
                ad.id,
                ad.product,
                ad.advertiser,
                ad.adCategory.category
            ))
            .from(ad)
            .where(
                ad.assignee.isNull(),
                gtCursorId(cursorId)
            )
            .limit(MANAGE_TASK_ADVERTISEMENT_SCROLL_SIZE)
            .fetch();

        String nextCursorId = getNextCursorId(cursorId, contents);

        Long totalElements = queryFactory
            .select(ad.count())
            .from(ad)
            .where(ad.assignee.isNull())
            .fetchOne();

        return ScrollPagination.of(totalElements, nextCursorId, contents);
    }

    private String getNextCursorId(String cursorId, List<AdminInfo.UnassignedAdInfo> contents) {
        if (!contents.isEmpty()) {
            AdminInfo.UnassignedAdInfo lastUserInfo = contents.getLast();
            return lastUserInfo.adId();
        }
        return cursorId;
    }

    private BooleanExpression gtCursorId(String cursorId) {
        if (cursorId == null) {
            return null;
        }
        return ad.id.gt(cursorId);
    }

    public List<AdminInfo.UnassignedAdIdInfo> findAllIdByAssigneeIsNull(Long amount) {
        List<String> ids = queryFactory
            .select(ad.id)
            .from(ad)
            .where(ad.assignee.isNull())
            .limit(amount)
            .fetch();

        return queryFactory
            .select(Projections.constructor(AdminInfo.UnassignedAdIdInfo.class,
                ad.id
            ))
            .from(ad)
            .where(ad.id.in(ids))
            .orderBy(ad.id.desc())  // limit 결과에 대해 정렬
            .fetch();
    }

    public void updateAssignee(UserSummary userSummary, List<String> personalTaskAdIdList) {
        queryFactory.update(ad)
            .set(ad.assignee, userSummary)
            .set(ad.assignDateTime, LocalDateTime.now())
            .where(ad.id.in(personalTaskAdIdList))
            .execute();
    }
}