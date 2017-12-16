import java.lang.*;
import java.io.*;
import java.sql.*;

import oracle.jdbc.*;

import java.util.ArrayList;
/**
 * Product(ResultSet rs) throws SQLException
 * ResultSet selectAll(Connection conn) throws SQL exception
 * ArrayList<Product> parseResultSet(ResultSet rs) throws SQLException
 * void outputList(ArrayList<Product>)
 */
public class Product {
	private String pid, pname;
	private int qoh, qoh_threshold, discnt_category;
	private float original_price;

	public Product(){
		super();
	}

	public Product(String pid_in, Connection con) throws SQLException{
		//Check for null or empty string
		if(pid_in == "" || pid_in == null){
			throw new SQLException("PID passed is null or empty.");
		}

		Statement stmt = null;
		//System.out.println("PID passed to product contstructor is: " + pid_in);
		//Create the query
		StringBuilder sql = new StringBuilder();
		sql.append("select * ");
		sql.append("from products p ");
		sql.append("where p.pid = '" + pid_in + "'");

		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			if(rs.next())
					{
					this.pid = rs.getString(1);
					this.pname = rs.getString(2);
					this.qoh = rs.getInt(3);
					this.qoh_threshold = rs.getInt(4);
					this.original_price = rs.getFloat(5);
					this.discnt_category = rs.getInt(6);
					//System.out.println("PID of product obtained: " + this.pid);
					}//Ensure the product selected was correct
					if(this.pid == null || !this.pid.equalsIgnoreCase(pid_in))
					{
						throw new SQLException("Unable to find product with pid: " + pid_in);
					}
			}
			catch(SQLException e)
			{
				System.out.println ("\n*** SQLException caught when attempting to call "
				+ "product constructor with string pid ***\n");
				System.out.println(e.getMessage());
				throw e;
			}
			finally{
				if(stmt != null){
				try{ 	
					stmt.close();
					}catch(SQLException e){
						System.out.println ("\n*** SQLException caught when attempting to call product constructor with string pid ***\n");
						System.out.println(e.getMessage());
						throw e;
					}
				}
			}
	}

	/*
	 *	Take in a result set and initialize.
	 */
	public Product(ResultSet rs) throws SQLException{
		this.pid = rs.getString(1);
		this.pname = rs.getString(2);
		this.qoh = rs.getInt(3);
		this.qoh_threshold = rs.getInt(4);
		this.original_price = rs.getFloat(5);
		this.discnt_category = rs.getInt(6);
	}

	/*
	 *	Call the getProduct function
	 *	Return the result set obtained
	 */
	public ResultSet selectAll(Connection conn) throws SQLException, Exception{
		CallableStatement cs = conn.prepareCall("begin ? := Project2_PACKAGE.show_products(); end;");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		return (ResultSet)cs.getObject(1);
	}
	/*
	 *	Parse a result set into an array list of objects
	 */
	public ArrayList<Product> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new Product
		//object and pushing it onto the arrays
		ArrayList<Product> elements = new ArrayList<Product>();
		while(rs.next()){
			elements.add(new Product(rs));
		}
		return elements;
	}
	/*
	 *	Output a list of Product objects to std out
	 */
	public void outputList(ArrayList<Product> pl){									
		System.out.println("PID\tPNAME\t\tQOH\tQOH_THRESHOLD\tORGINIAL_PRICE\tDISCNT_CATEGORY");
		for(Product p: pl){														
			System.out.println(p.getPid() + "\t" + p.getPname() + "\t\t" + p.getQoh() + "\t" + p.getQohThreshold() + "\t\t" + p.getOriginalPrice() + "\t\t" + p.getDiscntCategory());
		}
	}
	
	public String getOutputList(ArrayList<Product> pl){
		String out = "PID\tPNAME\t\tQOH\tQOH_THRESHOLD\tORGINIAL_PRICE\tDISCNT_CATEGORY\n";
		for(Product p: pl){
			out += p.getPid() + "\t" + p.getPname() + "\t\t" + p.getQoh() + "\t" + p.getQohThreshold() + "\t\t" + p.getOriginalPrice() + "\t\t" + p.getDiscntCategory() + "\n";
		}
		return out;
	}
	
	//accessors
	public String getPid(){
		return this.pid;
	}
	public String getPname(){
		return this.pname;
	}
	public int getQoh(){
		return this.qoh;
	}
	public int getQohThreshold(){
		return this.qoh_threshold;
	}
	public float getOriginalPrice(){
		return this.original_price;
	}
	public float getDiscntCategory(){
		return this.discnt_category;
	}



}
