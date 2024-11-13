package com.example.testysavingsbe.domain.user.controller;

import com.example.testysavingsbe.domain.user.dto.request.SetUserTypesRequest;
import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.service.UserInfoSettingUseCase;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/userinfo")
@RestController
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoSettingUseCase userInfoSettingUseCase;

    @PostMapping("/prefer")
    public ResponseEntity<UserPreferTypeResponse> setUserPreferUserType(@AuthenticationPrincipal PrincipalDetails principal,
                                                   @Valid @RequestBody SetUserTypesRequest request){
        UserPreferTypeResponse userPreferTypeResponse = userInfoSettingUseCase.registerPreferType(new UserInfoSettingUseCase.
                SettingPreferTypeRequest(principal.getUser(), request.types()));
        return ResponseEntity.ok(userPreferTypeResponse);
    }
}
