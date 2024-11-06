package com.example.testysavingsbe.global.config;

import com.example.testysavingsbe.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PrincipalDetails implements OAuth2User {
    @Getter
    private final User user;
    private final Map<String, Object> attributes;

    public PrincipalDetails(User user , Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
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
