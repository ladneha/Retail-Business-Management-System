import java.lang.*;
import java.io.*;
import java.sql.*;
import oracle.jdbc.*;
import java.util.ArrayList;
/**
 * Log(ResultSet rs) throws SQLException
 * ResultSet selectAll(Connection conn) throws SQL exception
 * ArrayList<Log> parseResultSet(ResultSet rs) throws SQLException
 * void outputList(ArrayList<Log>)
 */
public class Log {
	private String logID, username, optime, table_name, operation, tuple_pkey;
	
	public Log(){
		super();
	}
	/*
	*	Take in a result set and initialize.
	*/
	public Log(ResultSet rs) throws SQLException{
		this.logID = rs.getString(1);
		this.username = rs.getString(2);
		this.optime = rs.getString(3);
		this.table_name = rs.getString(4);
		this.operation = rs.getString(5);
		this.tuple_pkey = rs.getString(6);
	}
	
	/*
	*	Call the getLog function
	*	Return the result set obtained
	*/
	public ResultSet selectAll(Connection conn) throws SQLException, Exception{
		CallableStatement cs = conn.prepareCall("begin ? := Project2_PACKAGE.show_logs(); end;");
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.execute();
		return (ResultSet)cs.getObject(1);
	}
	/*
	*	Parse a result set into an array list of objects
	*/
	public ArrayList<Log> parseResultSet(ResultSet rs) throws SQLException{
		//Loop through the result set creating a new Log
		//object and pushing it onto the arrays
		ArrayList<Log> elements = new ArrayList<Log>();
		while(rs.next()){
			elements.add(new Log(rs));
		}
		return elements;
	}
	/*
	*	Output a list of Log objects to std out
	*/
	public void outputList(ArrayList<Log> pl){									
		System.out.println("LOG#\tUSERNAME\tOPTIME\tTABLE_NAME\tOPERATION\tTUPLE_PKEY");
		for(Log p: pl){														
			System.out.println(p.getLogID() + "\t" + p.getusername() + "\t" + p.getoptime() + "\t" + p.getTableName() + "\t" + p.getOperation() + "\t" + p.gettuple_pkey());
		}
	}
	
	public String getOutputList(ArrayList<Log> pl){
		String out = "LOG#\tUSERNAME\tOPERATION\tOPTIME\t\tTABLE_NAME\tTUPLE_PKEY\n";
		for(Log p: pl){
			out += p.getLogID() + "\t" + p.getusername() + "\t" + p.getoptime() + "\t" + p.getTableName() + "\t" + p.getOperation() + "\t" + p.gettuple_pkey() + "\n";
		}
		return out;
	}
	
	//accessors
	public String getLogID(){
		return this.logID;
	}
	public String getusername(){
		return this.username;
	}
	public String getoptime(){
		return this.optime;
	}
	public String getTableName(){
		return this.table_name;
	}
	public String getOperation(){
		return this.operation;
	}
	public String gettuple_pkey(){
		return this.tuple_pkey;
	}


	
}
