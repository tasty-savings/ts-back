package com.example.testysavingsbe.domain.food.service;

import com.example.testysavingsbe.domain.food.repository.FoodInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@EnableCaching
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class FoodServiceTest {
    @Mock
    private FoodInfoRepository foodInfoRepository;

    @Autowired
    private CacheManager cacheManager;

    @InjectMocks
    private FoodService foodService;


    @BeforeEach
    void setUp() {
        cacheManager.getCache("foodInfoCache").clear();
    }

    @DisplayName("searchFood()는 ")
    @Nested
    class FoodSearchTest{

        @DisplayName("")
        @Test
        void searchFood_cacheMiss(){

        }
    }

}