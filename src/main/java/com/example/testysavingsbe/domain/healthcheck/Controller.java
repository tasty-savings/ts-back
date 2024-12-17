package com.example.testysavingsbe.domain.healthcheck;

import com.example.testysavingsbe.global.config.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/healthcheck")
public class Controller {
    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Success!!");
    }

    @GetMapping("/userinfo")
    public ResponseEntity<String> userInfo(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity.ok(principal.toString());
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> healthCheckUser(HttpServletRequest request) {
        // 세션 확인
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
        Map<String, Object> sessionData = new HashMap<>();

        if (session == null) {
            sessionData.put("isAuthenticated", false);
            sessionData.put("message", "No session found");
            return ResponseEntity.ok(sessionData);
        }

        // SecurityContext 확인
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContext == null || securityContext.getAuthentication() == null) {
            sessionData.put("isAuthenticated", false);
            sessionData.put("message", "User is not authenticated");
            return ResponseEntity.ok(sessionData);
        }

        // 인증된 사용자 정보 가져오기
        Authentication authentication = securityContext.getAuthentication();
        sessionData.put("isAuthenticated", authentication.isAuthenticated());
        sessionData.put("username", authentication.getName()); // 사용자 이름
        sessionData.put("authorities", authentication.getAuthorities()); // 권한 정보

        return ResponseEntity.ok(sessionData);
    }


}
