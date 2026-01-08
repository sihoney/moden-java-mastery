package lab.tools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Random;

// 대용량 로그 생성기
public class LogGenerator {

    public static void generate(Path path, int lines) throws IOException {
        var rnd = new Random();

        try (BufferedWriter w = Files.newBufferedWriter(path)) {
            for (int i = 0; i < lines; i++) {
                var line = "%s %s Action%d 192.168.0.%d %d"
                        .formatted(
                                Instant.now(),
                                rnd.nextBoolean() ? "INFO" : "ERROR",
                                i,
                                rnd.nextInt(255),
                                rnd.nextInt(1000)
                        );
                w.write(line);
                w.newLine();
            }
        }
    }
}
