package lab.analyzer;

import lab.model.LogEntry;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

// LogEntry: 입력 요소
// long[]: 누적기(작업 공간)
// ResponseStats: 최종 결과
// LogEntry -> 누적기 -> ResponseStats
public class ResponseStatsCollector implements Collector<LogEntry, long[], ResponseStats> {

    /*
    * Supplier: 계산 공간 만들기
    * Accumulator: 데이터 집어넣기
    * Combiner: 병렬 결과 합치기
    * Finisher: 결과 객체 생성
    * */

//    Supplier: 스트림이 새로운 작업 공간을 필요로 할 때 호출된다.
    @Override
    public Supplier<long[]> supplier() {
        return () -> new long[2]; // [count, sum]
    }

//    accumulator: 스트림의 각 요소가 여기에 흘러들어온다.
    @Override
    public BiConsumer<long[], LogEntry> accumulator() {
        return (a, log) -> {
            a[0]++;
            a[1] += log.responseTimeMs();
        };
    }

//    combiner: 병렬 스트림에서 각 스레드가 만든 누적기를 합칠 때 사용된다.
    @Override
    public BinaryOperator<long[]> combiner() {
        return (a, b) -> new long[] { a[0] + b[0], a[1] + b[1] };
    }

//    finisher: 작업 공간 -> 최종 불변 결과
    @Override
    public Function<long[], ResponseStats> finisher() {
        return a -> new ResponseStats(a[0], a[1]);
    }

//    characteristics: 아직은 스트림에게 특별한 최적화 힌트를 주지 않는다는 뜻.
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
