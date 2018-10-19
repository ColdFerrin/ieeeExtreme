// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

// Please name your class Main
class Main {
    public static void main (String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);

        int[][] shoecnt = new int[101][2];

        int num = in.nextInt();
        int sum = 0;

        for (int i = 0; i < num; i++) {
            int size = in.nextInt();
            char foot = in.next(".").charAt(0);

            int footi;
            if(foot == 'L'){
                footi = 1;
            }
            else
                footi = 0;

            shoecnt[size][footi] += 1;
        }

        for (int i = 0; i < 101; i++) {
            sum += Math.min(shoecnt[i][0], shoecnt[i][1]);
        }

        System.out.println(sum);

    }
}