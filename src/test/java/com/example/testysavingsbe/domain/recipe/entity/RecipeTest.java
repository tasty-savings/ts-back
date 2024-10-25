package com.example.testysavingsbe.domain.recipe.entity;

import com.example.testysavingsbe.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class RecipeTest {
    @Nested
    @DisplayName("updateEaten()은 ")
    class UpdateIsEaten {
        @DisplayName("현재 eaten 상태를 변환한다.")
        @Test
        void updateIsEatenToTrue() {
            // given
            User mockUser = mock(User.class);
            Recipe recipe = Recipe.builder()
                    .content("Test Content")
                    .user(mockUser)
                    .build();

            // when
            recipe.updateEaten();

            // then
            assertEquals("Test Content", recipe.getContent());
            assertTrue(recipe.getIsEaten());
        }

        @DisplayName("2번 사용시 원래대로 상태가 돌아온다.")
        @Test
        void updateIsEatenTwice() {
            User mockUser = mock(User.class);
            Recipe recipe = Recipe.builder()
                    .content("Test Content")
                    .user(mockUser)
                    .build();

            // when
            recipe.updateBookMarked();
            recipe.updateBookMarked();

            // then
            assertEquals("Test Content", recipe.getContent());
            assertFalse(recipe.getIsBookMarked());
        }

    }

    @DisplayName("updateBookMarked()는 ")
    @Nested
    class updateIsBookMarked {
        @DisplayName("isBookMarked의 상태를 변환한다.")
        @Test
        void updateIsBookmarkedToTrue() {
            User mockUser = mock(User.class);
            Recipe recipe = Recipe.builder()
                    .content("Test Content")
                    .user(mockUser)
                    .build();

            // when
            recipe.updateBookMarked();

            // then
            assertEquals("Test Content", recipe.getContent());
            assertTrue(recipe.getIsBookMarked());
        }
    }
}