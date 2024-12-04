package com.example.testysavingsbe.domain.user.entity;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    @DisplayName("유저가 생성될때 setPreferType이 false이여야 한다.")
    @Test
    void preferTypeIsFalseWhenUserCreate(){
        // given
        User user = new User();

        assertThat(user.getSetPreferType()).isFalse();
        assertThat(user.getSetPreferType()).isNotEqualTo(true);
    }


    @DisplayName("doneUserPreferType()을 사용하면 true로 나온다.")
    @Test
    void updatePreferTypeSuccess(){
        // given
        User user = new User();

        // then
        user.doneUserPreferType();

        // when
        then(user.getSetPreferType()).isTrue();
    }


    @DisplayName("doneUserPreferType()을 여러번 호출하더라도 true가 나온다.")
    @Test
    void ifDoneUserPreferTypeRepeat(){
        // given
        User user = new User();

        // when
        user.doneUserPreferType();
        user.doneUserPreferType();

        // then
        then(user.getSetPreferType()).isTrue();
    }
}