import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;
/*
*	Purchase(ResultSet rs) throws SQLException
*	ResultSet selectAll(Connection conn) throws SQLException
*	ArrayList<Purchase> parseResultSet(ResultSet rs) throws SQLException
*	void outputList(ArrayList<Purchase>)
*/

public class Purchase {

	private String purID;
	private String eid;
	private String pid;
	private String cid;
	private int qty;
	private String ptime;
	private float total_price;


	public Purchase(){
		super();
	}
	/*
	*	Take in a result set and initialize.
	*/
	public Purchase(ResultSet rs) throws SQLException{
		this.purID = rs.getString(1);
		this.eid = rs.getString(2);
		this.pid = rs.getString(3);
		this.cid = rs.getString(4);
		this.qty = rs.getInt(5);
		//this.date = rs.getString(6);
		this.ptime = rs.getString(6);
		this.total_price = rs.getFloat(7);
	}

	/*
	*	Call the getPurchases function
	*	Return the result set obtained
	*/
	public ResultSet selectAll(Connection conn) throws SQLException, Exception{

		CallableStatement cs = conn.prepareCall("begin ? := Project2_PACKAGE.show_purchases(); end;");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		return (ResultSet)cs.getObject(1);
	}

	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<Purchase> parseResultSet(ResultSet rs) throws SQLException{

		//Loop through the result set creating a new Purchase
		//object and pushing it onto the arrays
		ArrayList<Purchase> elements = new ArrayList<Purchase>();
		while(rs.next()){

			elements.add(new Purchase(rs));

		}

		return elements;

	}

	/*
	*	Output a list of Purchase objects to std out
	*/
	public void outputList(ArrayList<Purchase> pl){

											
		System.out.println("PUR#\tEID\tPID\tCID\tQTY\tDATE\tPTIME\t\tTOTAL_PRICE");
		for(Purchase p: pl){														
		System.out.println(p.getPurID() + "\t" + p.getEid() + "\t" + p.getPid() + "\t" + p.getCid() + "\t" + p.getQty() +  "\t" + p.getpTime()+ "\t" + p.getTotalPrice());
		}
	}
	
	public String getOutputList(ArrayList<Purchase> pl){
		String out = "PUR#\tEID\tPID\tCID\tQTY\tDATE\tPTIME\tTOTAL_PRICE\n";
		for(Purchase p: pl){
			out += p.getPurID() + "\t" + p.getEid() + "\t" + p.getPid() + "\t" + p.getCid() + "\t" + p.getQty() +  "\t" + p.getpTime()+ "\t" + p.getTotalPrice() + "\n";
		}
		return out;
	}
	
	public String getPurID(){
		return this.purID;
	}

	public String getEid(){
		return this.eid;
	}
	public String getPid(){
		return this.pid;
	}
	public String getCid(){
		return this.cid;
	}
	public int getQty(){
		return this.qty;
	}
	public String getpTime(){
		return this.ptime;
	}
	public float getTotalPrice(){
		return this.total_price;
	}



}
