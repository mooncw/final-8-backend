package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.QUser;
import com.fastcampus.befinal.domain.entity.QUserSummary;
import com.fastcampus.befinal.domain.info.DashboardInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserSummaryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final QUserSummary userSummary = QUserSummary.userSummary;

    public List<DashboardInfo.UserName> findUserNameList() {
        return queryFactory
            .select(Projections.constructor(DashboardInfo.UserName.class,
                userSummary.name))
            .from(userSummary)
            .fetch();
    }
}
