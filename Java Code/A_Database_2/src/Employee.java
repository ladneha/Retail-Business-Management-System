import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;
/**
 * Employee(ResultSet rs) throws SQLException
 * ResultSet selectAll(Connection conn) throws SQL exception
 * ArrayList<Employee> parseResultSet(ResultSet rs) throws SQLException
 * void outputList(ArrayList<Employee>)
 */
public class Employee {
	private String eid, ename, telephoneNum, email;
	
	
	public Employee(){
		super();
	}
	/*
	*	Take in a result set and initialize.
	*/
	public Employee(ResultSet rs) throws SQLException{
		this.eid = rs.getString(1);
		this.ename = rs.getString(2);
		this.telephoneNum = rs.getString(3);
		this.email = rs.getString(4);
	}
	
	/*
	*	Call the getEmployees function
	*	Return the result set obtained
	*/
	public ResultSet selectAll(Connection conn) throws SQLException, Exception{
		CallableStatement cs = conn.prepareCall("begin ? := Project2_PACKAGE.show_employees(); end;");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		return (ResultSet)cs.getObject(1);
	}
	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<Employee> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new Employee
		//object and pushing it onto the arrays
		ArrayList<Employee> elements = new ArrayList<Employee>();
		while(rs.next()){
			elements.add(new Employee(rs));
		}
		return elements;
	}
	/*
	*	Output a list of Employee objects to std out
	*/
	public void outputList(ArrayList<Employee> pl){									
		System.out.println("EID\tENAME\tTELEPHONE#\tEMAIL");
		for(Employee p: pl){														
			System.out.println(p.getEid() + "\t" + p.getEname() + "\t" + p.getTelephoneNum() + "\t" + p.getEmail());
		}
	}
	
	public String getOutputList(ArrayList<Employee> pl){
		String out = "EID\tENAME\tTELEPHONE#\t\tEMAIL\n";
		for(Employee p: pl){
			out += p.getEid() + "\t" + p.getEname() + "\t" + p.getTelephoneNum() + "\t\t" + p.getEmail() + "\n";
		}
		return out;
	}
	//accessors
	public String getEid(){
		return this.eid;
	}
	public String getEname(){
		return this.ename;
	}
	public String getTelephoneNum(){
		return this.telephoneNum;
	}
	public String getEmail(){
		return this.email;
	}
	
}
