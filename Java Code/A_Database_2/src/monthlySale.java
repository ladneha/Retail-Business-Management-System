import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;
/**
 * monthlySale(ResultSet rs) throws SQLException
 * ResultSet selectAll(Connection conn) throws SQL exception
 * ArrayList<monthlySale> parseResultSet(ResultSet rs) throws SQLException
 * void outputList(ArrayList<monthlySale>)
 */
public class monthlySale {
	private String eid,ename, month, year;
	private int qty_sold, total_times_sale;
	private float total_dollar_amount;
	
	public monthlySale(){
		super();
	}
	/*
	*	Take in a result set and initialize.
	*/
	public monthlySale(ResultSet rs) throws SQLException{
		//System.out.println("Here: "+rs.getFetchSize());
		this.eid = rs.getString(1);
	}
	
	/*
	*	Call the getmonthlySale function
	*	Return the result set obtained
	*/
	public void selectAll(String eid, Connection conn) throws SQLException, Exception{
		//System.out.println("inside selectALL");
		CallableStatement cs = conn.prepareCall("{call Project2_PACKAGE.monthly_sale_activities(?,?)}");		
		//cs.setString(3, eid);
		cs.setString(1, eid);
		cs.registerOutParameter(2, OracleTypes.CURSOR);
		cs.execute();
		ResultSet rs = ((OracleCallableStatement)cs).getCursor(2);
		//System.out.println("After execute");
		System.out.println("EID\tENAME\tMONTH\tYEAR\tQty_Sold\tTOTAL_SALE\tTOTAL_SPENT");
		while (rs.next()) {
	        System.out.println(rs.getString("eid")+"\t"+rs.getString("name")+"\t"+rs.getString("Month") + "\t" + rs.getString("Year") +"\t"+rs.getString("QTY_SOLD")+"\t"+rs.getString("TOTAL_SALE")+"\t"+rs.getString("TOTAL_SPENT"));
	      }
		//System.out.println((ResultSet)cs.getResultSet());
		//return (ResultSet)cs.getObject(1);
	}
	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<monthlySale> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new monthlySale
		//object and pushing it onto the arrays
		ArrayList<monthlySale> elements = new ArrayList<monthlySale>();
		while(rs.next()){
			elements.add(new monthlySale(rs));
		}
		return elements;
	}
	/*
	*	Output a list of monthlySale objects to std out
	*/
	public void outputList(ArrayList<monthlySale> pl){									
		System.out.println("EID\tENAME\tMONTH\tYEAR\tQty_Sold\tTOTAL_SALE\tTOTAL_SPENT");
		for(monthlySale p: pl){														
			System.out.println(p.geteid()+"\t"+p.getEname() + "\t" + p.getMonth() + "\t" + p.getYear()+ "\t" + p.getQtySold() +"\t\t" + p.getTotalsale()+ "\t\t" + p.getTotalDollar());
		}
	}
	//accessors
	public String geteid(){
		return this.eid;
	}
	public String getEname(){
		return this.ename;
	}
	public String getMonth(){
		return this.month;
	}
	public String getYear(){
		return this.year;
	}
	public int getQtySold(){
		return this.qty_sold;
	}
	public float getTotalsale(){
		return this.total_times_sale;
	}
	public float getTotalDollar(){
		return this.total_dollar_amount;
	}

	
}
