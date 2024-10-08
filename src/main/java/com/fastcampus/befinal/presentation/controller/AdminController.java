package com.fastcampus.befinal.presentation.controller;

import com.fastcampus.befinal.application.facade.AdminFacade;
import com.fastcampus.befinal.common.response.AppApiResponse;
import com.fastcampus.befinal.common.response.ResponseEntityFactory;
import com.fastcampus.befinal.common.util.DefaultGroupSequence;
import com.fastcampus.befinal.presentation.dto.AdminDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.fastcampus.befinal.common.response.success.info.AdminSuccessCode.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 관련 API")
public class AdminController {
    private final AdminFacade adminFacade;

    @PostMapping("/approve-user")
    @Operation(summary = "회원가입 승인")
    @ApiResponse(responseCode = "200", description = "회원가입 승인되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1000, " +
                    "\"message\": \"회원가입 승인되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> approveUser(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AdminDto.ApproveUserRequest request
    ) {
        adminFacade.approveUser(request);
        return ResponseEntityFactory.toResponseEntity(APPROVE_USER_SUCCESS);
    }

    @PostMapping("/reject-user")
    @Operation(summary = "회원가입 반려")
    @ApiResponse(responseCode = "200", description = "회원가입 반려되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1002, " +
                    "\"message\": \"회원가입 반려되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> rejectUser(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AdminDto.RejectUserRequest request
    ) {
        adminFacade.rejectUser(request);
        return ResponseEntityFactory.toResponseEntity(REJECT_USER_SUCCESS);
    }

    @GetMapping("/approve-user")
    @Operation(summary = "회원가입 신청 유저 목록 조회 - Param default 값은 null")
    @ApiResponse(responseCode = "200", description = "회원가입 신청 유저 목록 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                                "\"code\": 1001, " +
                                "\"message\": \"회원가입 신청 유저 목록 조회되었습니다.\", " +
                                "\"data\": {" +
                                        "\"totalElements\": \"\"," +
                                        "\"currentCursorId\": \"\"," +
                                        "\"contents\":[" +
                                            "{" +
                                                "\"cursorId\": 2," +
                                                "\"name\": \"박길동\"," +
                                                "\"empNo\": \"11111113\"," +
                                                "\"phoneNumber\": \"01011114444\"," +
                                                "\"email\": \"parkgil@hong.com\"," +
                                                "\"signUpRequestDateTime\": \"2024-09-03 01:32\"" +
                                            "}" +
                                        "]" +
                                    "}" +
                                "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<AdminDto.FindSignUpUserListResponse>> findSignUpUserList(
        @RequestParam(required = false)
        Long cursorId
    ) {
        AdminDto.FindSignUpUserListResponse response = adminFacade.findSignUpUserScroll(cursorId);
        return ResponseEntityFactory.toResponseEntity(FIND_SIGN_UP_USER_LIST_SUCCESS, response);
    }

    @GetMapping("/manage-user")
    @Operation(summary = "회원 정보 목록 조회 - Param default 값은 null")
    @ApiResponse(responseCode = "200", description = "회원 정보 목록이 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1003, " +
                    "\"message\": \"회원 정보 목록이 조회되었습니다.\", " +
                    "\"data\": {" +
                        "\"totalElements\": \"3\"," +
                        "\"currentCursorId\": \"1\"," +
                        "\"contents\":[" +
                                "{" +
                                    "\"cursorId\": 1," +
                                    "\"empNo\": \"11111113\"," +
                                    "\"name\": \"박길동\"," +
                                    "\"authority\": \"작업자\"," +
                                    "\"userId\": \"aaaa\"," +
                                    "\"phoneNumber\": \"01011114444\"," +
                                    "\"email\": \"parkgil@hong.com\"," +
                                    "\"signUpDate\": \"2024-09-02\"," +
                                    "\"finalLoginDateTime\": \"2024-09-03 01:32\"" +
                                "}" +
                            "]" +
                        "}" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse<AdminDto.FindUserListResponse>> findUserList(
        @RequestParam(required = false)
        Long cursorId
    ) {
        AdminDto.FindUserListResponse response = adminFacade.findUserScroll(cursorId);
        return ResponseEntityFactory.toResponseEntity(FIND_USER_LIST_SUCCESS, response);
    }

    @DeleteMapping("/manage-user/{userId}")
    @Operation(summary = "회원 정보 삭제")
    @ApiResponse(responseCode = "200", description = "회원 정보가 삭제되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = "{ " +
                    "\"code\": 1004, " +
                    "\"message\": \"회원 정보가 삭제되었습니다.\"" +
                    "}"
            )
        )
    )
    public ResponseEntity<AppApiResponse> deleteUser(
        @PathVariable
        Long userId
    ) {
        adminFacade.deleteUser(userId);
        return ResponseEntityFactory.toResponseEntity(DELETE_USER_SUCCESS);
    }

    @PostMapping("/manage-emp")
    @Operation(summary = "작업자 관리 정보 조회")
    @ApiResponse(responseCode = "200", description = "작업자 관리 정보 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                    {
                        "code": 1005,
                        "message": "작업자 관리 정보 조회되었습니다.",
                        "data": {
                            "totalElements": 3,
                            "currentCursorId": 1,
                            "contents": [
                                {
                                    "id": 1,
                                    "empNo": "11111111",
                                    "name": "홍길동",
                                    "totalAd": 345,
                                    "notDoneAd": 45,
                                    "DoneAd": 300,
                                    "doneRatio": 87
                                }
                            ]
                        }
                    }
                    """
            )
        )
    )
    public ResponseEntity<AppApiResponse<AdminDto.FindUserTaskListResponse>> findUserTaskList(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AdminDto.FindUserTaskListRequest request
    ) {
        AdminDto.FindUserTaskListResponse response = adminFacade.findUserTaskScroll(request);
        return ResponseEntityFactory.toResponseEntity(FIND_USER_TASK_LIST_SUCCESS, response);
    }

    @GetMapping("/manage-task/advertisement")
    @Operation(summary = "작업 배분 광고 목록 조회")
    @ApiResponse(responseCode = "200", description = "작업 배분 광고 목록이 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                    {
                        "code": 1006,
                        "message": "작업 배분 광고 목록이 조회되었습니다.",
                        "data": {
                            "totalElements": 3,
                            "currentCursorId": "202409A12345",
                            "contents": [
                                {
                                    "adId": "N00001",
                                    "product": "상품_202409N00001",
                                    "advertiser": "광고주_810",
                                    "category": "과자"
                                }
                            ]
                        }
                    }
                    """
            )
        )
    )
    public ResponseEntity<AppApiResponse<AdminDto.FindUnassignedAdListResponse>> findUnassignedAdList(
        @RequestParam(required = false)
        String cursorId
    ) {
        AdminDto.FindUnassignedAdListResponse response = adminFacade.findUnassignedAdScroll(cursorId);
        return ResponseEntityFactory.toResponseEntity(FIND_UNASSIGNED_AD_LIST_SUCCESS, response);
    }

    @GetMapping("/manage-task/emp")
    @Operation(summary = "작업 배분 작업자 목록 조회")
    @ApiResponse(responseCode = "200", description = "작업 배분 작업자 목록이 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                    {
                        "code": 1007,
                        "message": "작업 배분 작업자 목록이 조회되었습니다.",
                        "data": {
                            "assigneeList": [
                                {
                                    "id": 1,
                                    "empNo": "12312312",
                                    "name": "홍길동",
                                    "additionalTaskCount": 0
                                }
                            ]
                        }
                    }
                    """
            )
        )
    )
    public ResponseEntity<AppApiResponse<AdminDto.FindAssigneeListResponse>> findAssigneeList() {
        AdminDto.FindAssigneeListResponse response = adminFacade.findAssigneeList();
        return ResponseEntityFactory.toResponseEntity(FIND_ASSIGNEE_LIST_SUCCESS, response);
    }

    @PostMapping("/manage-task")
    @Operation(summary = "작업 배분 완료")
    @ApiResponse(responseCode = "200", description = "작업 배분이 완료되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                    {
                        "code": 1008,
                        "message": "작업 배분이 완료되었습니다."
                    }
                    """
            )
        )
    )
    public ResponseEntity<AppApiResponse> assignTask(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AdminDto.AssignTaskRequest request
    ) {
        adminFacade.assignTask(request);
        return ResponseEntityFactory.toResponseEntity(ASSIGN_TASK_SUCCESS);
    }

    @PostMapping("/manage-emp/detail")
    @Operation(summary = "작업자 관리 작업 상세 정보")
    @ApiResponse(responseCode = "200", description = "작업자 관리 상세 정보 조회되었습니다.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                example = """
                    {
                        "code": 1009,
                        "message": "작업자 관리 상세 정보 조회되었습니다.",
                        "data": {
                            "userDetailInfo" : {
                                "name" : "홍길동",
                                "role" : "작업자"
                            },
                            "userTaskDetailInfo" : {
                                "totalElements": 3,
                                "currentCursorId": "202409N00147",
                                "contents" : [
                                    {
                                        "adId": "N00147",
                                        "media": "문화일보",
                                        "category": "금융",
                                        "product": "상품_202409N00147",
                                        "advertiser": "광고주_619"
                                    }
                                ]
                            }
                        }
                     }
                    """
            )
        )
    )
    public ResponseEntity<AppApiResponse<AdminDto.AdminFindUserDetailResponse>> findUserTaskDetailList(
        @RequestBody
        @Validated(DefaultGroupSequence.class)
        AdminDto.FindUserTaskDetailListRequest request
    ) {
        AdminDto.AdminFindUserDetailResponse response = adminFacade.findUserTaskDetailScroll(request);
        return ResponseEntityFactory.toResponseEntity(FIND_USER_TASK_DETAIL_LIST_SUCCESS, response);
    }
}

