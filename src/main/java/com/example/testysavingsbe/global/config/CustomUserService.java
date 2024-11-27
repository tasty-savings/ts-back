package com.example.testysavingsbe.global.config;

import com.example.testysavingsbe.domain.user.entity.CookingLevel;
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
        User userEntity = userRepository.findBySocialId(socialId);
        String username = extractUsername(oAuth2User);
        if (userEntity == null) {
            userEntity = User.builder()
                .username(username)
                .socialId(socialId)
                .cookingLevel(CookingLevel.BEGINNER)
                .build();
            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }

    private String extractUsername(OAuth2User oAuth2User) {
        return Optional.ofNullable(oAuth2User.getAttribute("properties"))
            .filter(Map.class::isInstance)
            .map(Map.class::cast)
            .map(properties -> (String) properties.get("nickname"))
            .orElse("Unknown");
    }

}
