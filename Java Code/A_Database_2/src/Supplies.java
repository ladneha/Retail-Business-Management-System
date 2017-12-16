import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;
/**
 * Supplies(ResultSet rs) throws SQLException
 * ResultSet selectAll(Connection conn) throws SQL exception
 * ArrayList<Supplies> parseResultSet(ResultSet rs) throws SQLException
 * void outputList(ArrayList<Supplies>)
 */
public class Supplies {
	private String supID, pid, sid, sdate;
	private int quantity;
	
	public Supplies(){
		super();
	}
	/*
	*	Take in a result set and initialize.
	*/
	public Supplies(ResultSet rs) throws SQLException{
		this.supID = rs.getString(1);
		this.pid = rs.getString(2);
		this.sid = rs.getString(3);
		this.sdate = rs.getString(4);
		this.quantity = rs.getInt(5);
	}
	
	/*
	*	Call the getSupplies function
	*	Return the result set obtained
	*/
	public ResultSet selectAll(Connection conn) throws SQLException, Exception{
		CallableStatement cs = conn.prepareCall("begin ? := Project2_PACKAGE.show_supplies(); end;");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		return (ResultSet)cs.getObject(1);
	}
	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<Supplies> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new Supplies
		//object and pushing it onto the arrays
		ArrayList<Supplies> elements = new ArrayList<Supplies>();
		while(rs.next()){
			elements.add(new Supplies(rs));
		}
		return elements;
	}
	/*
	*	Output a list of Supplies objects to std out
	*/
	public void outputList(ArrayList<Supplies> pl){									
		System.out.println("SUP#\tPID\tSID\tSDATE\t\tQUANTITY");
		for(Supplies p: pl){														
			System.out.println(p.getSupID() + "\t" + p.getPid() + "\t" + p.getSid() + "\t" + p.getSDate() + "\t" + p.getQuantity());
		}
	}
	
	public String getOutputList(ArrayList<Supplies> pl){
		String out = "SUP#\tPID\tSID\tSDATE\tQUANTITY\n";
		for(Supplies p: pl){
			out += p.getSupID() + "\t" + p.getPid() + "\t" + p.getSid() + "\t" + p.getSDate() + "\t" + p.getQuantity() + "\n";
		}
		return out;
	}
	
	//accessors
	public String getSupID(){
		return this.supID;
	}
	public String getPid(){
		return this.pid;
	}
	public String getSid(){
		return this.sid;
	}
	public String getSDate(){
		return this.sdate.substring(0,10);
	}
	public int getQuantity(){
		return this.quantity;
	}
}
