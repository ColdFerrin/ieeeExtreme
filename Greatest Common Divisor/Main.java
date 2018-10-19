// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int a = in.nextInt();
        int b = in.nextInt();

        int gcd = GCD (a,b);

        System.out.println(gcd);
    }

    private static int GCD(int a, int b){

        if (a==0||b==0){
            return 0;
        }
        if (a == b){
            return a;
        }
        if (a > b)
            return GCD(a-b, b);
        return GCD(a, b-a);

    }
}
