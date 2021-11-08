package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /*
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        ArrayList[] buckets = new ArrayList[M];
        for (int i = 0; i < M; i++) {
            buckets[i] = new ArrayList();
        }
        int oomageNum = oomages.size();
        double max = oomageNum / 2.5;
        double min = oomageNum / 50;
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum].add(o);
        }

        for (ArrayList bucket : buckets) {
            if (bucket.size() > max || bucket.size() < min) {
                return false;
            }
        }
        return true;
    }
}
