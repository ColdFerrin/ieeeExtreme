package failures;
// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main2 {
	static Scanner sc = new Scanner(System.in);
	static HashMap<String, Division> divs = new HashMap<String, Division>();
	
	
	public static void main(String[] args) {
		int n = sc.nextInt();
		int q = sc.nextInt();
		sc.nextLine();
		
		// read in info
		for (int i = 0; i < n; i++) {
			String divisionName = sc.next();
			String parentDivision = sc.next();
			int sz1 = sc.nextInt();
			int sz2 = sc.nextInt();
			sc.nextLine();
			
			divs.put(divisionName, new Division(parentDivision, sz1, sz2));
		}
		
		// read in query
		for (int i = 0; i < q; i++) {
			String divisionName = sc.next();
			int type = sc.nextInt();
			sc.nextLine();
			
			query(divisionName, type);
		}
		
		
		
		for (String s: divs.keySet()) {
			Division current = divs.get(s);
		    System.out.format("%15s | %15s | %5d | %5d\n",
		    		s, current.extending, current.numEmployees, current.numEmployeesTotal);
		}
	}
	
	
	static void query(String name, int type) {
		Division current = divs.get(name);
		switch (type) {
			case 1:
				if (current.numEmployees == 0) {
					System.out.println("1 " + current.findEmployeeUpperBound());
				}
				else {
					System.out.println(current.numEmployees + " " + current.numEmployees);
				}
				break;
			case 2:
				if (current.numEmployeesTotal == 0) {
					System.out.println("1 " + current.findEmployeeTotalUpperBound());
				}
				else {
					System.out.println(current.numEmployeesTotal + " " + current.numEmployeesTotal);
				}
				break;
		}
	}

}

class Division {
	String extending;
	ArrayList<Division> children;
	int numEmployees;
	int numEmployeesTotal;
	int employeesUsed;
	
	public Division(String parent, int numEmployees, int numEmployeesTotal) {
		children = new ArrayList<Division>();
		employeesUsed = numEmployees;
		
		if (!parent.equals("NONE"))
			this.extending = parent;
		
		this.numEmployees = numEmployees;
		Division parentDiv = Main2.divs.get(parent);
		if (parentDiv != null) {
			parentDiv.children.add(this);
			parentDiv.takeEmployees(numEmployees);
		}
		
		this.numEmployeesTotal = numEmployeesTotal;
	}
	
	
	public int findEmployeeUpperBound() {
		int parentEmployeesUnassigned = getParentUnassigned();
		
		setEmployees(parentEmployeesUnassigned);
		
		return numEmployees;
	}
	public int findEmployeeTotalUpperBound() {
		return Math.min(employeesUsed, getParentUnassigned());
	}
	private int getParentUnassigned() {
		Division parent = Main2.divs.get(extending);
		return parent.unassignedEmployeeCount();
	}
	private int getChildUnassigned() {
		int childrenEmployeeCount = 0;
		
		for (Division child: children)
			childrenEmployeeCount += child.numEmployeesTotal;
		
		return childrenEmployeeCount;
	}
	
	
	public int unassignedEmployeeCount() {
		return numEmployeesTotal - employeesUsed;
	}
	public void takeEmployees(int amount) {
		employeesUsed += amount;
	}
	private void setEmployees(int amount) {
		Division parent = Main2.divs.get(extending);
		numEmployees = amount;
		parent.takeEmployees(numEmployees);
	}
}
