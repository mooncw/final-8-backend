package com.fastcampus.befinal.common.filter;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtAuthService jwtAuthService;

    private final List<String> permitAllPathList = List.of(
        "/api/v1/auth/signin",
        "/api/v1/auth/reissue",
        "/api/v1/auth/signup",
        "/api/v1/auth/check-id",
        "/api/v1/auth/cert-no",
        "/api/health-check",
        "/actuator/prometheus",
        "/actuator/health",
        "/actuator/info",
        "/api/v1/auth/check-cert-no",
        "/api/v1/filter-options/media",
        "/api/v1/filter-options/category"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String jwt = jwtAuthService.resolveAuthorizationHeader(request);
            jwtAuthService.setAuthentication(jwt);
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, BusinessException e) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(e.getErrorCode().getHttpStatus().value());

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonResponse = objectMapper.writeValueAsString(
            Map.of("code", e.getErrorCode().getCode(),
                "message", e.getErrorCode().getMessage())
        );

        response.getWriter().write(jsonResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();

        return permitAllPathList.stream().anyMatch(servletPath::contains);
    }
}
