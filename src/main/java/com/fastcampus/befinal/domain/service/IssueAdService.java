package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.TaskCommand;
import com.fastcampus.befinal.domain.info.IssueAdInfo;
import com.fastcampus.befinal.domain.info.TaskInfo;

public interface IssueAdService {

    TaskInfo.TaskListInfo findIssueAdList(TaskCommand.FilterConditionRequest command);
    IssueAdInfo.IssueAdDetailAllInfo findIssueAdDetail(String advertisementId);
}
