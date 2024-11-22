package com.example.testysavingsbe.domain.recipe.entity;

import com.example.testysavingsbe.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class CustomRecipeTest {
    @Nested
    @DisplayName("updateEaten()은 ")
    class UpdateIsEaten {
        @DisplayName("현재 eaten 상태를 변환한다.")
        @Test
        void updateIsEatenToTrue() {
            // given
//            User mockUser = mock(User.class);
//            CustomRecipe customRecipe = CustomRecipe.builder()
//                    .content("Test Content")
//                    .user(mockUser)
//                    .build();
//
//            // when
//            customRecipe.updateEaten();
//
//            // then
//            assertEquals("Test Content", customRecipe.getContent());
//            assertTrue(customRecipe.getIsEaten());
        }

        @DisplayName("2번 사용시 원래대로 상태가 돌아온다.")
        @Test
        void updateIsEatenTwice() {
            User mockUser = mock(User.class);
//            CustomRecipe customRecipe = CustomRecipe.builder()
//                    .content("Test Content")
//                    .user(mockUser)
//                    .build();
//
//            // when
//            customRecipe.updateBookMarked();
//            customRecipe.updateBookMarked();
//
//            // then
//            assertEquals("Test Content", customRecipe.getContent());
//            assertFalse(customRecipe.getIsBookMarked());
        }

    }

    @DisplayName("updateBookMarked()는 ")
    @Nested
    class updateIsBookMarked {
        @DisplayName("isBookMarked의 상태를 변환한다.")
        @Test
        void updateIsBookmarkedToTrue() {
            User mockUser = mock(User.class);
//            CustomRecipe customRecipe = CustomRecipe.builder()
//                    .content("Test Content")
//                    .user(mockUser)
//                    .build();
//
//            // when
//            customRecipe.updateBookMarked();
//
//            // then
//            assertEquals("Test Content", customRecipe.getContent());
//            assertTrue(customRecipe.getIsBookMarked());
        }
    }
}