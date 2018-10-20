// Don't place your source in a package
import java.util.*;
import java.lang.*;

// Please name your class Main
class Main {
    public static void main (String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();

        for (int num = 0; num < a; num++){
            int sum = in.nextInt();
            int[] set = new int[in.nextInt()];
            for (int i = 0; i < set.length; i++) {
                set[i] = in.nextInt();
            }
            int[] sumdig = findSum(set, sum);

            if(sumdig[0] == 0){
                System.out.println("!OK");
            }
            else {
                System.out.println(sumdig[0] + " " + sumdig[1]);
            }
        }
    }

    static int[] findSum (int[] set, int sum){
        CandidateSum candidateSum = new CandidateSum();
        for (int i = 0; i < set.length; i++) {
            for (int j = i + 1; j < set.length; j++) {
                if (set[i] + set[j] == sum && j < candidateSum.getyPos()){
                    int[] vals = new int[] {set[i], set[j]};
                    Arrays.sort(vals);
                    candidateSum.setValues(vals);
                    candidateSum.setyPos(j);
                }
            }
        }
        if(candidateSum.getyPos() != Integer.MAX_VALUE) {
            return candidateSum.getValues();
        }
        else {
            return new int[] {0};
        }
    }
}

class CandidateSum {
    private int[] values;
    private int yPos;

    public CandidateSum() {
        yPos = Integer.MAX_VALUE;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}