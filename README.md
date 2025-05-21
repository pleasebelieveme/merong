# merong

스파르타 플러스 노래검색프로젝트

## 🔍 검색 API 캐시 적용 내역

### ✅ 적용 대상
- `/api/songs/search?keyword=...`
- 키워드 기반의 노래 검색 기능에 **In-memory Local Cache(Caffeine)** 적용

### ✅ 캐시 방식
- Spring Cache 추상화(`@Cacheable`) 사용
- **Caffeine** 라이브러리를 통한 **JVM 내 메모리 캐시**
- TTL(Time-To-Live): 10분
- 최대 캐시 항목 수: 1,000개

### ✅ 캐시 적용 코드 위치
```java
@Cacheable(value = "songSearchCache", key = "#keyword")
public List<SongResponseDto.Get> getSearchResults(String keyword) { ... }
