package com.example.testysavingsbe.domain.food.docs;

import com.example.testysavingsbe.docs.RestDocsSupport;
import com.example.testysavingsbe.domain.food.controller.FoodController;
import com.example.testysavingsbe.domain.food.dto.FoodRegisterRequest;
import com.example.testysavingsbe.domain.food.dto.FoodResponse;
import com.example.testysavingsbe.domain.food.dto.FoodUpdateRequest;
import com.example.testysavingsbe.domain.food.service.usecase.FoodCommandUseCase;
import com.example.testysavingsbe.domain.food.service.usecase.FoodQueryUseCase;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FoodDocs extends RestDocsSupport {
    private final FoodCommandUseCase foodCommandUseCase = mock(FoodCommandUseCase.class);
    private final FoodQueryUseCase foodQueryUseCase = mock(FoodQueryUseCase.class);

    @Override
    public Object initController() {
        return new FoodController(foodCommandUseCase, foodQueryUseCase);
    }

    @BeforeEach
    void setUp() {
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @DisplayName("대시보드 음식 가져오기")
    @Test
    void getAllUserFoods() throws Exception {
        when(foodQueryUseCase.getAllFoods(any()))
                .thenReturn(List.of(
                        FoodResponse.builder()
                                .id(1L)
                                .foodName("대파")
                                .savingType("REFRIGERATED")
                                .expirationDate(LocalDate.parse("2024-11-23"))
                                .build(),
                        FoodResponse.builder()
                                .id(2L)
                                .foodName("냉동치킨")
                                .savingType("FROZEN")
                                .expirationDate(LocalDate.parse("2025-03-01"))
                                .build()
                ));

        mockMvc.perform(get("/food/my_foods").with(oauth2Login())
                ).andExpect(status().isOk())
                .andDo(document("food/my_foods",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("레시피 아이디"),
                                fieldWithPath("[].foodName").type(JsonFieldType.STRING).description("음식이름"),
                                fieldWithPath("[].savingType").type(JsonFieldType.STRING).description("저장되는 형식 [FROZEN(냉동), REFRIGERATED(냉장), ROOM_TEMPERATURE(상온)]"),
                                fieldWithPath("[].expirationDate").type(JsonFieldType.STRING).description("유통기한")
                        )
                ));
    }

    @DisplayName("음식 등록하기")
    @Test
    void registerFood() throws Exception {
        FoodRegisterRequest requestDto = new FoodRegisterRequest(
                "대파", "REFRIGERATED", LocalDate.parse("2024-11-23")
        );

        when(foodCommandUseCase.registerFood(any(), any(FoodCommandUseCase.RegisterFoodRequest.class)))
                .thenReturn(
                        FoodResponse.builder()
                                .id(1L)
                                .foodName("대파")
                                .savingType("REFRIGERATED")
                                .expirationDate(LocalDate.parse("2024-11-23"))
                                .build()
                );

        mockMvc.perform(post("/food").with(oauth2Login())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andDo(document("food/register",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("foodName").type(JsonFieldType.STRING).description("음식이름"),
                                fieldWithPath("savingType").type(JsonFieldType.STRING).description("저장되는 형식 [FROZEN(냉동), REFRIGERATED(냉장), ROOM_TEMPERATURE(상온)]"),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("유통기한")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("foodName").type(JsonFieldType.STRING).description("음식이름"),
                                fieldWithPath("savingType").type(JsonFieldType.STRING).description("저장되는 형식"),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("유통기한")
                        )
                ));
    }

    @DisplayName("음식을 삭제한다.")
    @Test
    void deleteFood() throws Exception {
        mockMvc.perform(delete("/food/{foodId}", 1).with(oauth2Login())
                ).andExpect(status().isOk())
                .andDo(document("food/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodId").description("음식 id")
                        )));
    }

    @DisplayName("음식의 저장형식과 유통기한을 변경한다.")
    @Test
    void updateFood() throws Exception {
        FoodUpdateRequest request = new FoodUpdateRequest("FROZEN", LocalDate.parse("2024-11-23"));

        when(foodCommandUseCase.updateFood(any(), any(FoodCommandUseCase.UpdateFoodRequest.class)))
                .thenReturn(
                        FoodResponse.builder()
                                .id(1L)
                                .foodName("대파")
                                .savingType("FROZEN")
                                .expirationDate(LocalDate.parse("2024-11-23"))
                                .build()
                );

        mockMvc.perform(put("/food/{foodId}", 1).with(oauth2Login())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andDo(document("food/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodId").description("음식 id")
                        ),
                        requestFields(
                                fieldWithPath("savingType").type(JsonFieldType.STRING).description("저장되는 형식 [FROZEN(냉동), REFRIGERATED(냉장), ROOM_TEMPERATURE(상온)]"),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("유통기한")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("foodName").type(JsonFieldType.STRING).description("음식이름"),
                                fieldWithPath("savingType").type(JsonFieldType.STRING).description("저장되는 형식"),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("유통기한")
                        )));
    }

}
