package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int baseNumber = 1000;
        int count = 7;
        int ops = 10000;

        for (int i = 0; i <= count; i += 1) {
            SLList<Integer> tmp = initList(baseNumber);
            Ns.addLast(baseNumber);
            times.addLast(helperTime(ops, tmp));
            opCounts.addLast(ops);
            baseNumber = baseNumber * 2;
        }

        printTimingTable(Ns, times, opCounts);

    }

    public static Double helperTime(int ops, SLList slList) {
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < ops; i += 1) {
            slList.getLast();
        }
        double timeInSeconds = sw.elapsedTime();
        return timeInSeconds;
    }

    public static SLList<Integer> initList(int tmp) {

        SLList<Integer> newList = new SLList<>();

        for (int i = 0; i < tmp; i += 1) {
            newList.addLast(i);
        }

        return newList;
    }

}
