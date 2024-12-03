package com.example.testysavingsbe.global.config;

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

    public static final String KAKAO_SOCIAL_ID_PROPERTY = "id";
    public static final String KAKAO_NAME_PROPERTY = "nickname";
    public static final String KAKAO_GENDER_PROPERTY = "gender";
    public static final String KAKAO_AGE_GROUP_PROPERTY = "age_range";
    public static final String KAKAO_PROPERTIES_KEY = "properties";
    public static final String KAKAO_ACCOUNT_KEY = "kakao_account";
    public static final String UNKNOWN_VALUE = "Unknown";
    private final UserRepository userRepository;

    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Long socialId = oAuth2User.getAttribute(KAKAO_SOCIAL_ID_PROPERTY);
        String username = extractArgument(oAuth2User, KAKAO_NAME_PROPERTY);
        String gender = extractArgument(oAuth2User, KAKAO_GENDER_PROPERTY);

        User userEntity = userRepository.findBySocialId(socialId)
            .orElseGet(() -> {
                User newUser = User.builder()
                    .username(username)
                    .socialId(socialId)
                    .cookingLevel(CookingLevel.BEGINNER)
                    .gender(Gender.of(gender))
                    .build();
                return userRepository.save(newUser);
            });
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }

    private String extractArgument(OAuth2User oAuth2User, String argument) {
        String attributeKey =
            argument.equals(KAKAO_NAME_PROPERTY) ? KAKAO_PROPERTIES_KEY : KAKAO_ACCOUNT_KEY;

        return Optional.ofNullable(oAuth2User.getAttribute(attributeKey))
            .filter(Map.class::isInstance)
            .map(Map.class::cast)
            .map(properties -> (String) properties.get(argument))
            .orElse(UNKNOWN_VALUE);
    }

}
