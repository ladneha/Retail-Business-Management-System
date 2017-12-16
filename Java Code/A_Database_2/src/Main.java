import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import oracle.jdbc.*;
import java.math.*;
import java.io.*;
import java.awt.*;
import oracle.jdbc.pool.OracleDataSource;

public class Main {

    public static int i = 0;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		try{

		      //Connection to Oracle server
		      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		      //System.out.println("1st Phase");
		      Connection conn = ds.getConnection("rmistry2", "mi663277");
		      //Class.forName("oracle.jdbc.driver.OracleDriver");
		      //Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","123");  
		      System.out.println("Connection Established");
		      
		      //GUI
		      User_Interface gui = new User_Interface(conn);
		      
		      Scanner s = new Scanner(System.in);
		      int choice = 0;
		      
		      //code of User Interface
		      while(i!=1){
					choice = displayMenu();
					switch(choice){
						case 1://View records from a table
							choice = displayTableList();
							if(choice != 8){
								executeShowTableData(choice, conn);
							}
							break;
						case 2://Insert records into a table
							choice = displayInsertRecordsTableList();
							if(choice != 3){
								executeInsertRecordTable(choice, conn);
							}
							break;
						case 3://Total Saving of any purchase
						  	ComputePurchaseSaving(conn);
							break;
						case 4://Monthly Sale Activity of an Employee
							executeMonthlySaleActivity(conn);
							break;
						case 5://Delete from Purchase Table
							deletePurchase(conn);
							break;
						case 6://Termination Condition
							i = 1;
							break;
					}
				}
				//Clean up 
				conn.close();
		}
		catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n"+ex.getMessage());}
	}
	
	public static int displayTableList(){

		//Output menu
		System.out.println("Select Table: ");
		System.out.println("\t1. Employees");
		System.out.println("\t2. Customers");
		System.out.println("\t3. Products");
		System.out.println("\t4. Suppliers");
		System.out.println("\t5. Supplies");
		System.out.println("\t6. Purchases");
		System.out.println("\t7. Discounts");
		System.out.println("\t8. Logs");
		System.out.println("\t9. Return to main menu");

		//Get the users input
		int retVal = TableNumber();

		//Check for correct user input
		if(retVal > 0 && retVal < 9){
			return retVal;
		}else{
			System.out.println("Unrecognized option");
		}
		return retVal;
	}
	
	public static int displayMenu(){

		//Display the menu
		System.out.println("\n\nPlease select a choice below. (1 - 6)");
		System.out.println("\t1. View records from a table");
		System.out.println("\t2. Insert records into a table");
		System.out.println("\t3. Calculate the saving on purchase");
		System.out.println("\t4. Monthly sale activity of an employee");
		System.out.println("\t5. Delete record from the Purchase table");
		System.out.println("\t6. Exit");

		int retVal = TableNumber();

		//Check for correct input
		if(retVal > 0 && retVal < 7){
			return retVal;
		}
		else{
			System.out.println("Unrecognized option");
		}
		return retVal;
	}
	
	//function to get the table number from user
	public static int TableNumber(){

		//Get user input
		Scanner s = new Scanner(System.in);
		String choice = s.nextLine();
		int retVal = -1;
		try{
			if(choice != null && choice != ""){
				retVal = Integer.parseInt(choice);
			}
			else{
				retVal = -1;
			}
		}catch(NumberFormatException nfe){
			//Attempted to parse a string which is not an Integer
			retVal = -1;
		}catch(Exception e){
			retVal = -1;
		}
		return retVal;

	}
	
	//Function to show the table records on stdout
	public static void executeShowTableData(int choice, Connection conn) throws SQLException, Exception{

		switch(choice){
			case 1:{
				       //procedure = "getEmployees";
				       Employee e = new Employee();
				       ResultSet rs = e.selectAll(conn);
				       ArrayList<Employee> employees = e.parseResultSet(rs);
				       e.outputList(employees);
				       return;
			       }
			case 2:{
				       //procedure = "getCustomers";
				       Customer c = new Customer();
				       ResultSet rs = c.selectAll(conn);
				       ArrayList<Customer> customers = c.parseResultSet(rs);
				       c.outputList(customers);
				       return;
			       }
			case 3:{
				       //procedure = "getProducts";
				       Product p = new Product();
				       ResultSet rs = p.selectAll(conn);
				       ArrayList<Product> products = p.parseResultSet(rs);
				       p.outputList(products);
				       return;
			       }

			case 4:{
				       //procedure = "getSuppliers";
				       Supplier s = new Supplier();
				       ResultSet rs = s.selectAll(conn);
				       ArrayList<Supplier> suppliers = s.parseResultSet(rs);
				       s.outputList(suppliers);
				       return;
			       }
			case 5: {
					//procedure = "getSupply";
					Supplies s = new Supplies();
					ResultSet rs = s.selectAll(conn);
					ArrayList<Supplies> supplies = s.parseResultSet(rs);
					s.outputList(supplies);
					return;
				}
			case 6:{
				       //procedure = "getPurchases";
				       Purchase p = new Purchase();
				       ResultSet rs = p.selectAll(conn);
				       ArrayList<Purchase> purchases = p.parseResultSet(rs);
				       p.outputList(purchases);
				       return;
				    
			       }
			case 7:{
			       //procedure = "getDiscounts";
			       Discounts d = new Discounts();
			       ResultSet rs = d.selectAll(conn);
			       ArrayList<Discounts> discounts = d.parseResultSet(rs);
			       d.outputList(discounts);
			       return;
					}
			case 8:{
				       //procedure = "getLogs";
				       Log l = new Log();
				       ResultSet rs = l.selectAll(conn);
				       ArrayList<Log> logs = l.parseResultSet(rs);
				       l.outputList(logs);
				       return;
			       }
			default:
			       System.out.println("Unrecognized option: " + choice);
			       return;
		}
	}
	
	
	//Function to show the list of tables to insert data 
	public static int displayInsertRecordsTableList(){

		//Output menu
		System.out.println("\n\nPlease select a choice below. (1 - 3)");
		System.out.println("\t1. Add Customer Details");
		System.out.println("\t2. Add Purchase Details");
		System.out.println("\t3. Return to main menu");

		int retVal = TableNumber();
		if(retVal > 0 && retVal < 4)
		{
			return retVal;
		}else{
			System.out.println("Unrecognized option");
		}
		return retVal;

	}
	
	//Function to asks user to input the details of customer or purchase
	public static void executeInsertRecordTable(int choice, Connection conn){
		Scanner s = new Scanner(System.in);
		String in = "", exec;
		CallableStatement cs = null;
		int val = 0;

		try{

			System.out.println("\nPlease provide the following information: \n");
			if(choice == 1){
				String cid, cname, ctel;

				//cid
				System.out.println("\nEnter new customer id: ");
				cid = s.nextLine();
				//cname
				System.out.println("\nEnter customer name: ");
				cname= s.nextLine();

				//ctel
				System.out.println("\nEnter customer's Telephone number: ");
				ctel = s.nextLine();
				//Create and prepare the call
				cs = conn.prepareCall("begin addCustomer.add_Customer(?, ?, ?); end;");
				cs.setString(1, cid);
				cs.setString(2, cname);
				cs.setString(3, ctel);
				cs.execute();
				System.out.println("\nInsert executed successfully!\n");

			}else if (choice == 2){
				/*Adding a purchase here, need to do some additional checking of the QOH
				 * to see whether or not a supply was automatically ordered. In that situation
				 * we need to regenerate the messages to the user.
				 */ 
				String eid, pid, cid;
				int qty;			

				//eid
				System.out.println("Enter Employee ID: ");
				eid = s.nextLine();

				//pid
				System.out.println("\nEnter Product ID: ");
				pid = s.nextLine();

				//cid
				System.out.println("\nEnter Customer ID: ");
				cid = s.nextLine();

				//qty
				System.out.println("\nEnter Quantity: ");
				try{
					qty = Integer.parseInt(s.nextLine());
          if(qty < 1)
            throw new NumberFormatException("Unable to purchase less than 1 of any product at the moment.");
				}catch(NumberFormatException nfe){
					System.out.println("\n*** Invalid number given for quantity value when attempting to -Insert Purchase Record-. Please try again with an 'integer' value.");
					return;			
				}

				Product oldProd = new Product(pid, conn);
				//System.out.println("Products QOH before purchase: " + oldProd.getQoh());

				// check for Qoh and inserted quantity
				if(qty<oldProd.getQoh())
				{
					
					//Create and prepare the call
					cs = conn.prepareCall("begin addPurchases.add_Purchases(?,?,?,?,?); end;");
					cs.setString(1, eid);
					cs.setString(2, pid);
					cs.setString(3, cid);
					cs.setInt(4, qty);
					cs.registerOutParameter(5, Types.VARCHAR);
				
					cs.execute();
					
					Product newProd = new Product(pid, conn);
			
					//System.out.println("Products QOH after purchase: " + newProd.getQoh());

					//Check newQoh is oldQoh - qty, otherwise a supply was ordered so
					//Output to user about it
					if(newProd.getQoh() != (oldProd.getQoh() - qty))
					{
						int supply = newProd.getQoh() - (oldProd.getQoh() - qty);
						System.out.println("The supply for this product ran below the threshold. A new supply in the quantity of " + supply + " was ordered.");
					}
					System.out.println("\nInsert executed successfully!\n");
				}
				else
				{
					System.out.println("Order quantity must be smaller than Quantity on Hand");
				}
			}


		}catch(SQLException se){
			System.out.println ("\n*** SQLException caught when attempting to insert a record ***\n");
			System.out.println(se.getMessage());
			return;
		}catch(NullPointerException npe){
			System.out.println("\n*** Null Pointer Exception caught when attempting to insert a record ***\n");
			return;
		}catch(Exception e){
			System.out.println ("\n*** Exception caught when attempting to insert a record ***\n");
			System.out.println(e.getMessage());
			return;
		}
		finally{
			try{
				if(cs != null){
					cs.close();
				}
			}catch(SQLException se){
				System.out.println("Error closing the callable statement in insert record");
			}catch(NullPointerException npe){
				System.out.println("\n*** Null Pointer Exception caught when attempting to insert a record ***\n");
				System.out.println("Error closing the callable statement in insert record");
				return;
			}
		}

		

		return;
	}
	
	//Function to compute the purchase saving
	public static void ComputePurchaseSaving(Connection conn)
	{
		Scanner s = new Scanner(System.in);
		CallableStatement cs = null;
		
		//Asking user to enter purchase id
		try{
			System.out.println("\n\nEnter Purchase Id:\n");
			String pur_id;
			pur_id = s.nextLine();

			PurchaseSaving ms = new PurchaseSaving();
			ms.selectAll(pur_id, conn);
			return;
		}
		catch(SQLException se){
			System.out.println ("\n*** SQLException caught when attempting to report purchase saving ***\n");
			System.out.println(se.getMessage());
			return;
		}catch(NullPointerException npe){
			System.out.println("\n*** Null Pointer Exception caught when attempting to report purchase saving ***\n");
			return;
		}catch(Exception e){
			System.out.println ("\n*** Exception caught when attempting to report purchase saving ***\n");
			System.out.println(e.getMessage());
			return;
		}
		finally{
			try{
				if(cs != null){
					cs.close();
				}
			}catch(SQLException se){
				System.out.println("Error closing the callable statement in purchase saving");
			}catch(NullPointerException npe){
				System.out.println("\n*** Null Pointer Exception caught when attempting to compute purchase saving ***\n");
				System.out.println("Error closing the callable statement in compute purchase saving");
				return;
			}
		}

	}
	
	//Function to get the Monthly Sale Activity of an Employee
	public static void executeMonthlySaleActivity(Connection conn){
		Scanner s = new Scanner(System.in);
		CallableStatement cs = null;
		//asking user to enter eid
		try{
			System.out.println("\n\nPlease provide a Employee ID:\n");
			String eid;
			eid = s.nextLine();

			monthlySale ms = new monthlySale();
			ms.selectAll(eid, conn);
			//ResultSet rs = ms.selectAll(eid, conn);
			//System.out.println("***"+rs.getFetchSize());
			//ArrayList<monthlySale> monthlySales = ms.parseResultSet(rs);
			//ms.outputList(monthlySales);
			return;
		}
		catch(SQLException se){
			System.out.println ("\n*** SQLException caught when attempting to report monthly sale ***\n");
			System.out.println(se.getMessage());
			return;
		}catch(NullPointerException npe){
			System.out.println("\n*** Null Pointer Exception caught when attempting to report monthly sale ***\n");
			return;
		}catch(Exception e){
			System.out.println ("\n*** Exception caught when attempting to report monthly sale ***\n");
			System.out.println(e.getMessage());
			return;
		}
		finally{
			try{
				if(cs != null){
					cs.close();
				}
			}catch(SQLException se){
				System.out.println("Error closing the callable statement in monthly sale report");
			}catch(NullPointerException npe){
				System.out.println("\n*** Null Pointer Exception caught when attempting to report monthly sale ***\n");
				System.out.println("Error closing the callable statement in report monthly sale");
				return;
			}
		}
	}
	
	//Function to delete the data from Purchase table
	public static void deletePurchase(Connection conn)
	{
		Scanner s = new Scanner(System.in);
		CallableStatement cs = null;
		
		//enter a purchase id to delete from purchase table
		try{
			System.out.println("Enter a Purchase id:\n");
			String pur_id;
			pur_id = s.nextLine();
			cs = conn.prepareCall("begin delete_Purchases(?); end;");
			cs.setString(1, pur_id);
			cs.execute();
			System.out.println("Record Delete Successfully");
		} catch(SQLException se){
			System.out.println ("\n*** SQLException caught when attempting to delete a record ***\n");
			System.out.println(se.getMessage());
			return;
		}catch(NullPointerException npe){
			System.out.println("\n*** Null Pointer Exception caught when attempting to delete a record ***\n");
			return;
		}catch(Exception e){
			System.out.println ("\n*** Exception caught when attempting to delete a record ***\n");
			System.out.println(e.getMessage());
			return;
		}
		finally{
			try{
				if(cs != null){
					cs.close();
				}
			}catch(SQLException se){
				System.out.println("Error closing the callable statement");
			}catch(NullPointerException npe){
				System.out.println("\n*** Null Pointer Exception caught when attempting to delete a record in purchase ***\n");
				System.out.println("Error closing the callable statement while deleting record in purchase");
				return;
			}
		}
	}

	//Function to display table data to GUI
	public static String executeShowTableData1(int choice, Connection conn) throws SQLException, Exception{

		//	String procedure = "";

		switch(choice){
			case 1:{
				       //old
				       //procedure = "getEmployees";
				       Employee e = new Employee();
				       ResultSet rs = e.selectAll(conn);
				       ArrayList<Employee> employees = e.parseResultSet(rs);
				       
				       return e.getOutputList(employees);
				       //break;
			       }
			case 2:{
				       //old line
				       //procedure = "getCustomers";
				       //new
				       Customer c = new Customer();
				       ResultSet rs = c.selectAll(conn);
				       ArrayList<Customer> customers = c.parseResultSet(rs);
				       //c.outputList(customers);
				       return c.getOutputList(customers);
				       //break;
			       }
			case 3:{
				       //OLD
				       //procedure = "getProducts";
				       Product p = new Product();
				       ResultSet rs = p.selectAll(conn);
				       ArrayList<Product> products = p.parseResultSet(rs);
				       //p.outputList(products);
				       return p.getOutputList(products);
				       //break;
			       }

			case 4:{
				       //out with the old
				       //procedure = "getSuppliers";
				       Supplier s = new Supplier();
				       ResultSet rs = s.selectAll(conn);
				       ArrayList<Supplier> suppliers = s.parseResultSet(rs);
				       //s.outputList(suppliers);
				       return s.getOutputList(suppliers);
				       //break;
			       }
			case 5: {
					//old
					//procedure = "getSupply";
					Supplies s = new Supplies();
					ResultSet rs = s.selectAll(conn);
					ArrayList<Supplies> supplies = s.parseResultSet(rs);
					//s.outputList(supplies);
					return s.getOutputList(supplies);
					//break;
				}
			case 6:{
				       //old
				       //procedure = "getPurchases";
				       Purchase p = new Purchase();
				       ResultSet rs = p.selectAll(conn);
				       ArrayList<Purchase> purchases = p.parseResultSet(rs);
				      // p.outputList(purchases);
				       return p.getOutputList(purchases);
				       //break;
			       }
			case 7:{
			       //procedure = "getDiscounts";
			       Discounts d = new Discounts();
			       ResultSet rs = d.selectAll(conn);
			       ArrayList<Discounts> discounts = d.parseResultSet(rs);
			       //d.outputList(discounts);
			       return d.getOutputList(discounts);
					}
			case 8:{
				       //procedure = "getLogs";
				       Log l = new Log();
				       ResultSet rs = l.selectAll(conn);
				       ArrayList<Log> logs = l.parseResultSet(rs);
				     //  l.outputList(logs);
				       return l.getOutputList(logs);
			       }
			default:
			       System.out.println("Unrecognized option: " + choice);
			       return null;
		}
	}
	
	//Function to exit from GUI
	public static void exit_function()
	{
		System.exit(0);
	}
}
