package lab.analyzer;

import lab.model.LogEntry;

import java.util.List;

// LogAnalyzer -- 함수형 파이프라인
// 목적: 데이터를 '선언적으로' 처리
// - 함수형 사고
// - side effect 제거
// - 파이프라인 설계
// '어떻게 처리할지 말고, 무엇을 처리할지만 말한다.'
public class LogAnalyzer {

    public static long countErrors(List<LogEntry> logs) {
        return logs.stream()
                .filter(l -> l.level().equals("ERROR"))
                .count();
    }
}
