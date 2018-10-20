// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

// Please name your class Main
class Main {
	static int[][] tipping;
	static int numLetters;
	static int weightings;
	
	
	public static void main (String[] args) throws java.lang.Exception {
	    Scanner sc = new Scanner(System.in);
		String[] initVals = sc.nextLine().split(",");
		numLetters = Integer.parseInt(initVals[0]);
		weightings = Integer.parseInt(initVals[1]);
		
		tipping = new int[weightings][numLetters];
		
		for (int i = 0; i < weightings; i++) {
			String[] sample = sc.nextLine().split("-");
			adjustTipping(sample, i);
		}
		
printArr(tipping);

		StringBuilder b = new StringBuilder();
		for (int var1 = 0; var1 < numLetters - 1; var1++) {
			for (int var2 = var1 + 1; var2 < numLetters; var2++) {
				for (int var3 = var2; var3 < numLetters; var3++) {
					for (int var4 = var3 + 1; var4 < numLetters; var4++) {
						if (testDoubleTippings(var1, var2, var3, var4)) {
							char c1 = (char) (var1 + 'A'),
								 c2 = (char) (var2 + 'A'),
								 c3 = (char) (var3 + 'A'),
								 c4 = (char) (var4 + 'A');
							b.append(c1 + "" + c2 + "=" + c3 + "" + c4);
						}
					}
				}
			}
		}
		System.out.println(b.toString());
	}
	
	
	static boolean testDoubleTippings(int var1, int var2, int var3, int var4) {
		for (int i = 0; i < weightings; i++) {
			int sum12 = tipping[i][var1] + tipping[i][var2];
			int sum34 = tipping[i][var3] + tipping[i][var4];
			if (sum12 != sum34)
				return false;
		}
		
		return true;
	}
	static void adjustTipping(String[] sample, int weightingNumber) {
		for (char c: sample[0].toCharArray()) {
			tipping[weightingNumber][c - 'A'] = -1;
		}
		for (char c: sample[1].toCharArray()) {
			tipping[weightingNumber][c - 'A'] = 1;
		}
	}
	
	static int factorial(int number) {
		if (number == 2)
			return 2;
		else
			return number * factorial(number - 1);
	}
	
	
	
	static void printArr(int[][] arr) {
		for (int i = 0; i < arr[0].length; i++)
			System.out.format("  %c ", (char) ('A' + i));
		System.out.println();
		
		for (int[] row: arr) {
			for (int i: row)
				System.out.format("%3d ", i);
			System.out.println();
		}
	}
}
