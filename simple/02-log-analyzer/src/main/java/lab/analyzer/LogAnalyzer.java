package lab.analyzer;

import lab.model.LogEntry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// LogAnalyzer -- 함수형 파이프라인
// 목적: 데이터를 '선언적으로' 처리
// - 함수형 사고
// - side effect 제거
// - 파이프라인 설계
// '어떻게 처리할지 말고, 무엇을 처리할지만 말한다.'
public class LogAnalyzer {

//    0. ERROR 개수 집계
    public static long countErrors(List<LogEntry> logs) {
        return logs.stream()
                .filter(l -> l.level().equals("ERROR"))
                .count();
    }

//    1. 평균 응답 시간 계산
    public static double averageResponseTime(List<LogEntry> logs) {
        return logs.stream()
                .mapToLong(LogEntry::responseTimeMs)
                .average()
                .orElse(0);
    }

//    2. 레벨별 요청 수 집계
    public static Map<String, Long> countByLevel(List<LogEntry> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::level,
                        Collectors.counting()
                ));
    }

//    3. 가장 많이 등장한 IP 찾기
    public static Optional<String> topIp(List<LogEntry> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::ip,
                        Collectors.counting()
                ))
                .entrySet().stream() // Map은 직접 max를 못 구하니까 Entry Set(집합)으로 바꿔서 다시 스트림화
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

//    lab3
    public static ResponseStats responseStats(List<LogEntry> logs) {
        return logs.stream().collect(new ResponseStatsCollector());
    }

//    lab4 - 불변성 데이터 모델
//    - 데이터 처리 파이프라인에서 side effect(부작용)를 완전히 제거
    public static AnalysisResult analyze(List<LogEntry> logs) {

        var errorCount = logs.stream()
                .filter(l -> l.level().equals("ERROR"))
                .count();

        var stats = responseStats(logs);

        var byLevel = logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::level,
                        Collectors.counting()
                ));

        var topIp = logs.stream()
                .collect(Collectors.groupingBy(
                        LogEntry::ip,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        return new AnalysisResult(
                errorCount,
                stats.average(),
                byLevel,
                topIp
        );
    }

//    lab05
//    public static AnalysisResult analyzeStream(Stream<LogEntry> stream) {
//
//        var errorCount = stream.filter(l -> l.level().equals("ERROR")).count();
//
//        return new AnalysisResult(errorCount, 0, Map.of(), "N/A");
//    }

//    스트리밍 방식 분석 (상태을 누적)
    public static AnalysisResult analyzeStream(Stream<LogEntry> stream) {

        var state = new AnalyzerState();

        stream.forEach(state::accept);

        return state.finish();
    }

}
