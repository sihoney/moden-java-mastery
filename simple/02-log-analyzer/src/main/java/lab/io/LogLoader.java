package lab.io;

import lab.model.LogEntry;
import lab.parser.LogParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

// LogLoader -- 파일 -> 객체 스트림
// 목적: 파일을 '데이터 스트림'으로 변환
// 학습 포인트: '데이터는 모으는 것이 아니라 흐르게 해야 한다.'
public class LogLoader {

    public static Stream<LogEntry> load(Path path) throws IOException {
        return Files.lines(path).map(LogParser::parse);
//        Files.lines(): 지연 실행
//        Stream 반환
//        terminal operation(toList, count, collect, forEach)이 호촐될 때만 작동
    }
}
