package lab.client;

import lab.model.Quote;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleHttpClient {

    private static final HttpClient client =
            HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

    private static final Pattern ADVICE_PATTERN =
            Pattern.compile("\"advice\"\\s*:\\s*\"(.*?)\"");

//    lab 01 - 동기 방식
    public static Quote fetchQuoteSync() throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.adviceslip.com/advice"))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var body = response.body();

        return new Quote(extractAdvice(body), "System");
    }

//    lab 01 - 비동기 방식
    public static CompletableFuture<Quote> fetchQuoteAsync() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.adviceslip.com/advice"))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> new Quote(extractAdvice(body), "System"));
    }

    private static String extractAdvice(String json) {
        Matcher m = ADVICE_PATTERN.matcher(json);
        if (!m.find()) return "";

        // advice 값 안의 \" 같은 이스케이프를 최소 복원
        return m.group(1).replace("\\\"", "\"");
    }
}
