import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;

public class PurchaseSaving {
	public float totalSaving;
	
	public PurchaseSaving(){
		super();
	}
	
	public PurchaseSaving(ResultSet rs) throws SQLException{
		/*this.pname = rs.getString(1);
		this.month = rs.getString(2);
		this.year = rs.getString(3);
		this.qty_sold = rs.getInt(4);
		this.total_spent = rs.getFloat(5);
		this.average_price = rs.getFloat(6);*/
		this.totalSaving = rs.getFloat(1);
	}
	
	/*
	*	Call the getmonthlySale function
	*	Return the result set obtained
	*/
	public void selectAll(String pid, Connection conn) throws SQLException, Exception{
		CallableStatement cs = conn.prepareCall(" {? = call Project2_PACKAGE.purchase_saving(?)}");	
		cs.registerOutParameter(1, Types.FLOAT);
		cs.setInt(2, Integer.parseInt(pid));
		cs.execute();
		System.out.println(cs.getFloat(1));
		//return (ResultSet)cs.getObject(1);
	}
	
	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<PurchaseSaving> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new monthlySale
		//object and pushing it onto the arrays
		ArrayList<PurchaseSaving> elements = new ArrayList<PurchaseSaving>();
		while(rs.next()){
			elements.add(new PurchaseSaving(rs));
		}
		return elements;
	}
	/*
	*	Output a list of monthlySale objects to std out
	*/
	public void outputList(ArrayList<PurchaseSaving> pl){									
		System.out.println("Total Saving");
		for(PurchaseSaving p: pl){														
			System.out.println(p.getSaving());
		}
	}
	//accessors
	public float getSaving(){
		return totalSaving;
	}
}
