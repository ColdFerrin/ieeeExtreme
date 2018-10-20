// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

// Please name your class Main
class Main {
	static Scanner sc = new Scanner(System.in);
	static MyHash nums = new MyHash();
	static int operations = 0;
	static int length;
	
	public static void main (String[] args) throws Exception {
		length = sc.nextInt() + 1;
		
		for (int i = 0; i < length - 1; i++) {
			int key = sc.nextInt();
			nums.increment(key);
			while (nums.get(key) > 1) {
				sort(key);
			}
		}
		
		System.out.println(operations);
	}
	
	static void sort(int key) {
		int closestIndex = findClosestOpening(key);
		operations += Math.abs(closestIndex - key);
		nums.increment(closestIndex);
		nums.decrement(key);
	}
	
	static int findClosestOpening(int index) {
		for (int highIndex = index + 1, lowIndex = index - 1; ; highIndex++, lowIndex--) {
			if (!nums.containsKey(highIndex))
				return lowIndex;
			
			if (!nums.containsKey(lowIndex))
				return highIndex;
		}
	}
}

class MyHash extends HashMap<Integer, Integer> {
	public int increment(int key) {
		if (containsKey(key)) {
			put(key, get(key) + 1);
		}
		else {
			put(key, 1);
		}
		
		return get(key);
	}
	
	public int decrement(int key) {
		if (containsKey(key)) {
			put(key, get(key) - 1);
		}
		else {
			put(key, 0);
		}
		
		return get(key);
	}
}