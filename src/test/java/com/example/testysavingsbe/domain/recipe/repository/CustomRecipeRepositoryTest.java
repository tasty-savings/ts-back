package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.CustomRecipe;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


@DataJpaTest
class CustomRecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("findAllEatenRecipe() ")
    class FindAllEatenCustomRecipeTest {

        @DisplayName("먹었다고 체크한 레시피만 가져온다")
        @Test
        void findAllEatenRecipeTest() {
            // given
            User mockUser = mock(User.class);
            User anotherUser = mock(User.class);
            userRepository.save(mockUser);
            userRepository.save(anotherUser);

            CustomRecipe eatenCustomRecipe = CustomRecipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            CustomRecipe eatenCustomRecipe2 = CustomRecipe.builder()
                    .user(mockUser)
                    .content("recipe content2")
                    .build();

            CustomRecipe defaultCustomRecipe = CustomRecipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();

            CustomRecipe anotherUserCustomRecipe = CustomRecipe.builder()
                    .user(anotherUser)
                    .content("another User recipe content")
                    .build();

            eatenCustomRecipe.updateEaten();
            eatenCustomRecipe2.updateEaten();
            anotherUserCustomRecipe.updateEaten();

            recipeRepository.save(eatenCustomRecipe);
            recipeRepository.save(eatenCustomRecipe2);
            recipeRepository.save(defaultCustomRecipe);
            recipeRepository.save(anotherUserCustomRecipe);


            // when
            List<CustomRecipe> customRecipeList = recipeRepository.findAllEatenRecipeByUser(mockUser);

            // then
            assertThat(customRecipeList).isNotEmpty();
            assertThat(customRecipeList.size()).isEqualTo(2);
            assertThat(customRecipeList).allMatch(customRecipe -> customRecipe.getIsEaten().equals(true));
            assertThat(customRecipeList).allMatch(customRecipe -> customRecipe.getUser().equals(mockUser));
        }

        @DisplayName("없는 경우 사이즈가 0인 리스트를 반환한다")
        @Test
        void returnEmptyListWhenUserIsNotEaten() {
            // given
            User mockUser = mock(User.class);
            userRepository.save(mockUser);
            CustomRecipe notEaten = CustomRecipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            CustomRecipe defaultCustomRecipe = CustomRecipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();
            recipeRepository.save(notEaten);
            recipeRepository.save(defaultCustomRecipe);

            // when
            List<CustomRecipe> customRecipeList = recipeRepository.findAllEatenRecipeByUser(mockUser);

            // then
            assertThat(customRecipeList).isEmpty();
        }
    }

    @DisplayName("findAllBookMarkedRecipeByUser() ")
    @Nested
    class FindAllBookMarkedCustomRecipeTest {

        @DisplayName("북마크된 레시피만 가져온다")
        @Test
        void findAllBookMarkedRecipeTest() {
            // given
            User mockUser = mock(User.class);
            User anotherUser = mock(User.class);
            userRepository.save(mockUser);
            userRepository.save(anotherUser);

            CustomRecipe eatenCustomRecipe = CustomRecipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            CustomRecipe eatenCustomRecipe2 = CustomRecipe.builder()
                    .user(mockUser)
                    .content("recipe content2")
                    .build();

            CustomRecipe defaultCustomRecipe = CustomRecipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();

            CustomRecipe anotherMockUser = CustomRecipe.builder()
                    .user(anotherUser)
                    .content("another Bookmarked Mock User Recipe")
                    .build();

            eatenCustomRecipe.updateBookMarked();
            eatenCustomRecipe2.updateBookMarked();
            anotherMockUser.updateBookMarked();
            recipeRepository.save(eatenCustomRecipe);
            recipeRepository.save(eatenCustomRecipe2);
            recipeRepository.save(defaultCustomRecipe);
            recipeRepository.save(anotherMockUser);


            // when
            List<CustomRecipe> customRecipeList = recipeRepository.findAllBookMarkedRecipeByUser(mockUser);

            // then
            assertThat(customRecipeList).isNotEmpty();
            assertThat(customRecipeList.size()).isEqualTo(2);
            assertThat(customRecipeList).allMatch(customRecipe -> customRecipe.getIsBookMarked().equals(true));
            assertThat(customRecipeList).allMatch(customRecipe -> customRecipe.getUser().equals(mockUser));
        }

        @DisplayName("먹었다고 체크한 레시피가 없을경우 빈 리스트를 반환한다")
        @Test
        void returnEmptyListWhenUserIsNotEaten() {
            // given
            User mockUser = mock(User.class);
            userRepository.save(mockUser);
            CustomRecipe notEaten = CustomRecipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            CustomRecipe defaultCustomRecipe = CustomRecipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();
            recipeRepository.save(notEaten);
            recipeRepository.save(defaultCustomRecipe);

            // when
            List<CustomRecipe> customRecipeList = recipeRepository.findAllBookMarkedRecipeByUser(mockUser);

            // then
            assertThat(customRecipeList).isEmpty();

        }

    }

}