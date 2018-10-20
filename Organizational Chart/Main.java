// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {
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
			
			divs.put(divisionName, new Division(divisionName, parentDivision, sz1, sz2));
		}

		print();
		
		// read in query
		for (int i = 0; i < q; i++) {
			String divisionName = sc.next();
			int type = sc.nextInt();
			sc.nextLine();
			
			query(divisionName, type);
		}
		
		print();
	}
	
	
	static void print() {
		System.out.println();
		for (String s: divs.keySet()) {
			Division current = divs.get(s);
		    System.out.format("%15s | %5d | %5d | %5d\n",
		    		s,
		    		current.employees, current.employeeTotal, current.unassignedEmployeeCount());
		}
		System.out.println();
	}
	
	
	static void query(String name, int type) {
		Division current = divs.get(name);
		switch (type) {
			case 1:
				if (current.employees == 0) {
					System.out.println("1 " + current.findEmployeeUpperBound());
				}
				else {
					System.out.println(current.employees + " " + current.employees);
				}
				break;
			case 2:
				if (current.employeeTotal == 0) {
					System.out.println("1 " + current.findEmployeeTotalUpperBound());
				}
				else {
					System.out.println(current.employeeTotal + " " + current.employeeTotal);
				}
				break;
		}
	}

}

class Division {
	String extending, name;
	ArrayList<Division> children;
	int employees;
	int employeeTotal;
	int employeesUsed;
	
	public Division(String name, String parent, int numEmployees, int numEmployeesTotal) {
//		System.out.println(name + ": Division");
		children = new ArrayList<Division>();
		
		this.name = name;
		setParent(parent);

		setEmployeeTotal(numEmployeesTotal);
		setEmployees(numEmployees);
		if (employeeTotal == 0)
			findEmployeeTotalUpperBound();
	}
	
	
	void p(String s) {
//		if (name.equals("finance"))
			System.out.println(name + "> " + s);
	}
	public int findEmployeeUpperBound() {
//		System.out.println(name + ": findEmployeeUpperBound");
		if (employees == 0) {
			int unassigned = unassignedEmployeeCount();
			p("unassigned > " + unassigned);
			int childrenEmployees = 0;
			p("childrenEmployees > " + childrenEmployees);
			for (Division child: children) {
				childrenEmployees += child.getEmployeeTotal();
				p("+" + child.getEmployeeTotal() + " | " + childrenEmployees);
			}
			p("diff > " + (unassigned - childrenEmployees));
			setEmployees(unassigned - childrenEmployees);
		}
		return employees;
	}
	
	public int findEmployeeTotalUpperBound() {
//		System.out.println(name + ": findEmployeeTotalUpperBound");
		if (employeeTotal == 0) {
			Division parent = getParent();
			if (parent != null) {
				int parentUnassigned = parent.unassignedEmployeeCount();
				if (parentUnassigned == parent.employeeTotal)
					parentUnassigned--;
				
				setEmployeeTotal(parentUnassigned);
			}
		}
		return employeeTotal;
	}
	

	private void setEmployees(int amount) {
//		System.out.println(name + ": setEmployees");
		employees = amount;
		takeEmployees(amount);
	}
	private int getEmployees() {
//		System.out.println(name + ": getEmployees");
		if (employees == 0)
			findEmployeeUpperBound();
		
		return employees;
	}
	
	private void setEmployeeTotal(int amount) {
//		System.out.println(name + ": setEmployeeTotal");
		employeeTotal = amount;
		
		Division parent = getParent();
		if (parent != null) {
			parent.takeEmployees(employeeTotal);
			parent.children.add(this);
		}
	}
	private int getEmployeeTotal() {
//		System.out.println(name + ": getEmployeeTotal " + employeeTotal);
		if (employeeTotal == 0)
			findEmployeeTotalUpperBound();
			
		return employeeTotal;
	}
	
	
	private Division getParent() {
//		System.out.println(name + ": getParent");
		return Main.divs.get(extending);
	}
	private void setParent(String name) {
//		System.out.println(name + ": setParent");
		if (!name.equals("NONE"))
			extending = name;
	}
	
	
	public int unassignedEmployeeCount() {
//		System.out.println(name + ": unassignedEmployeeCount");
		return getEmployeeTotal() - employeesUsed;
	}
	public void takeEmployees(int amount) {
//		System.out.println(name + ": takeEmployees");
		if (name.equals("finance"))
			System.out.println(name + " taking " + amount + " from finance");
		employeesUsed += amount;
	}
}