package lab;

import lab.client.SimpleHttpClient;
import lab.model.Quote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws Exception {
        var start = System.currentTimeMillis();

////        lab 01 - step 1, 동기 방식
////        Time: 5347 ms
//        for (int i = 0; i < 10; i++) {
//            var quote = SimpleHttpClient.fetchQuoteSync();
//            System.out.println(quote);
//        }

////        lab 01 - step 2, 비동기 방식
////        Time: 2539 ms
////        비동기는 “빠른 코드”가 아니라 “대기 시간을 계산에서 제거하는 구조”다.
//        var futures = IntStream.range(0, 10)
//                .mapToObj(i -> SimpleHttpClient.fetchQuoteAsync())
//                .toList();
//        futures.forEach(f -> System.out.println(f.join()));
////        -> 대기 + 출력이 섞여 있다.

//        lab 01 - step 3, 실무형 비동기 방식
//        Time: 1696 ms
//        “비동기 호출을 그냥 여러 번 한다” → “실무에서 안전하게 관리한다”
//        var futures = IntStream.range(0, 10)
//                .mapToObj(i -> SimpleHttpClient.fetchQuoteAsync()
//                        .orTimeout(3, java.util.concurrent.TimeUnit.SECONDS)
//                        .exceptionally(ex -> {
//                            System.out.println("Request failed: " + ex.getMessage());
//                            return null;
//                        }))
//                .toList();
////        + 타임아웃: 외부 시스템이 늦으면 강제로 끊는다 -> 시스템 생존성
////        + 예외 흡수: 일부 요청 실패해도 전체 프로그램은 계속 진행
//
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
////        + 전체 동기화: 모든 작업이 끝날 때까지 대기, join 여러 번 호출하는 구조 제거
//
//        futures.stream()
//                .map(CompletableFuture::join)
//                .filter(q -> q != null)
//                .forEach(System.out::println);
////        +  결과 안전 처리: 실패한 작업은 자동 제외, 출력 안정성 확보
////        -> 대기 단계와 출력 단계가 분리된다.

//      lab 01 - step 4, Structured Task Scope
//        Time: 2746 ms
//        step 3 한계: 시작과 끝이 명확 X, 실패 전파를 파악하기 어려움
//        Structured Concurrency -> "동시 작업도 하나의 블록처럼 보이게" 만든다.
        List<Quote> results = new ArrayList<>();

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            이 블록 안에서 시작된 모든 동시 작업은 이 블록이 끝나기 전에 반드시 정리된다.

            var tasks = new ArrayList<StructuredTaskScope.Subtask<Quote>>();

            for (int i = 0; i < 10; i++) {
                tasks.add(scope.fork(SimpleHttpClient::fetchQuoteSync));
//                fork(): 새 가상 스레드 생성, 해당 작업을 스코프에 등록, 실패/취소/완료 상태를 추적
            }

            scope.join();           // 모든 작업 대기
            scope.throwIfFailed();  // 실패 전파

            for (var task : tasks) {
                results.add(task.get());
            }
        }

        results.forEach(System.out::println);

        var end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) + " ms");


    }
}
