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

    public static int randint(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static void main(String[] args) {

        int runs = 1;
        int width = 9;
        int height = 6;
        int mod = 7;

        Stopwatch stopwatch = new Stopwatch();
        ArrayList<Double> times = new ArrayList<>();

        int[][] res = null;
        String v = "rowreduce({";

        for (int i=0;i<runs;i++) {
            int[][] a = new int[height][width];
            for (int r=0;r<height;r++) {
                for (int c=0;c<width;c++) {
                    int rand = randint(0,mod-1);
                    a[r][c] = rand;
                    String cm = (c==width-1) ? ((r==height-1) ? "" : ";") : ",";
                    v += (rand + cm);
                }
            }
            v += "})";
            MatrixSpace d = new MatrixSpace(a,mod);
            stopwatch.start();
            res = d.REFM();
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
        System.out.println("V: " + v);
        for (int[] i : res) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

}


