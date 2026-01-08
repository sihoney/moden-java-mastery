package lab.model;

import java.time.Instant;

// LogEntry -- 불변 데이터 모델링
// 목적: 현실의 로그 한 줄을 "도메인 객체"로 바꾸는 작업
public record LogEntry(
        Instant timestamp,
        String level,
        String message,
        String ip,
        long responseTimeMs
) {}
