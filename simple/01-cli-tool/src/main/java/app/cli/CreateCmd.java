package app.cli;

import app.App;
import app.command.Create;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.time.Duration;

@Command(name = "create")
public class CreateCmd implements Runnable {

    @Option(names = "--title", required = true)
    String title;

    @Option(names = "--content", required = true)
    String content;

    // 예: --ttl 10s, --ttl 5m, --ttl 2h
    @Option(names = "--ttl", description = "Time-to-live (e.g. 10s, 5m, 2h). If omitted, note never expires.")
    Duration ttl;

    public void run() {
//      여기서 처음으로
//      문자열 → 도메인 타입 변환이 일어난다.
        App.service().handle(new Create(title, content, ttl));
    }
}

