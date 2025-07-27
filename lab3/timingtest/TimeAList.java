package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int baseNumber = 1000;
        int count = 7;

        for (int i = 0; i <= count; i += 1) {
            Ns.addLast(baseNumber);
            times.addLast(helperTime(baseNumber));
            opCounts.addLast(baseNumber);
            baseNumber *= 2;
        }


        printTimingTable(Ns, times, opCounts);
    }

    public static Double helperTime(int tmp) {

        AList<Integer> count = new AList<>();

        Stopwatch sw = new Stopwatch();

        for (int i = 0; i < tmp; i += 1) {
            count.addLast(i);
        }

        double timeInSeconds = sw.elapsedTime();
        return timeInSeconds;
    }

}
