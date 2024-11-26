package com.example.testysavingsbe.domain.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class UserTypesValidator implements ConstraintValidator<ValidUserType, List<String>> {

    private static final Set<String> VALID_TYPES = Set.of(
        "건강식", "고단백", "한식", "주식", "채식", "저칼로리", "반찬", "해산물",
        "간편식", "구이", "전통식", "고섬유", "새콤달콤한맛", "볶음", "생식", "저당식",
        "무침", "양식", "매운맛1단계", "매운맛2단계", "매운맛3단계", "매운맛4단계", "매운맛5단계",
        "저염식", "퓨전", "담백한맛", "국", "찜", "디저트", "샐러드", "저탄수화물",
        "가벼운", "튀김", "비타민", "조림", "소화촉진", "음료", "장건강", "체중조절",
        "중식", "부침", "무기질", "삶기", "특별식", "죽", "글루텐프리", "전",
        "일식", "스프"
    );

    @Override
    public boolean isValid(List<String> types, ConstraintValidatorContext context) {
        if (types == null || types.isEmpty()) {
            return true; // @NotEmpty와 함께 사용하지 않는 경우
        }
        // 모든 값이 VALID_TYPES에 포함되어 있는지 검증
        return VALID_TYPES.containsAll(types);
    }
}
