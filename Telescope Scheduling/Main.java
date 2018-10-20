// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.awt.Rectangle;
import java.io.*;

// Please name your class Main
class Main {
	static Scanner sc = new Scanner(System.in);
	static ArrayList<Star> stars;
	
	public static void main (String[] args) throws Exception {
		int numStars = sc.nextInt();
		stars = new ArrayList<Star>(numStars);
		
		for (int i = 0; i < numStars; i++) {
			int start  = sc.nextInt();
			int end    = sc.nextInt();
			int desire = sc.nextInt();
			
			stars.add(new Star(start, end, desire));
		}
		
		Collections.sort(stars, (s1, s2) -> {
			return s2.shininess - s1.shininess;
		});
		
		for (int index = 0; index < stars.size(); index++) {
			Star current = stars.get(index);
			for (int searchIndex = index + 1; searchIndex < stars.size(); searchIndex++) {
				Star search = stars.get(searchIndex);
				if (search.intersects(current)) {
					if (current.shininess > search.shininess) {
						stars.remove(searchIndex);
						searchIndex--;
					}
					else {
						stars.remove(index);
						index--;
					}
					break;
				}
			}
		}
		
		int totalShininess = 0;
		for (Star s: stars)
			totalShininess += s.shininess;
		System.out.println(totalShininess);
	}
}

class Star extends Rectangle {
	int shininess;
	String id;
	
	public Star(int start, int end, int desirability) {
		super(start, 0, end - start, 1);
		this.shininess = desirability;
		id = start + " " + end + " " + desirability;
	}
	
	
	@Override
	public boolean intersects(Rectangle other) {
		return super.intersects(other) && (other.x != x + width) && (other.x + other.width != x);
	}
}