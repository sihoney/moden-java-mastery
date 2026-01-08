package lab.analyzer;

import lab.model.LogEntry;

import java.util.HashMap;
import java.util.Map;

// 스트리밍 분석에서는 “데이터”가 아니라
// “상태(State)”를 누적해야 한다.
public final class AnalyzerState {

    long errorCount;
    long totalResponse;
    long count;

    final Map<String, Long> byLevel = new HashMap<>();
    final Map<String, Long> byIp = new HashMap<>();

    void accept(LogEntry log) {
        if (log.level().equals("ERROR")) errorCount++;

        byLevel.merge(log.level(), 1L, Long::sum);
        byIp.merge(log.ip(), 1L, Long::sum);

        totalResponse += log.responseTimeMs();
        count++;
    }

    AnalysisResult finish() {
        var avg = count == 0 ? 0 : (double) totalResponse / count;

        var topIp = byIp.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        return new AnalysisResult(errorCount, avg, byLevel, topIp);
    }
}
