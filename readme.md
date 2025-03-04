# 맛있는 절약

### 📌 프로젝트 개요
맛있는 절약은 사용자의 식재료, 영양 정보, 선호도를 기반으로 맞춤형 레시피를 추천하고, 관리하는 서비스입니다.

사용자는 오리지널 레시피를 검색하고 추천을 받을 수 있으며, 자신만의 커스텀 레시피를 생성하거나 공유할 수도 있습니다.

### 🎯 목표
- 사용자의 냉장고 속 재료를 활용한 맞춤형 레시피 추천
- 사용자의 영양 정보와 선호도 기반으로 개인화된 레시피 제공
- RAG(Reinforcement Augmented Generation) 기반 AI 추천 시스템 활용
- 간편한 레시피 검색, 저장 및 공유 기능 제공

### 🛠️ 기술스택
<img width="1091" alt="스크린샷 2025-02-08 오후 3 08 49" src="https://github.com/user-attachments/assets/5c4f4554-1fc2-44f5-ba88-fa60530e24c0" />

### ⚙️ 아키텍쳐
<img width="1102" alt="스크린샷 2025-02-08 오후 3 20 13" src="https://github.com/user-attachments/assets/5b4fba5b-44cc-408c-8ce8-b2aa1b8c2a46" />


### 📖 주요기능

**1️⃣ 카카오 소셜 로그인 (간편 인증)**
- 카카오 계정으로 간편 로그인하여 서비스를 빠르게 이용 가능

**2️⃣ 레시피 검색 & 저장**
- 오리지널 레시피 & 사용자 커스텀 레시피 검색 가능
- 원하는 레시피를 북마크하여 저장
- 레시피 먹기 기록 기능 제공 (소비한 레시피 기록)

**3️⃣ AI 맞춤형 레시피**
- 사용자가 AI를 통해 레시피를 생성하고 수정 가능
- 기존 레시피를 냉장고 속 재료에 맞게 커스텀
- 레시피를 다른 사용자와 공유 URL을 통해 공유가능

### 🚀 트러블슈팅
- **Redis에 List<DTO> 형태로 데이터 저장시 DeSerialize시 제네릭 타입소거 발생**
    - List<T> 형태의 데이터 저장 시 타입 정보가 소거되는 문제가 발생
    - 기존 GenericJackson2JsonRedisSerializer 사용 시 직렬화된 데이터에 @class 메타데이터가 포함되나, 이후 클래스 수정시 버전 문제 발생
    - **해결 방법**: Custom Redis Serializer (RedisGsonSerializer)를 구현하여 Gson을 활용한 직렬화/역직렬화 처리
    - https://github.com/tasty-savings/ts-back/wiki/Redis-Serialize-DeSerialize

