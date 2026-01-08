package lab;

import lab.analyzer.LogAnalyzer;
import lab.io.LogLoader;
import lab.tools.LogGenerator;

import java.nio.file.Path;

// App -- 데이터 흐름 오케스트레이션
// 목적: 시스템 전체의 데이트 흐름 연결
// - 데이터 파이프라인: 입력 -> 변환 -> 분석 -> 출력
// - 역할 분리: 각 레이어 책임 명확
// - 시스템 사고: 코드가 아닌 '흐름'으로 설계
public class App {
    public static void main(String[] args) throws Exception {

//        var logs = LogLoader.load(Path.of("logs/app.log")).toList();

//        lab01
//        System.out.println("Errors: " + LogAnalyzer.countErrors(logs));

//        lab02
//        System.out.println("Avg Response: " + LogAnalyzer.averageResponseTime(logs));
//        System.out.println("By Level: " + LogAnalyzer.countByLevel(logs));
//        System.out.println("Top IP: " + LogAnalyzer.topIp(logs).orElse("N/A"));

//        lab03
//        var stats = LogAnalyzer.responseStats(logs);
//        System.out.println("Avg(ResponseStats): " + stats.average()); // Avg(ResponseStats): 233.33333333333334

//        lab04
//        var result = LogAnalyzer.analyze(logs);
//        System.out.println(result); // AnalysisResult[errorCount=1, avgResponse=233.33333333333334, countByLevel={ERROR=1, INFO=2}, topIp=192.168.0.1]

//        lab05
        var bigFile = Path.of("logs/big.log");
        LogGenerator.generate(bigFile, 5_000_000);

        var start = System.currentTimeMillis();

//         ❌ 메모리를 다 먹는 방식 (메모리 적재 방식)
//         var logs = LogLoader.load(bigFile).toList();
//         var result = LogAnalyzer.analyze(logs);

//        설명
//        stream은 지연 실행이지만 toList() 순간 전부 메모리에 들어간다.
//        -> stream을 써도 수집하면 끝이다.

//        결과
//        AnalysisResult[errorCount=2497725, avgResponse=499.4860048, countByLevel={ERROR=2497725, INFO=2502275}, topIp=192.168.0.57]
//        Time: 8810 ms

//         ✅ 스트리밍 방식 (backpressure 체감 실험)
        try (var stream = LogLoader.load(bigFile)) {
            var result = LogAnalyzer.analyzeStream(stream);
            System.out.println(result);
        }

//        설명
//        toList() 사용 X + try-with-resources 사용
//        여기서는 한 줄 일고 -> 처리하고 -> 바로 버린다.
//        즉, 메모리는 항상 작게 유지된다.

//        결과
//        AnalysisResult[errorCount=2499225, avgResponse=0.0, countByLevel={}, topIp=N/A]
//        Time: 6238 ms

        var end = System.currentTimeMillis();

//        System.out.println(result);
        System.out.println("Time: " + (end - start) + " ms");
    }
}
