package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.util.ScrollPagination;
import com.fastcampus.befinal.domain.command.AdminCommand;
import com.fastcampus.befinal.domain.dataprovider.*;
import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.entity.UserManagement;
import com.fastcampus.befinal.domain.entity.UserSummary;
import com.fastcampus.befinal.domain.info.AdminInfo;
import com.fastcampus.befinal.domain.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.INSUFFICIENT_UNASSIGNED_ADVERTISEMENT;
import static com.fastcampus.befinal.common.response.error.info.AdminErrorCode.INVALID_TASK_ASSIGNMENT_AMOUNT;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserManagementReader userManagementReader;
    private final UserManagementStore userManagementStore;
    private final UserReader userReader;
    private final UserStore userStore;
    private final UserSummaryReader userSummaryReader;
    private final UserSummaryStore userSummaryStore;
    private final AdvertisementReader advertisementReader;
    private final AdvertisementStore advertisementStore;

    @Override
    @Transactional
    public void approveUser(AdminCommand.ApproveUserRequest command) {
        command.userList().stream()
            .map(AdminCommand.ApproveUser::empNo)
            .forEach(empNo -> {
                UserManagement userManagement = userManagementReader.findByEmpNo(empNo);
                userStore.store(userManagement);
                userSummaryStore.store(userManagement);
                userManagementStore.delete(userManagement);
            });
    }

    @Override
    @Transactional
    public void rejectUser(AdminCommand.RejectUserRequest command) {
        command.userList().stream()
            .map(AdminCommand.RejectUser::empNo)
            .forEach(userManagementStore::deleteByEmpNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<Long, AdminInfo.SignUpUserInfo> findSignUpUserScroll(Long cursorId) {
        return userManagementReader.findScroll(cursorId);
    }

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<Long, AdminInfo.UserInfo> findUserScroll(Long cursorId) {
        return userReader.findScroll(cursorId);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userReader.findById(userId);

        userStore.delete(user);

        UserSummary userSummary = userSummaryReader.findById(userId);

        userSummaryStore.update(userSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<Integer, AdminInfo.UserTaskInfo> findUserTaskScroll(AdminCommand.FindUserTaskListRequest command) {
        return userReader.findScroll(command);
    }

    @Override
    @Transactional(readOnly = true)
    public ScrollPagination<String, AdminInfo.UnassignedAdInfo> findUnassignedAdScroll(String cursorId) {
        return advertisementReader.findUnassignedAdScroll(cursorId);
    }

    @Override
    @Transactional(readOnly = true)
    public void assignTask(AdminCommand.AssignTaskRequest command) {
        AdminInfo.TaskAssignmentAmountInfo taskAssignmentAmountInfo = validateAndGetTaskAssignmentAmountInfo(command);

        List<AdminInfo.UnassignedAdIdInfo> unassignedAdvertisementList =
            advertisementReader.findAllUnassignedAdId(taskAssignmentAmountInfo.total());

        command.selectedAssigneeList()
            .parallelStream()
            .forEach(selectedAssigneeInfo -> updateAssigneeAndAdditionalTaskCount(selectedAssigneeInfo,
                unassignedAdvertisementList, taskAssignmentAmountInfo));
    }

    private AdminInfo.TaskAssignmentAmountInfo validateAndGetTaskAssignmentAmountInfo(AdminCommand.AssignTaskRequest command) {
        Long totalTaskAssignmentAmount = command.selectedAssigneeList().stream()
            .map(AdminCommand.SelectedAssigneeInfo::taskAssignmentAmount)
            .reduce(0L, Long::sum);

        Long totalUnassignedAdvertisement = advertisementReader.countUnassigned();

        if (totalUnassignedAdvertisement.compareTo(totalTaskAssignmentAmount) < 0) {
            throw new BusinessException(INSUFFICIENT_UNASSIGNED_ADVERTISEMENT);
        }

        Long baseTaskAssignmentAmount = totalTaskAssignmentAmount / command.selectedAssigneeList().size();

        command.selectedAssigneeList().stream()
            .map(AdminCommand.SelectedAssigneeInfo::taskAssignmentAmount)
            .forEach(taskAssignmentAmount -> {
                if ((taskAssignmentAmount - baseTaskAssignmentAmount) < 0 ||
                    (taskAssignmentAmount - baseTaskAssignmentAmount) > 1) {
                    throw new BusinessException(INVALID_TASK_ASSIGNMENT_AMOUNT);
                }
            });

        return AdminInfo.TaskAssignmentAmountInfo.of(totalTaskAssignmentAmount, baseTaskAssignmentAmount);
    }

    private void updateAssigneeAndAdditionalTaskCount(
        AdminCommand.SelectedAssigneeInfo selectedAssigneeInfo,
        List<AdminInfo.UnassignedAdIdInfo> unassignedAdvertisementList,
        AdminInfo.TaskAssignmentAmountInfo taskAssignmentAmountInfo
    ) {
        UserSummary userSummary = userSummaryReader.findById(selectedAssigneeInfo.id());

        List<String> personalTaskAdIdList = new ArrayList<>();

        for (long i = 0L; i < selectedAssigneeInfo.taskAssignmentAmount(); i++) {
            synchronized (unassignedAdvertisementList) {
                AdminInfo.UnassignedAdIdInfo unassignedAdIdInfo = unassignedAdvertisementList.removeLast();

                personalTaskAdIdList.add(unassignedAdIdInfo.id());
            }
        }

        advertisementStore.updateAssignee(userSummary, personalTaskAdIdList);

        if (selectedAssigneeInfo.taskAssignmentAmount().compareTo(taskAssignmentAmountInfo.base()) > 0) {
            userStore.update(selectedAssigneeInfo);
        }
    }

    @Override
    @Transactional
    public void assignTaskOriginal(AdminCommand.AssignTaskRequest command) {
        AdminInfo.TaskAssignmentAmountInfo taskAssignmentAmountInfo = validateAndGetTaskAssignmentAmountInfo(command);

        List<Advertisement> unassignedAdvertisementList =
            advertisementReader.findAllUnassignedAd(taskAssignmentAmountInfo.total());

        command.selectedAssigneeList()
            .forEach(selectedAssigneeInfo -> {
                UserSummary userSummary = userSummaryReader.findById(selectedAssigneeInfo.id());

                for (long i = 0L; i < selectedAssigneeInfo.taskAssignmentAmount(); i++) {
                    Advertisement advertisement = unassignedAdvertisementList.removeLast();
                    advertisement.updateAssignee(userSummary);
                }

                if (selectedAssigneeInfo.taskAssignmentAmount().compareTo(taskAssignmentAmountInfo.base()) > 0) {
                    userStore.update(selectedAssigneeInfo);
                }
            });
    }

    // 쿼리 개선
    @Override
    @Transactional
    public void assignTaskV1(AdminCommand.AssignTaskRequest command) {
        AdminInfo.TaskAssignmentAmountInfo taskAssignmentAmountInfo = validateAndGetTaskAssignmentAmountInfo(command);

        List<AdminInfo.UnassignedAdIdInfo> unassignedAdvertisementList =
            advertisementReader.findAllUnassignedAdId(taskAssignmentAmountInfo.total());

        command.selectedAssigneeList()
            .forEach(selectedAssigneeInfo -> {
                UserSummary userSummary = userSummaryReader.findById(selectedAssigneeInfo.id());

                List<String> personalTaskAdIdList = new ArrayList<>();

                for (long i = 0L; i < selectedAssigneeInfo.taskAssignmentAmount(); i++) {
                    AdminInfo.UnassignedAdIdInfo unassignedAdIdInfo = unassignedAdvertisementList.removeLast();

                    personalTaskAdIdList.add(unassignedAdIdInfo.id());
                }

                advertisementStore.updateAssignee(userSummary, personalTaskAdIdList);

                if (selectedAssigneeInfo.taskAssignmentAmount().compareTo(taskAssignmentAmountInfo.base()) > 0) {
                    userStore.update(selectedAssigneeInfo);
                }
            });
    }

    // 멀티 스레딩
    @Override
    @Transactional
    public void assignTaskV2(AdminCommand.AssignTaskRequest command) {
        AdminInfo.TaskAssignmentAmountInfo taskAssignmentAmountInfo = validateAndGetTaskAssignmentAmountInfo(command);

        List<Advertisement> unassignedAdvertisementList =
            advertisementReader.findAllUnassignedAd(taskAssignmentAmountInfo.total());

        command.selectedAssigneeList()
            .parallelStream()
            .forEach(selectedAssigneeInfo -> {
                UserSummary userSummary = userSummaryReader.findById(selectedAssigneeInfo.id());

                for (long i = 0L; i < selectedAssigneeInfo.taskAssignmentAmount(); i++) {
                    synchronized (unassignedAdvertisementList) {
                        Advertisement advertisement = unassignedAdvertisementList.removeLast();
                        advertisement.updateAssignee(userSummary);
                    }
                }

                if (selectedAssigneeInfo.taskAssignmentAmount().compareTo(taskAssignmentAmountInfo.base()) > 0) {
                    userStore.update(selectedAssigneeInfo);
                }
            });
    }
}
