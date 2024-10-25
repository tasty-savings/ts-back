package com.example.testysavingsbe.domain.recipe.repository;

import com.example.testysavingsbe.domain.recipe.entity.Recipe;
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
class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("findAllEatenRecipe() ")
    class FindAllEatenRecipeTest {

        @DisplayName("먹었다고 체크한 레시피만 가져온다")
        @Test
        void findAllEatenRecipeTest() {
            // given
            User mockUser = mock(User.class);
            User anotherUser = mock(User.class);
            userRepository.save(mockUser);
            userRepository.save(anotherUser);

            Recipe eatenRecipe = Recipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            Recipe eatenRecipe2 = Recipe.builder()
                    .user(mockUser)
                    .content("recipe content2")
                    .build();

            Recipe defaultRecipe = Recipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();

            Recipe anotherUserRecipe = Recipe.builder()
                    .user(anotherUser)
                    .content("another User recipe content")
                    .build();

            eatenRecipe.updateEaten();
            eatenRecipe2.updateEaten();
            anotherUserRecipe.updateEaten();

            recipeRepository.save(eatenRecipe);
            recipeRepository.save(eatenRecipe2);
            recipeRepository.save(defaultRecipe);
            recipeRepository.save(anotherUserRecipe);


            // when
            List<Recipe> recipeList = recipeRepository.findAllEatenRecipeByUser(mockUser);

            // then
            assertThat(recipeList).isNotEmpty();
            assertThat(recipeList.size()).isEqualTo(2);
            assertThat(recipeList).allMatch(recipe -> recipe.getIsEaten().equals(true));
            assertThat(recipeList).allMatch(recipe -> recipe.getUser().equals(mockUser));
        }

        @DisplayName("없는 경우 사이즈가 0인 리스트를 반환한다")
        @Test
        void returnEmptyListWhenUserIsNotEaten() {
            // given
            User mockUser = mock(User.class);
            userRepository.save(mockUser);
            Recipe notEaten = Recipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            Recipe defaultRecipe = Recipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();
            recipeRepository.save(notEaten);
            recipeRepository.save(defaultRecipe);

            // when
            List<Recipe> recipeList = recipeRepository.findAllEatenRecipeByUser(mockUser);

            // then
            assertThat(recipeList).isEmpty();
        }
    }

    @DisplayName("findAllBookMarkedRecipeByUser() ")
    @Nested
    class FindAllBookMarkedRecipeTest {

        @DisplayName("북마크된 레시피만 가져온다")
        @Test
        void findAllBookMarkedRecipeTest() {
            // given
            User mockUser = mock(User.class);
            User anotherUser = mock(User.class);
            userRepository.save(mockUser);
            userRepository.save(anotherUser);

            Recipe eatenRecipe = Recipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            Recipe eatenRecipe2 = Recipe.builder()
                    .user(mockUser)
                    .content("recipe content2")
                    .build();

            Recipe defaultRecipe = Recipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();

            Recipe anotherMockUser = Recipe.builder()
                    .user(anotherUser)
                    .content("another Bookmarked Mock User Recipe")
                    .build();

            eatenRecipe.updateBookMarked();
            eatenRecipe2.updateBookMarked();
            anotherMockUser.updateBookMarked();
            recipeRepository.save(eatenRecipe);
            recipeRepository.save(eatenRecipe2);
            recipeRepository.save(defaultRecipe);
            recipeRepository.save(anotherMockUser);


            // when
            List<Recipe> recipeList = recipeRepository.findAllBookMarkedRecipeByUser(mockUser);

            // then
            assertThat(recipeList).isNotEmpty();
            assertThat(recipeList.size()).isEqualTo(2);
            assertThat(recipeList).allMatch(recipe -> recipe.getIsBookMarked().equals(true));
            assertThat(recipeList).allMatch(recipe -> recipe.getUser().equals(mockUser));
        }

        @DisplayName("먹었다고 체크한 레시피가 없을경우 빈 리스트를 반환한다")
        @Test
        void returnEmptyListWhenUserIsNotEaten() {
            // given
            User mockUser = mock(User.class);
            userRepository.save(mockUser);
            Recipe notEaten = Recipe.builder()
                    .user(mockUser)
                    .content("recipe content")
                    .build();
            Recipe defaultRecipe = Recipe.builder()
                    .user(mockUser)
                    .content("Default recipe content")
                    .build();
            recipeRepository.save(notEaten);
            recipeRepository.save(defaultRecipe);

            // when
            List<Recipe> recipeList = recipeRepository.findAllBookMarkedRecipeByUser(mockUser);

            // then
            assertThat(recipeList).isEmpty();

        }

    }

}