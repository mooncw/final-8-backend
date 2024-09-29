package com.fastcampus.befinal.domain.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import com.fastcampus.befinal.common.util.PeriodUtil;
import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.entity.ESAdvertisement;
import com.fastcampus.befinal.domain.info.TaskInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ESAdvertisementRepositoryCustom {
    private final ElasticsearchTemplate elasticsearchTemplate;

    public List<TaskInfo.IssueAdvertisementListInfo> searchIssueAdvertisementListInfo(TaskCommand.FilterConditionRequest command) {
        NativeQuery searchQuery = new NativeQuery(issueESAdvertisementQuery(command));

        SearchHits<ESAdvertisement> hits = elasticsearchTemplate.search(searchQuery, ESAdvertisement.class);

        return hits.getSearchHits()
            .stream()
            .map(SearchHit::getContent)
            .map(TaskInfo.IssueAdvertisementListInfo::from)
            .toList();
    }

    private Query issueESAdvertisementQuery(TaskCommand.FilterConditionRequest command) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        List<Query> shouldQuery = new ArrayList<>();
        List<Query> filterQuery = new ArrayList<>();

        if (StringUtils.hasText(command.keyword())) {
            shouldQuery.add(new TermQuery.Builder().field("advertisementId").value(command.keyword()).build()._toQuery());
            shouldQuery.add(new MatchQuery.Builder().field("product").query(command.keyword()).build()._toQuery());
            shouldQuery.add(new MatchQuery.Builder().field("advertiser").query(command.keyword()).build()._toQuery());
        }

        PeriodUtil periodUtil = PeriodUtil.from(command.period());

        filterQuery.add(
            new RangeQuery.Builder().field("assignDateTime")
                .gte(JsonData.of(periodUtil.getStartAssignDateTime()))
                .lte(JsonData.of(periodUtil.getEndAssignDateTime()))
                .build()
                ._toQuery()
        );

        if (command.state() != null) {
            filterQuery.add(new TermQuery.Builder().field("state").value(command.state()).build()._toQuery());
        }

        if (command.issue() != null) {
            filterQuery.add(new TermQuery.Builder().field("state").value(command.state()).build()._toQuery());
        }

        if (command.media() != null) {
            for (String media : command.media()) {
                filterQuery.add(new TermQuery.Builder().field("media").value(media).build()._toQuery());
            }
        }

        if (command.category() != null) {
            for (String category : command.category()) {
                filterQuery.add(new TermQuery.Builder().field("category").value(category).build()._toQuery());
            }
        }

        return boolQuery.should(shouldQuery).filter(filterQuery).build()._toQuery();
    }
}
