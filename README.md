/Orders(10248) → "키가 10248인 그 1건". SQL WHERE 키 = 10248 LIMIT 1. 키 컬럼 전용.

임의 컬럼 매칭은 (...) 아니라 $filter 씀.

둘 차이

┌────────────────┬───────────────────────────────────────┬──────────────────────────┬──────────────┐
│      목적      │                 OData                 │           SQL            │     결과     │
├────────────────┼───────────────────────────────────────┼──────────────────────────┼──────────────┤
│ 키로 단건      │ /Orders(10248)                        │ WHERE OrderID=10248      │ 객체 1개     │
├────────────────┼───────────────────────────────────────┼──────────────────────────┼──────────────┤
│ 임의 컬럼 매칭 │ /Orders?$filter=CustomerID eq 'ALFKI' │ WHERE CustomerID='ALFKI' │ 배열 (0~N개) │
└────────────────┴───────────────────────────────────────┴──────────────────────────┴──────────────┘

GET /Orders(10248)                          → { OrderID:10248, ... }     단건
GET /Orders?$filter=CustomerID eq 'ALFKI'   → { value:[ {...},{...} ] }  목록

- (...) = 무조건 키. 키 아닌 값 넣으면 에러/404.
- $filter = 아무 컬럼이나 조건.

$filter 문법

┌─────────────┬────────────────────────────────────────────┐
│    연산     │                     예                     │
├─────────────┼────────────────────────────────────────────┤
│ 같음        │ $filter=Country eq 'Germany'               │
├─────────────┼────────────────────────────────────────────┤
│ 다름        │ $filter=Country ne 'USA'                   │
├─────────────┼────────────────────────────────────────────┤
│ 비교        │ $filter=Freight gt 100 (gt/ge/lt/le)       │
├─────────────┼────────────────────────────────────────────┤
│ AND/OR      │ $filter=Country eq 'USA' and Freight gt 50 │
├─────────────┼────────────────────────────────────────────┤
│ 문자열 함수 │ $filter=contains(CompanyName,'Market')     │
├─────────────┼────────────────────────────────────────────┤
│ startswith  │ $filter=startswith(CustomerID,'A')         │
└─────────────┴────────────────────────────────────────────┘

문자열 값은 작은따옴표 '...', 숫자는 그냥.

내 코드 현황 — $filter 아직 없음

지금 생성된 Service:
- getByKey(10248, expand) → 키 단건 ✅
- list(expand) → 전체 목록 ✅ (필터 없음)

$filter 미지원. 컬럼 조건 조회 하려면 추가해야 함.

추가하면 이렇게 됨:
// Service
// Service
public List<OrderResponse> list(String filter, String expand) {
.uri(uri -> uri.path("/"+ENTITY_SET)
.queryParamIfPresent("$filter", Optional.ofNullable(filter))
.queryParamIfPresent("$expand", Optional.ofNullable(expand)).build())
}
// REST
GET /api/Orders?filter=CustomerID eq 'ALFKI'&expand=Order_Details

같이 자주 쓰는 OData 옵션 4종 한번에 넣을까?
- $filter (조건)
- $select (컬럼 고르기)
- $orderby (정렬)
- $top/$skip (페이징)

넣을까?
