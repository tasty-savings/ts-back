package com.example.testysavingsbe.domain.recipe.docs;

import com.example.testysavingsbe.docs.RestDocsSupport;
import com.example.testysavingsbe.domain.recipe.controller.RecipeController;
import com.example.testysavingsbe.domain.recipe.dto.request.RecipeSearchByMenuNameRequest;
import com.example.testysavingsbe.domain.recipe.dto.response.RecipeResponse;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeCommandUseCase;
import com.example.testysavingsbe.domain.recipe.service.usecase.RecipeQueryUseCase;
import com.example.testysavingsbe.domain.user.entity.User;
import com.example.testysavingsbe.global.config.PrincipalDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomRecipeDocs extends RestDocsSupport {
    private final RecipeQueryUseCase recipeQueryUseCase = mock(RecipeQueryUseCase.class);
    private final RecipeCommandUseCase recipeCommandUseCase = mock(RecipeCommandUseCase.class);

    private User user;
    private PrincipalDetails principalDetails;

    @Override
    public Object initController() {
        return new RecipeController(recipeCommandUseCase, recipeQueryUseCase);
    }

    @BeforeEach
    void setup() {
        user = mock(User.class);
        principalDetails = new PrincipalDetails(user, Map.of());
    }

    @DisplayName("메뉴 이름으로 레시피 생성 API")
    @Test
    void getRecipeByMenuName() throws Exception {
        RecipeSearchByMenuNameRequest requestDto = new RecipeSearchByMenuNameRequest("갈비찜");

        when(recipeCommandUseCase.generateRecipe(any(RecipeCommandUseCase.RecipeGenerateServiceRequest.class)))
                .thenReturn(RecipeResponse.builder()
                        .id(1L)
                        .content("갈비찜 레시피입니다 .....")
                        .userName("test user")
                        .isBookMarked(false)
                        .isEaten(false)
                        .build());

        mockMvc.perform(post("/recipe").with(oauth2Login())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("recipe/create/menu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("menuName").type(JsonFieldType.STRING).description("메뉴 이름")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("isEaten").type(JsonFieldType.BOOLEAN).description("먹었습니다 여부"),
                                fieldWithPath("isBookMarked").type(JsonFieldType.BOOLEAN).description("북마크 여부"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저 이름")
                        )
                ));
    }

    @DisplayName("북마크 OR 먹은 레시피 가져오기")
    @Test
    void getRecipesByQuery() throws Exception {
        when(recipeQueryUseCase.getRecipeByQuery(any(), any()))
                .thenReturn(List.of(
                        RecipeResponse.builder()
                                .id(1L)
                                .content("북마크 레시피1")
                                .isEaten(false)
                                .isBookMarked(true)
                                .userName("test user")
                                .build(),
                        RecipeResponse.builder()
                                .id(2L)
                                .content("북마크 레시피2")
                                .isEaten(true)
                                .isBookMarked(true)
                                .userName("다른 유저")
                                .build()
                ));

        mockMvc.perform(get("/recipe")
                        .queryParam("type", "BOOKMARK")
                ).andExpect(status().isOk())
                .andDo(document("recipe/get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("type").description("BOOKMARK or EATEN")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].isEaten").type(JsonFieldType.BOOLEAN).description("먹었습니다 여부"),
                                fieldWithPath("[].isBookMarked").type(JsonFieldType.BOOLEAN).description("북마크 여부"),
                                fieldWithPath("[].userName").type(JsonFieldType.STRING).description("유저 이름")
                        )
                ));

    }


    @DisplayName("레시피 북마크 하기")
    @Test
    void updateBookmarkRecipe() throws Exception {
        when(recipeCommandUseCase.bookmarkRecipe(any()))
                .thenReturn(RecipeResponse.builder()
                        .id(1L)
                        .content("북마크 레시피1")
                        .isEaten(false)
                        .isBookMarked(true)
                        .userName("test user")
                        .build());

        mockMvc.perform(put("/recipe/bookmark/{recipeId}", 1))
                .andExpect(status().isOk())
                .andDo(document("recipe/update/bookmark",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("recipeId").description("레시피 id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("isEaten").type(JsonFieldType.BOOLEAN).description("먹었습니다 여부"),
                                fieldWithPath("isBookMarked").type(JsonFieldType.BOOLEAN).description("북마크 여부"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저 이름")
                        )
                ));

    }


    @DisplayName("먹은 레시피 체크하기")
    @Test
    void updateEatenRecipe() throws Exception {
        when(recipeCommandUseCase.checkEatRecipe(any()))
                .thenReturn(RecipeResponse.builder()
                        .id(1L)
                        .content("먹은 레시피1")
                        .isEaten(true)
                        .isBookMarked(false)
                        .userName("test user")
                        .build());


        mockMvc.perform(put("/recipe/eat/{recipeId}", 1))
                .andExpect(status().isOk())
                .andDo(document("recipe/update/eat",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("recipeId").description("레시피 id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("isEaten").type(JsonFieldType.BOOLEAN).description("먹었습니다 여부"),
                                fieldWithPath("isBookMarked").type(JsonFieldType.BOOLEAN).description("북마크 여부"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저 이름")
                        )
                ));

    }
}

