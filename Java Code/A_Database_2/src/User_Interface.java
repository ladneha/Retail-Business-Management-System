import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class User_Interface {

	CallableStatement cs = null;
	public User_Interface(Connection conn)
	{
		JFrame f=new JFrame("Retail Business Management System");  
	    JButton b1=new JButton("View Records from a Table"); 
	    JButton b2=new JButton("Insert records into a table"); 
	    JButton b3=new JButton("Calculate the saving on purchase"); 
	    JButton b4=new JButton("Monthly sale activity of an employee");
	    JButton b5=new JButton("Delete record from the Purchase"); 
	    JButton b6=new JButton("Exit"); 
	    JTextArea area=new JTextArea("");
	    JScrollPane scroll = new JScrollPane(area);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	      
	    area.setEditable(false);
        area.setBounds(400,100, 900,500);  
	    b1.setBounds(50,100,250,30);
	    b2.setBounds(50,180,250,30);
	    b3.setBounds(50,260,250,30);
	    b4.setBounds(50,340,250,30);
	    b5.setBounds(50,420,250,30);
	    b6.setBounds(50,500,250,30);
	    
	    //Show table data
	    b1.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		 final JPopupMenu popupmenu = new JPopupMenu("Tables");
	    		 JMenuItem emp = new JMenuItem("Employees");  
	             JMenuItem cust = new JMenuItem("Customers");  
	             JMenuItem pro = new JMenuItem("Products"); 
	             JMenuItem sup_r = new JMenuItem("Suppliers");  
	             JMenuItem sup = new JMenuItem("Supplies");  
	             JMenuItem pur = new JMenuItem("Purchases"); 
	             JMenuItem disc = new JMenuItem("Discounts");  
	             JMenuItem logs = new JMenuItem("Logs"); 
	             popupmenu.add(emp);
	             popupmenu.add(cust);
	             popupmenu.add(pro);
	             popupmenu.add(sup_r);
	             popupmenu.add(sup);
	             popupmenu.add(pur);
	             popupmenu.add(disc);
	             popupmenu.add(logs);
	             f.add(popupmenu);
	             popupmenu.show(f, 330 , 100);
	             emp.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(1,conn));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             cust.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(2,conn));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             pro.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(3,conn));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             sup_r.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(4,conn));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             sup.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(5,conn));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             pur.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(6,conn));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             disc.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(7,conn));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             logs.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							area.setText(Main.executeShowTableData1(8,conn));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	    	        }  
	    	    }); 
	    
	    // Insert Data
	    b2.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		 final JPopupMenu popupmenu = new JPopupMenu("Tables");
	    		 JMenuItem cust = new JMenuItem("Customer");  
	             JMenuItem pur = new JMenuItem("Purchases");
	             popupmenu.add(cust);
	             popupmenu.add(pur);
	             f.add(popupmenu);
	             popupmenu.show(f, 330 , 200);
	             cust.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
							//area.setText(Main.executeShowTableData1(2,conn));
	                    	 JFrame f_temp=new JFrame("Insert Details");
	                    	 
	                    	 JButton bTemp=new JButton("Insert");
	                    	 
	                    	 JTextArea cid=new JTextArea("");
	                    	 JLabel l1 = new JLabel("Customer id:");
	                    	 cid.setBounds(280,50,100,30);
	                    	 l1.setBounds(150, 50, 100, 30);
	                    	 
	                    	 JTextArea cname=new JTextArea("");
	                    	 JLabel l2 = new JLabel("Customer Name:");
	                    	 l2.setBounds(150, 150, 100, 30);
	                    	 cname.setBounds(280,150,100,30);
	                    	 
	                    	 JTextArea ctel=new JTextArea("");
	                    	 JLabel l3 = new JLabel("Telephone Number:");
	                    	 l3.setBounds(150, 250, 150, 30);
	                    	 ctel.setBounds(280,250,100,30);
	                    	 
	                    	 bTemp.setBounds(150,350,150,30);
	                    	 
	                    	 //action listener code of insert button
	                    	 bTemp.addActionListener(new ActionListener(){  
	        	                 public void actionPerformed(ActionEvent e) {              
	        	                     try {
	        							String s1 = cid.getText();
	        							String s2 = cname.getText();
	        							String s3 = ctel.getText();
	        							cs = conn.prepareCall("begin Project2_PACKAGE.add_customer(?, ?, ?); end;");
	        							cs.setString(1, s1);
	        							cs.setString(2, s2);
	        							cs.setString(3, s3);
	        							cs.execute();
	        							area.setText("Data Inserted Successfully");
	        							f_temp.dispose();
	        						}catch(SQLException se){
	        							f_temp.dispose();
	        							//area.setText("\n*** SQLException caught when attempting to insert a record ***\n");
	        							if(cid.getText().equals("")){area.setText("Customer ID is null.");}
	        	                     else if(cname.getText().equals("")){area.setText("Customer name is null.");}
	        	                     else if(ctel.getText().equals("")){area.setText("Customer telephone number is null.");}}
	        	                     catch (Exception e1) {
	        							// TODO Auto-generated catch block
	        							//e1.printStackTrace();
	        	                    	 f_temp.dispose();
	        							area.setText(e1.getMessage());
	        						}  
	        	                 }  
	        	                });
	                    	 
	                    	 
	                    	 f_temp.add(l1);
	                    	 f_temp.add(l2);
	                    	 f_temp.add(l3);
	                    	 f_temp.add(bTemp);
	                    	 f_temp.add(cid);
	                    	 f_temp.add(cname);
	                    	 f_temp.add(ctel);
	                    	 f_temp.setSize(500,500);
	                    	 f_temp.setLayout(null);  
	                 	     f_temp.setVisible(true);   
						} /*catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/ catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	            
	             
	             
	             pur.addActionListener(new ActionListener(){  
	                 public void actionPerformed(ActionEvent e) {              
	                     try {
	                    	 JFrame f_temp=new JFrame("Insert Details");
	                    	 
	                    	 JButton bTemp=new JButton("Insert");
	                    	 
	                    	 JTextArea eid=new JTextArea("");
	                    	 JLabel l1 = new JLabel("Employee id:");
	                    	 eid.setBounds(250,50,100,30);
	                    	 l1.setBounds(150, 50, 100, 30);
	                    	 
	                    	 JTextArea pid=new JTextArea("");
	                    	 JLabel l2 = new JLabel("Product id:");
	                    	 pid.setBounds(250,150,100,30);
	                    	 l2.setBounds(150, 150, 100, 30);
	                    	 
	                    	 JTextArea cid=new JTextArea("");
	                    	 JLabel l3 = new JLabel("Customer id:");
	                    	 cid.setBounds(250,250,100,30);
	                    	 l3.setBounds(150, 250, 100, 30);
	                    	 
	                    	 JTextArea qty=new JTextArea("");
	                    	 JLabel l4 = new JLabel("Quantity:");
	                    	 qty.setBounds(250,350,100,30);
	                    	 l4.setBounds(150, 350, 100, 30);
	                    	 
	                    	 bTemp.setBounds(150,450,150,30);
	                    	 
	                    	 bTemp.addActionListener(new ActionListener(){  
	        	                 public void actionPerformed(ActionEvent e) {              
	        	                     try {
	        							String s1 = eid.getText();
	        							String s2 = pid.getText();
	        							String s3 = cid.getText();
	        							String s4 = qty.getText();
	        							Product oldProd = new Product(s2, conn);
	        							if(Integer.parseInt(s4)<1)
	        							{
	        								f_temp.dispose();
	        								area.setText("Unable to purchase less than 1 of any product at the moment.");
	        							}
	        							else if(Integer.parseInt(s4)<oldProd.getQoh() && Integer.parseInt(s4)>0)
	        							{
	        								cs = conn.prepareCall("begin Project2_PACKAGE.add_Purchases(?,?,?,?,?); end;");
	        								cs.setString(1, s1);
	        								cs.setString(2, s2);
	        								cs.setString(3, s3);
	        								cs.setInt(4, Integer.parseInt(s4));
	        								cs.registerOutParameter(5, Types.VARCHAR);
	        								cs.execute();
	        								area.setText(cs.getString(5));
	        								Product newProd = new Product(s2, conn);
	        								//area.setText(oldProd.getQoh()+" "+newProd.getQoh());
	        								if(newProd.getQoh() != (oldProd.getQoh() - Integer.parseInt(s4)))
	        								{
	        									int supply = newProd.getQoh() - (oldProd.getQoh() - Integer.parseInt(s4));
	        									area.setText("The supply for this product ran below the threshold. A new supply in the quantity of " + supply + " was ordered.\n");
	        								}
	        								area.append("\nPurchase made successfully");
	        								f_temp.dispose();
	        							}
	        							else
	        							{
	        								f_temp.dispose();
	        								area.setText("Insufficient quantity in stock, the purchase request is rejected");
	        							}
	        						}catch(SQLException se){
	        							f_temp.dispose();
	        							//area.setText("\n*** SQLException caught when attempting to insert a record ***\n");
	        							//area.setText(se.getMessage());
	        							if(eid.getText().equals("")){area.setText("Employee ID is null.");}
	        							else if(pid.getText().equals("")){area.setText("Product ID is null.");}
	        							else if(cid.getText().equals("")){area.setText("Customer ID is null");}
	        							else if(qty.getText().equals("")){area.setText("Purchase Quantity is null.");}
	        							else {area.setText(se.getMessage());}
	        							}
	        	                     catch (Exception e1) {
	        							// TODO Auto-generated catch block
	        							//e1.printStackTrace();
	        	                    	 f_temp.dispose();
	        							area.setText(e1.getMessage());
	        						}  
	        	                 }  
	        	                });
	                    	 
	                    	 f_temp.add(l1);
	                    	 f_temp.add(l2);
	                    	 f_temp.add(l3);
	                    	 f_temp.add(l4);
	                    	 f_temp.add(bTemp);
	                    	 f_temp.add(cid);
	                    	 f_temp.add(eid);
	                    	 f_temp.add(qty);
	                    	 f_temp.add(pid);
	                    	 f_temp.setSize(500,600);
	                    	 f_temp.setLayout(null);  
	                 	     f_temp.setVisible(true); 
	                    	 
	                    	 
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	             
	    	        }  
	    	    });
	    
	    //Calculate the purchase saving
	    b3.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		
	    		try{
	    			JFrame f_temp=new JFrame("Insert Details");
               	 	JButton bTemp=new JButton("Compute");
               	 	JTextArea pid=new JTextArea("");
               	 	JLabel l1 = new JLabel("Purchase id:");
               	 	pid.setBounds(250,100,100,30);
               	 	bTemp.setBounds(150,200,150,30);
               	 	l1.setBounds(150,100,100,30);
	                    	 
	               //action listener code of insert button
               	 	bTemp.addActionListener(new ActionListener(){  
               	 		public void actionPerformed(ActionEvent e) {              
               	 			try {
               	 					String s1 = pid.getText();
               	 					if(!s1.equals(""))
               	 					{
               	 						cs = conn.prepareCall(" {? = call Project2_PACKAGE.purchase_saving(?)}");
               	 						cs.registerOutParameter(1, Types.FLOAT);
               	 						cs.setInt(2, Integer.parseInt(s1));
               	 						cs.execute();
               	 						f_temp.dispose();
               	 						area.setText(Float.toString(cs.getFloat(1)));
               	 					}
               	 					else{throw new SQLException("Purchase ID is null."); }
	        					}catch(SQLException se){
	        							f_temp.dispose();
	        							//area.setText("\n*** SQLException caught when attempting to compute a record ***\n");
	        							area.setText(se.getMessage());}
	        	                     catch (Exception e1) {
	        							// TODO Auto-generated catch block
	        							//e1.printStackTrace();
	        	                    	 f_temp.dispose();
	        	                    	// area.setText("Please enter a Purchase id");
	        						}  
	        	                 }  
	        	                });
               	 			 f_temp.add(l1);
	                    	 f_temp.add(bTemp);
	                    	 f_temp.add(pid);
	                    	 f_temp.setSize(500,500);
	                    	 f_temp.setLayout(null);  
	                 	     f_temp.setVisible(true);   
						} /*catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/ catch (Exception e1) {
							// TODO Auto-generated catch block
							area.setText(e1.getMessage());
						}  
	                 }  
	                });
	    
	    //Monthly Activity Report of an Employee
	    b4.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		
	    		try{
	    			JFrame f_temp=new JFrame("Insert Details");
               	 	JButton bTemp=new JButton("Search");
               	 	JTextArea eid=new JTextArea("");
               	 	JLabel l1 = new JLabel("Employee id:");
               	 	eid.setBounds(250,100,100,30);
               	 	bTemp.setBounds(150,200,150,30);
               	 	l1.setBounds(150,100,100,30);
	                    	 
	               //action listener code of insert button
               	 	bTemp.addActionListener(new ActionListener(){  
               	 		public void actionPerformed(ActionEvent e) {              
               	 			try {
               	 					boolean isEmpty = true;
               	 					String s1 = eid.getText(); //monthly_sale_activities
               	 					cs = conn.prepareCall("{call Project2_PACKAGE.monthly_sale_activities(?,?)}");
               	 					cs.setString(1, s1);
               	 					cs.registerOutParameter(2, OracleTypes.CURSOR);
               	 					cs.execute();
               	 					ResultSet rs = ((OracleCallableStatement)cs).getCursor(2);
               	 					
               	 					if(!s1.equals("")){
               	 					area.setText("");
               	 					String output = "EID\tENAME\tMONTH\tYEAR\tQty_Sold\tTOTAL_SALE\tTOTAL_SPENT\n";
               	 					while (rs.next()) {
               	 						isEmpty = false;
               	 						output = output + (rs.getString("eid")+"\t"+rs.getString("name")+"\t"+rs.getString("Month") + "\t" + rs.getString("Year") +"\t"+rs.getString("QTY_SOLD")+"\t"+rs.getString("TOTAL_SALE")+"\t"+rs.getString("TOTAL_SPENT")+"\n");
               	 					}
	        						f_temp.dispose();
	        						if(isEmpty == false)
	        						{
	        							area.append(output);
	        						}
	        						//else{throw new SQLException("There is no employee with this eid.");}
	        						}
               	 					else{f_temp.dispose();throw new SQLException("Employee ID is null.");}
	        					}catch(SQLException se){
	        							f_temp.dispose();
	        							//area.setText("\n*** SQLException caught when attempting to compute a record ***\n");
	        							area.append(se.getMessage());}
	        	                     catch (Exception e1) {
	        							// TODO Auto-generated catch block
	        							//e1.printStackTrace();
	        	                    	f_temp.dispose();
	        							area.setText("Please enter a valid employee id");
	        						}  
	        	                 }  
	        	                });
               	 			 f_temp.add(l1);
	                    	 f_temp.add(bTemp);
	                    	 f_temp.add(eid);
	                    	 f_temp.setSize(500,500);
	                    	 f_temp.setLayout(null);  
	                 	     f_temp.setVisible(true);   
						} /*catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/ catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	    
	    //delete record from the table
	    b5.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		
	    		try{
	    			JFrame f_temp=new JFrame("Insert Details");
               	 	JButton bTemp=new JButton("Delete");
               	 	JTextArea purid=new JTextArea("");
               	 	JLabel l1 = new JLabel("Purchase id:");
               	 	purid.setBounds(250,100,100,30);
               	 	bTemp.setBounds(150,200,150,30);
               	 	l1.setBounds(150,100,100,30);
	                    	 
	               //action listener code of insert button
               	 	bTemp.addActionListener(new ActionListener(){  
               	 		public void actionPerformed(ActionEvent e) {              
               	 			try {
               	 					String s1 = purid.getText();
               	 					cs = conn.prepareCall("begin Project2_PACKAGE.delete_Purchases(?); end;");
               	 					cs.setString(1, s1);
               	 					if(!s1.equals("")){
               	 					cs.execute();
	        						f_temp.dispose();
	        						area.setText("Purchase deleted is: "+s1);
	        						}
               	 					else{area.setText("Purchase ID is null.");f_temp.dispose();}
	        					}catch(SQLException se){
	        							f_temp.dispose();
	        							area.setText("\n*** SQLException caught when attempting to insert a record ***\n");
	        							area.append(se.getMessage());}
	        	                     catch (Exception e1) {
	        							// TODO Auto-generated catch block
	        							//e1.printStackTrace();
	        	                    	 f_temp.dispose();
	        							area.setText(e1.getMessage());
	        						}  
	        	                 }  
	        	                });
               	 			 f_temp.add(l1);
	                    	 f_temp.add(bTemp);
	                    	 f_temp.add(purid);
	                    	 f_temp.setSize(500,500);
	                    	 f_temp.setLayout(null);  
	                 	     f_temp.setVisible(true);   
						} /*catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/ catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
	                 }  
	                });
	    
	    
	    
	    //Exit
	    b6.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		
	    		f.dispose();
	    		Main.exit_function();
	                 }  
	                });
	    
	    
	    
	    f.add(b1);
	    f.add(b2);
	    f.add(b3);
	    f.add(b4);
	    f.add(b5);
	    f.add(b6); 
	    
        f.add(area);
        //f.add(scroll);
	    f.setSize(5000,800);  
	    f.setLayout(null);  
	    f.setVisible(true);
	}
	
}
