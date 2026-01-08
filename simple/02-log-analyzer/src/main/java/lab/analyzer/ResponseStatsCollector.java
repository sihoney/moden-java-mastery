package lab.analyzer;

import lab.model.LogEntry;

import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;

public class ResponseStatsCollector implements Collector<LogEntry, long[], ResponseStats> {

    @Override
    public Supplier<long[]> supplier() {
        return () -> new long[2]; // [count, sum]
    }

    @Override
    public BiConsumer<long[], LogEntry> accumulator() {
        return (a, log) -> {
            a[0]++;
            a[1] += log.responseTimeMs();
        };
    }

    @Override
    public BinaryOperator<long[]> combiner() {
        return (a, b) -> new long[] { a[0] + b[0], a[1] + b[1] };
    }

    @Override
    public Function<long[], ResponseStats> finisher() {
        return a -> new ResponseStats(a[0], a[1]);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
