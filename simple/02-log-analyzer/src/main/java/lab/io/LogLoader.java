package lab.io;

import lab.model.LogEntry;
import lab.parser.LogParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

// LogLoader -- 파일 -> 객체 스트림
// 목적: 파일을 '데이터 스트림'으로 변환
// - 파일 -> 객체 스트림: IO를 도메인 흐름으로 바꾸는 사고
// - 스트림 사용: 대량 데이터 처리 기본기
// '데이터는 모으는 것이 아니라 흐르게 해야 한다.'
// 지연 실행: 아직 아무 일도 일어나지 않음 (Files.lines(path) 부분)
public class LogLoader {

    public static Stream<LogEntry> load(Path path) throws IOException {
        return Files.lines(path)
                .map(LogParser::parse);
    }
}
