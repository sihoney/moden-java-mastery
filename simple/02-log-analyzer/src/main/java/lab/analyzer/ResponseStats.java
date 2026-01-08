package lab.analyzer;

public record ResponseStats(long count, long sum) {

    public double average() {
        return count == 0 ? 0 : (double) sum / count;
    }
}
