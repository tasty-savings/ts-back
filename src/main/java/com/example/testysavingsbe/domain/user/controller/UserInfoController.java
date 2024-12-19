package com.example.testysavingsbe.domain.user.controller;

import com.example.testysavingsbe.domain.user.dto.request.DeleteUserTypeRequest;
import com.example.testysavingsbe.domain.user.dto.request.PhysicalInfoRegisterRequest;
import com.example.testysavingsbe.domain.user.dto.request.RegisterAllergyRequest;
import com.example.testysavingsbe.domain.user.dto.request.SetUserTypesRequest;
import com.example.testysavingsbe.domain.user.dto.response.CheckSetUserInfoResponse;
import com.example.testysavingsbe.domain.user.dto.response.RegisteredAllergyResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserCookingLevelResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserInfoResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserPreferTypeResponse;
import com.example.testysavingsbe.domain.user.dto.response.UserSpicyLevelResponse;
import com.example.testysavingsbe.domain.user.entity.CookingLevel;
import com.example.testysavingsbe.domain.user.entity.SpicyLevel;
import com.example.testysavingsbe.domain.user.service.UserInfoSettingUseCase;
import com.example.testysavingsbe.domain.user.service.UserinfoQueryUseCase;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RequestMapping("/userinfo")
@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserinfoQueryUseCase userinfoQueryUseCase;
    private final UserInfoSettingUseCase userInfoSettingUseCase;


    @GetMapping
    public ResponseEntity<UserInfoResponse> getUserinfo(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // TODO: gender, activitylevel, height, weight -> null이면 null로 전송 2024. 12. 16. by kong
        UserInfoResponse response = userinfoQueryUseCase.getUserInfo(principalDetails.getUser());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/me/preferences")
    public ResponseEntity<?> getUserPreferInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        UserPreferTypeResponse userPreferInfo = userinfoQueryUseCase.getUserPreferInfo(
            principalDetails.getUser());

        return ResponseEntity.ok(userPreferInfo);
    }

    @DeleteMapping("/preferences")
    public ResponseEntity<Void> deleteUserPreferInfo(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody DeleteUserTypeRequest request
    ) {
        userInfoSettingUseCase.deletePreferType(principalDetails.getUser(), request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me/preferences/status")
    public ResponseEntity<CheckSetUserInfoResponse> checkSetUserPrefer(@AuthenticationPrincipal PrincipalDetails principalDetails){
        CheckSetUserInfoResponse response = userinfoQueryUseCase.checkSetUserPrefer(
            principalDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/health/status")
    public ResponseEntity<CheckSetUserInfoResponse> getRegisteredAllergy(@AuthenticationPrincipal PrincipalDetails principalDetails){
        CheckSetUserInfoResponse response = userinfoQueryUseCase.checkSetUserHealthInfo(principalDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/prefer")
    public ResponseEntity<UserPreferTypeResponse> setUserPreferUserType(
        @AuthenticationPrincipal PrincipalDetails principal,
        @Valid @RequestBody SetUserTypesRequest request) {
        UserPreferTypeResponse userPreferTypeResponse = userInfoSettingUseCase.registerPreferType(
            new UserInfoSettingUseCase.SettingPreferTypeRequest(principal.getUser(),
                request.types()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userPreferTypeResponse);
    }


    @PostMapping("/allergy")
    public ResponseEntity<RegisteredAllergyResponse> registerAllergy(
        @AuthenticationPrincipal PrincipalDetails principal,
        @RequestBody RegisterAllergyRequest request) {
        List<String> allergy = userInfoSettingUseCase.registerAllergy(principal.getUser(),
            request.allergy());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new RegisteredAllergyResponse(allergy));
    }


    @PutMapping("/settings/spicy-level")
    public ResponseEntity<UserSpicyLevelResponse> updateUserSpicyLevel(
        @AuthenticationPrincipal PrincipalDetails principal, @RequestParam("value") int spicyLevel)
        throws BadRequestException {
        if (spicyLevel < 0 || spicyLevel > 5) {
            throw new BadRequestException("Spicy level must be between 0 and 5");
        }
        String response = userInfoSettingUseCase.updateSpicyLevel(principal.getUser(),
            SpicyLevel.of(spicyLevel));

        return ResponseEntity.ok(new UserSpicyLevelResponse(response));
    }

    @PutMapping("/settings/cooking-level")
    public ResponseEntity<UserCookingLevelResponse> updateUserCookingLevel(
        @AuthenticationPrincipal PrincipalDetails principal,
        @RequestParam("value") String cookingLevel) {
        String response = userInfoSettingUseCase.updateCookingLevel(principal.getUser(),
            CookingLevel.fromDisplayName(cookingLevel));
        return ResponseEntity.ok(new UserCookingLevelResponse(response));
    }


    @PutMapping("/setting/physical")
    public ResponseEntity<Void> updateUserSpicyLevel(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestBody @Valid PhysicalInfoRegisterRequest request
        ) {
        userInfoSettingUseCase.updateUserPhysicalAttribute(principalDetails.getUser(), request);
        return ResponseEntity.noContent().build();
    }

}
