package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.entity.QUserManagement;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.fastcampus.befinal.common.contant.AdminConstant.SIGN_UP_USER_LIST_SCROLL_SIZE;

@Repository
@RequiredArgsConstructor
public class UserManagementRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QUserManagement userManagement = QUserManagement.userManagement;

    // cursorId는 UserManagement의 auto_increment id와 동일합니다.
    public ScrollPagination<Long, AdminInfo.SignUpUserInfo> findScrollById(Long cursorId) {
        List<AdminInfo.SignUpUserInfo> contents = queryFactory
            .select(Projections.constructor(AdminInfo.SignUpUserInfo.class,
                userManagement.id,
                userManagement.name,
                userManagement.empNumber,
                userManagement.phoneNumber,
                userManagement.email,
                userManagement.signUpDateTime
            ))
            .from(userManagement)
            .where(gtCursorId(cursorId))
            .limit(SIGN_UP_USER_LIST_SCROLL_SIZE)
            .fetch();

        Long nextCursorId = getNextCursorId(cursorId, contents);

        Long totalElements = queryFactory
            .select(userManagement.count())
            .from(userManagement)
            .fetchOne();

        return ScrollPagination.of(totalElements, nextCursorId, contents);
    }

    private BooleanExpression gtCursorId(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        return userManagement.id.gt(userId);
    }

    private Long getNextCursorId(Long cursorId, List<AdminInfo.SignUpUserInfo> contents) {
        if (!contents.isEmpty()) {
            AdminInfo.SignUpUserInfo lastUserInfo = contents.get(contents.size() - 1);
            return lastUserInfo.id();
        }
        return cursorId;
    }
}
