package com.example.testysavingsbe.domain.healthcheck;

import com.example.testysavingsbe.global.config.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
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

}
