
import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;
/**
 * Discounts(ResultSet rs) throws SQLException
 * ResultSet selectAll(Connection conn) throws SQL exception
 * ArrayList<Discounts> parseResultSet(ResultSet rs) throws SQLException
 * void outputList(ArrayList<Discounts>)
 */
public class Discounts {
	private String discnt_category, discnt_rate;
	
	
	public Discounts(){
		super();
	}
	/*
	*	Take in a result set and initialize.
	*/
	public Discounts(ResultSet rs) throws SQLException{
		this.discnt_category = rs.getString(1);
		this.discnt_rate = rs.getString(2);
	}
	
	/*
	*	Call the getDiscountss function
	*	Return the result set obtained
	*/
	public ResultSet selectAll(Connection conn) throws SQLException, Exception{
		CallableStatement cs = conn.prepareCall("begin ? := Project2_PACKAGE.show_discounts(); end;");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		return (ResultSet)cs.getObject(1);
	}
	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<Discounts> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new Discounts
		//object and pushing it onto the arrays
		ArrayList<Discounts> elements = new ArrayList<Discounts>();
		while(rs.next()){
			elements.add(new Discounts(rs));
		}
		return elements;
	}
	/*
	*	Output a list of Discounts objects to std out
	*/
	public void outputList(ArrayList<Discounts> pl){									
		System.out.println("DISCNT_CATEGORY \t DISCNT_RATE");
		for(Discounts p: pl){														
			System.out.println(p.getdiscnt_category() + "\t\t\t" + p.getdiscnt_rate());
		}
	}
	
	public String getOutputList(ArrayList<Discounts> pl){
		String out = "DISCNT_CATEGORY \t DISCNT_RATE\n";
		for(Discounts p: pl){
			out += p.getdiscnt_category() + "\t\t" + p.getdiscnt_rate() + "\n";
		}
		return out;
	}
	
	//accessors
	public String getdiscnt_category(){
		return this.discnt_category;
	}
	public String getdiscnt_rate(){
		return this.discnt_rate;
	}	
}
