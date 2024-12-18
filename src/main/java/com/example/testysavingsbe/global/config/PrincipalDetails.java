package com.example.testysavingsbe.global.config;

import com.example.testysavingsbe.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PrincipalDetails implements OAuth2User {
    private final User user;

    private final Map<String, Object> attributes;

    public PrincipalDetails(User user , Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public User getUser() {
        if (this.user == null) {
            throw new SessionAuthenticationException("만료된 세션이거나 유저가 존재하지 않습니다.");
        }
        return user;
    }

    public Long getUserId(){
        return this.user.getId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return this.user.getUsername();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User: [");
        sb.append(this.getName());
        sb.append("]");
        sb.append("Attributes: ");
        sb.append(this.getAttributes());
        sb.append("]");
        return sb.toString();
    }
}
