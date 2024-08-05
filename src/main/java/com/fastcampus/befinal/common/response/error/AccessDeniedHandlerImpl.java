package com.fastcampus.befinal.common.response.error;

import com.fastcampus.befinal.common.response.error.info.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
        throws IOException, ServletException {
        log.error(AuthErrorCode.DENIED_ACCESS.getMessage());
        setErrorResponse(response);
    }

    private void setErrorResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(AuthErrorCode.DENIED_ACCESS.getHttpStatus().value());

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonResponse = objectMapper.writeValueAsString(
            Map.of("code", AuthErrorCode.DENIED_ACCESS.getCode(),
                "message", AuthErrorCode.DENIED_ACCESS.getMessage())
        );

        response.getWriter().write(jsonResponse);
    }
}
