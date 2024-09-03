package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.entity.QUser;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fastcampus.befinal.common.contant.ScrollConstant.MANAGE_USER_SCROLL_SIZE;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QUser user = QUser.user;

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
            .where(gtCursorId(cursorId))
            .limit(MANAGE_USER_SCROLL_SIZE)
            .fetch();

        Long nextCursorId = getNextCursorId(cursorId, contents);

        Long totalElements = queryFactory
            .select(user.count())
            .from(user)
            .fetchOne();

        return ScrollPagination.of(totalElements, nextCursorId, contents);
    }

    private BooleanExpression gtCursorId(Long userId) {
        if (userId == null) {
            return null;
        }
        return user.id.gt(userId);
    }

    private Long getNextCursorId(Long cursorId, List<AdminInfo.UserInfo> contents) {
        if (!contents.isEmpty()) {
            AdminInfo.UserInfo lastUserInfo = contents.get(contents.size() - 1);
            return lastUserInfo.id();
        }
        return cursorId;
    }
}
