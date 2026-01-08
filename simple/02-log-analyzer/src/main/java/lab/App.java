package lab;

import lab.analyzer.LogAnalyzer;
import lab.io.LogLoader;

import java.nio.file.Path;

// App -- 데이터 흐름 오케스트레이션
// 목적: 시스템 전체의 데이트 흐름 연결
// - 데이터 파이프라인: 입력 -> 변환 -> 분석 -> 출력
// - 역할 분리: 각 레이어 책임 명확
// - 시스템 사고: 코드가 아닌 '흐름'으로 설계
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Working Dir = " + System.getProperty("user.dir"));

        var logs = LogLoader.load(Path.of("logs/app.log")).toList();

        System.out.println("Errors: " + LogAnalyzer.countErrors(logs));
    }
}
