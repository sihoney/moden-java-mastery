package lab.analyzer;

import java.util.Map;

public record AnalysisResult(
        long errorCount,
        double avgResponse,
        Map<String, Long> countByLevel,
        String topIp
) {}
