import java.util.*;
import java.lang.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        for (int k = 0; k < m; k++) {
            int n = in.nextInt()
            int firstStatus = 0;
            int status = 0;
            boolean[] answer = new boolean[n];
            boolean[] toCheck = new boolean[n];
            for (int i = 0; i < n + 1; ++i) {
                StringBuilder toPrint = new StringBuilder();
                toPrint.append("Q");
                for (int j = 0; j < answer.length; j++) {
                    toPrint.append(" ");
                    toPrint.append((toCheck[j]) ? 1 : 0);
                }
                System.out.println(toPrint);
                System.out.flush();

                firstStatus = status;
                status = in.nextInt();


                if (i > 0) {
                    if (status > firstStatus) {
                        answer[i - 1] = true;
                    } else
                        answer[i - 1] = false;
                }

                if (i < n) {
                    toCheck[i] = true;
                }
                if (i != 0 && firstStatus > status) {
                    toCheck[i - 1] = false;
                }
            }
            StringBuilder toPrint = new StringBuilder();
            toPrint.append("A");
            for (int j = 0; j < answer.length; j++) {
                toPrint.append(" ");
                toPrint.append((answer[j]) ? 1 : 0);
            }
            System.out.println(toPrint);
            System.out.flush();
        }
    }
}