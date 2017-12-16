CREATE OR REPLACE PACKAGE Project2_PACKAGE AS
TYPE ref_cursor IS REF CURSOR;

/*function headers*/
function show_employees
	return ref_cursor;
function show_customers
	return ref_cursor;
function show_products
	return ref_cursor;
function show_suppliers
	return ref_cursor;
function show_supplies 
	return ref_cursor;
function show_purchases
	return ref_cursor;
function show_discounts	
	return ref_cursor;
function show_logs	
	return ref_cursor;
function purchase_saving(pur_no in purchases.pur#%type)return number;
	
/* procedure headers*/
procedure monthly_sale_activities(eid_in in employees.eid%type, ref_cursor out SYS_REFCURSOR);
procedure add_customer (c_id in customers.cid%type, c_name in customers.name%type, c_telephone# in customers.telephone#%type);
procedure add_Purchases(e_id in purchases.eid%type, p_id in purchases.pid%type, c_id in purchases.cid%type, pur_qty in purchases.qty%type, printoutput OUT varchar);
procedure delete_Purchases(pur_id in purchases.pur#%type);

END;
/
show errors;
