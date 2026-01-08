package lab.parser;

import lab.model.LogEntry;

import java.time.Instant;

// LogParser -- 비구조적 텍스트 -> 구조적 도메인 객체
// - 경계 분리: 파싱 로직은 비즈니스 분석과 완전히 분리
// - 도메인 진입점 설계
// - 학습 포인트: '입력은 더럽다. 시스템 내부는 항상 깨끗해야 한다.'
public class LogParser {

    public static LogEntry parse(String line) {
        // 2026-01-07T12:00:01Z INFO UserLogin 192.168.1.10 123
        var parts = line.split(" ");

        return new LogEntry(
                Instant.parse(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                Long.parseLong(parts[4])
        );
    }
}
