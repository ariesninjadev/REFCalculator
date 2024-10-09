import java.util.ArrayList;

class Stopwatch {
    private long startTime;
    private long stopTime;
    private boolean running;

    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = System.nanoTime() - startTime;
        } else {
            elapsed = stopTime - startTime;
        }
        return elapsed;
    }

    // Convert nanoseconds to milliseconds
    public long getElapsedTimeMillis() {
        return getElapsedTime() / 1_000_000;
    }

    // Convert nanoseconds to seconds
    public double getElapsedTimeSeconds() {
        return getElapsedTime() / 1_000_000_000.0;
    }
}

public class StopwatchClient {
    public static void main(String[] args) {

        int runs = 1;

        Stopwatch stopwatch = new Stopwatch();
        ArrayList<Double> times = new ArrayList<>();

        int[][] r = null;

        for (int i=0;i<runs;i++) {
            stopwatch.start();
            int[][] a = {{2,2,3,5,6},{5,4,3,2,0},{6,1,2,0,4}};
            MatrixSpace d = new MatrixSpace(a,7);
            r = d.REFM();
            stopwatch.stop();
            times.add(stopwatch.getElapsedTimeSeconds());
        }

        double sum = 0.0;
        for (double num : times) {
            sum += num;
        }
        double avg = sum / times.size();

        // Print the elapsed time in nanoseconds, milliseconds, and seconds
        System.out.println("Time: " + avg);

        for (int[] i : r) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

}


