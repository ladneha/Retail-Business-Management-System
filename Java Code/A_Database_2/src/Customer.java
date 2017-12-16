import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;
/**
 * Customer(ResultSet rs) throws SQLException
 * ResultSet selectAll(Connection conn) throws SQL exception
 * ArrayList<Customer> parseResultSet(ResultSet rs) throws SQLException
 * void outputList(ArrayList<Customer>)
 */
public class Customer {
	private String cid, cname, telephoneNum,last_visit_date;
	private int visits_made;
	
	public Customer(){
		super();
	}
	/*
	*	Take in a result set and initialize.
	*/
	public Customer(ResultSet rs) throws SQLException{
		this.cid = rs.getString(1);
		this.cname = rs.getString(2);
		this.telephoneNum = rs.getString(3);
		this.visits_made = rs.getInt(4);
		this.last_visit_date = rs.getString(5);
		
		if(this.cid.equals(""))
		{
			throw new SQLException("Customer id can't be empty");
		}
		
	}
	
	/*
	*	Call the getCustomer function
	*	Return the result set obtained
	*/
	public ResultSet selectAll(Connection conn) throws SQLException, Exception{
		CallableStatement cs = conn.prepareCall("begin ? := Project2_PACKAGE.show_customers(); end;");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		return (ResultSet)cs.getObject(1);
	}
	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<Customer> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new Customer
		//object and pushing it onto the arrays
		ArrayList<Customer> elements = new ArrayList<Customer>();
		while(rs.next()){
			elements.add(new Customer(rs));
		}
		return elements;
	}
	/*
	*	Output a list of Customer objects to std out
	*/
	public void outputList(ArrayList<Customer> pl){									
		System.out.println("CID\tNAME\tTELEPHONE#\tVISITS_MADE\tLAST_VISIT_DATE");
		for(Customer p: pl){														
			System.out.println(p.getCid() + "\t" + p.getCname() + "\t" + p.getTelephoneNum() + "\t" + p.getVisitsMade() + "\t" + p.getLastVisitDate());
		}
	}
	
	public String getOutputList(ArrayList<Customer> pl){
		String out = "CID\tNAME\tTELEPHONE#\tVISITS_MADE\tLAST_VISIT_DATE\n";
		for(Customer p: pl){
			out += p.getCid() + "\t" + p.getCname() + "\t" + p.getTelephoneNum() + "\t" + p.getVisitsMade() + "\t" + p.getLastVisitDate() + "\n";
		}
		return out;
	}
	//accessors
	public String getCid(){
		return this.cid;
	}
	public String getCname(){
		return this.cname;
	}
	public String getTelephoneNum(){
		return this.telephoneNum;
	}
	public int getVisitsMade(){
		return this.visits_made;
	}
	public String getLastVisitDate(){
		return this.last_visit_date.substring(0,10);
	}
}
