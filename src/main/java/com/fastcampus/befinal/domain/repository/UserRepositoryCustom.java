package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.entity.QAdvertisement;
import com.fastcampus.befinal.domain.entity.QUser;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.fastcampus.befinal.common.contant.AuthConstant.ADMIN_AUTHORITY;
import static com.fastcampus.befinal.common.contant.ScrollConstant.MANAGE_EMP_SCROLL_SIZE;
import static com.fastcampus.befinal.common.contant.ScrollConstant.MANAGE_USER_SCROLL_SIZE;
import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.INVALID_USER_TASK_SORT_TYPE;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QUser user = QUser.user;
    private static final QAdvertisement ad = QAdvertisement.advertisement;

    // cursorId는 User EmpNumber와 동일합니다.
    public ScrollPagination<Long, AdminInfo.UserInfo> findScrollByEmpNumber(Long cursorId) {
        List<AdminInfo.UserInfo> contents = queryFactory
            .select(Projections.constructor(AdminInfo.UserInfo.class,
                user.id,
                user.empNumber,
                user.name,
                user.role,
                user.userId,
                user.phoneNumber,
                user.email,
                user.signUpDateTime,
                user.finalLoginDateTime
            ))
            .from(user)
            .where(
                user.role.ne("ROLE_ADMIN"),
                gtCursorId(cursorId)
            )
            .limit(MANAGE_USER_SCROLL_SIZE)
            .fetch();

        Long nextCursorId = getNextCursorId(cursorId, contents);

        Long totalElements = queryFactory
            .select(user.count())
            .from(user)
            .where(user.role.ne(ADMIN_AUTHORITY))
            .fetchOne();

        return ScrollPagination.of(totalElements, nextCursorId, contents);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        if (cursorId == null) {
            return null;
        }
        return user.id.gt(cursorId);
    }

    private Long getNextCursorId(Long cursorId, List<AdminInfo.UserInfo> contents) {
        if (!contents.isEmpty()) {
            AdminInfo.UserInfo lastUserInfo = contents.getLast();
            return lastUserInfo.id();
        }
        return cursorId;
    }

    public ScrollPagination<Integer, AdminInfo.UserTaskInfo> findScrollOrderByRequest(AdminCommand.FindUserTaskListRequest command) {
        NumberExpression<Integer> totalAd = new CaseBuilder()
            .when(user.id.eq(ad.assignee.id)).then(1)
            .otherwise(0).sum();

        NumberExpression<Integer> notDoneAd = new CaseBuilder()
            .when(user.id.eq(ad.assignee.id).and(ad.state.eq(false))).then(1)
            .otherwise(0).sum();

        NumberExpression<Integer> doneAd = new CaseBuilder()
            .when(user.id.eq(ad.assignee.id).and(ad.state.eq(true))).then(1)
            .otherwise(0).sum();

        NumberExpression<Double> doneRatio = new CaseBuilder()
            .when(totalAd.eq(0)).then(0.0)
            .otherwise(doneAd.divide(totalAd).doubleValue());

        List<AdminInfo.UserTaskInfo> contents = queryFactory
            .select(Projections.constructor(AdminInfo.UserTaskInfo.class,
                user.id,
                user.empNumber,
                user.name,
                totalAd,
                notDoneAd,
                doneAd,
                doneRatio
            ))
            .from(user)
            .leftJoin(ad)
            .on(user.id.eq(ad.assignee.id).and(getByPeriod(command.period())))
            .groupBy(user.id)
            .orderBy(orderCase(command, doneAd, doneRatio).toArray(new OrderSpecifier[0]))
            .limit(MANAGE_EMP_SCROLL_SIZE)
            .offset((command.cursorId() - 1) * MANAGE_EMP_SCROLL_SIZE)
            .fetch();

        Long totalElements = queryFactory
            .select(user.count())
            .from(user)
            .fetchOne();

        return ScrollPagination.of(totalElements, command.cursorId(), contents);
    }

    public Optional<AdminInfo.UserDetailInfo> findUserDetailInfo(Long id) {
        return Optional.ofNullable(queryFactory
            .select(Projections.constructor(AdminInfo.UserDetailInfo.class,
                user.name,
                new CaseBuilder()
                    .when(user.role.eq("ROLE_USER")).then("작업자")
                    .when(user.role.eq("ROLE_ADMIN")).then("관리자")
                    .otherwise("0")
            ))
            .from(user)
            .where(user.id.eq(id))
            .fetchOne());
    }

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

    private List<OrderSpecifier<?>> orderCase(AdminCommand.FindUserTaskListRequest command,
                                              NumberExpression<Integer> doneAd, NumberExpression<Double> doneRatio) {
        switch (command.sorted()) {
            case EMP_NUMBER -> {
                return List.of(new OrderSpecifier<>(Order.ASC, user.empNumber));
            }
            case DONE_DESC -> {
                return List.of(
                    new OrderSpecifier<>(Order.DESC, doneAd),
                    new OrderSpecifier<>(Order.ASC, user.empNumber)
                );
            }
            case DONE_ASC -> {
                return List.of(
                    new OrderSpecifier<>(Order.ASC, doneAd),
                    new OrderSpecifier<>(Order.ASC, user.empNumber)
                );
            }
            case DONE_RATIO_DESC -> {
                return List.of(
                    new OrderSpecifier<>(Order.DESC, doneRatio),
                    new OrderSpecifier<>(Order.ASC, user.empNumber)
                );
            }
            case DONE_RATIO_ASC -> {
                return List.of(
                    new OrderSpecifier<>(Order.ASC, doneRatio),
                    new OrderSpecifier<>(Order.ASC, user.empNumber)
                );
            }
            default -> throw new BusinessException(INVALID_USER_TASK_SORT_TYPE);
        }
    }

    public List<AdminInfo.AssigneeInfo> findAllAssignee() {
        return queryFactory
            .select(Projections.constructor(AdminInfo.AssigneeInfo.class,
                user.id,
                user.empNumber,
                user.name,
                user.additionalTaskCount
                ))
            .from(user)
            .orderBy(user.empNumber.asc())
            .fetch();
    }

    public void updateAdditionalTaskCount(AdminCommand.SelectedAssigneeInfo info) {
        queryFactory.update(user)
            .set(user.additionalTaskCount, user.additionalTaskCount.add(1))
            .where(user.id.eq(info.id()))
            .execute();
    }
}
