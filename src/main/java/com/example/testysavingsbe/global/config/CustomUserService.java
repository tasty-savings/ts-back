package com.example.testysavingsbe.global.config;

import com.example.testysavingsbe.domain.user.entity.AgeGroup;
import com.example.testysavingsbe.domain.user.entity.CookingLevel;
import com.example.testysavingsbe.domain.user.entity.Gender;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class CustomUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Long socialId = oAuth2User.getAttribute("id");
        String username = extractUsername(oAuth2User);
        String gender = extractArgument(oAuth2User, "gender");
        String ageRange = extractArgument(oAuth2User, "age_range");


        User userEntity = userRepository.findBySocialId(socialId)
            .orElseGet(() -> {
                User newUser = User.builder()
                    .username(username)
                    .socialId(socialId)
                    .cookingLevel(CookingLevel.BEGINNER)
                    .gender(Gender.of(gender))
                    .ageRange(AgeGroup.fromAgeGroup(ageRange))
                    .build();
                return userRepository.save(newUser);
            });
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }

    private String extractArgument(OAuth2User oAuth2User, String argument) {
        return Optional.ofNullable(oAuth2User.getAttributes().get("kakao_account"))
            .filter(Map.class::isInstance)
            .map(Map.class::cast)
            .map(properties -> (String) properties.get(argument))
            .orElse("Unknown");
    }

    private String extractUsername(OAuth2User oAuth2User) {
        return Optional.ofNullable(oAuth2User.getAttribute("properties"))
            .filter(Map.class::isInstance)
            .map(Map.class::cast)
            .map(properties -> (String) properties.get("nickname"))
            .orElse("Unknown");
    }

}
