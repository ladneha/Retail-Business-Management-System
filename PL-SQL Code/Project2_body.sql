set serveroutput on
CREATE OR REPLACE PACKAGE BODY Project2_PACKAGE AS

function show_employees
	return ref_cursor as rc ref_cursor;
	begin
		open rc for
		select * from employees;
		return rc;
	end;
	
function show_customers
	return ref_cursor as rc ref_cursor;
	begin 
		open rc for
		select * from customers;
		return rc;
	end;

function show_products
	return ref_cursor as rc ref_cursor;
	begin
		open rc for
		select * from products;
		return rc;
	end;

function show_suppliers
	return ref_cursor as rc ref_cursor;
	begin 
		open rc for
		select * from suppliers;
		return rc;
	end;

function show_supplies return ref_cursor as rc ref_cursor;
	begin
		open rc for
		select * from supplies;
		return rc;
	end;
	
function show_purchases
	return ref_cursor as rc ref_cursor;
	begin
		open rc for
		select * from purchases;
		return rc;
	end;
	
function show_discounts	return ref_cursor as rc ref_cursor;
	begin
		open rc for
		select * from discounts;
		return rc;
	end;

function show_logs
	return ref_cursor as rc ref_cursor;
	begin
		open rc for
		select * from logs;
		return rc;
	end;

function purchase_saving
       (pur_no in purchases.pur#%type)
        return number is total_saving number;
		begin
	--If pur_no is null then raise error
		    if (pur_no is NULL) then
			    raise_application_error(-20625, 'Purchase ID is null.');
		    end if;
	
            select pr.original_price*pu.qty-pu.total_price total_sav into total_saving from purchases pu, products pr where pu.pid=pr.pid and pur#=pur_no; 
	        return (total_saving);
        end;
		
procedure monthly_sale_activities(eid_in in employees.eid%type, ref_cursor out SYS_REFCURSOR) is countEid int:=0;
begin
	--If the eid is null then raise error
		if (eid_in is NULL) then
			raise_application_error(-20625, 'Employee ID is null.');
		end if;
		
	--This query checks whether the eid passed in parameter is an existing employee
select count(*) into countEid from employees where eid = eid_in;
	if (countEid < 1) then 
			raise_application_error(-20123, 'There is no employee with this eid.');
	end if;

	open ref_cursor for
	--Following query returns the employees monthly sale
		select e.eid, e.name, 
  			to_char(pu.ptime, 'MON') as Month, 
  			to_char(pu.ptime, 'YYYY') as Year,
                        count(pu.PUR#) as TOTAL_SALE,
 			sum(qty) as QTY_SOLD,
                        sum(total_price) as TOTAL_SPENT
 		from employees e, purchases pu
 		where e.eid = pu.eid and e.eid = eid_in
 		group by e.eid,e.name, to_char(pu.ptime, 'MON'), to_char(pu.ptime, 'YYYY');
end;

procedure add_Customer (c_id in customers.cid%type, c_name in customers.name%type, c_telephone# in customers.telephone#%type) is
begin
--Check for null values first
		if c_id is NULL then
			raise_application_error(-20625, 'Customer ID is null.');
		elsif c_name is NULL then
			raise_application_error(-20625, 'Customer name is null.');
		elsif c_telephone# is NULL then
			raise_application_error(-20625, 'Customer telephone number is null.');
		end if;
	
	insert into customers values (c_id,c_name,c_telephone#,'1',SYSDATE);
end;

procedure add_Purchases(e_id in purchases.eid%type, p_id in purchases.pid%type, c_id in purchases.cid%type, pur_qty in purchases.qty%type, printoutput OUT varchar) is

total_price_In number(7,2);
total_qoh number(5);
org_price number(6,2);
category number (6);
discount  number(3,2);

--declare exceptions for string values
		val_large exception;
		pragma exception_init(val_large, -12899);
		not_parent exception;
		pragma exception_init(not_parent, -2291);
		--Need no data found exception

begin
--Check the null values
		if e_id is NULL then
			raise_application_error(-20625, 'Employee ID is null.');
		elsif p_id is NULL then
			raise_application_error(-20625, 'Product ID is null.');
		elsif c_id is NULL then
			raise_application_error(-20625, 'Customer ID is null.');
		elsif pur_qty is NULL then
			raise_application_error(-20625, 'Purchase Quantity is null.');
		end if;

Select original_price, discnt_category into org_price,category from products p where p.pid = p_id;
Select discnt_rate into discount from discounts where discnt_category = category;
select qoh into total_qoh from products where pid = p_id;
 
	total_price_In:=(org_price*(1-discount))* pur_qty;

if  (total_qoh-pur_qty)>=0 then
 insert into purchases values (pur#_seq.nextval,e_id,p_id,c_id, pur_qty, SYSDATE, total_price_In);
--	 dbms_output.put_line('Purchase made successfully');
	printoutput:='Purchase made Successfully';

end if;

if  (total_qoh-pur_qty)<0 then
 --	dbms_output.put_line('Insufficient quantity in stock, the purchase request is rejected');
	 printoutput:='Insufficient quantity in stock, the purchase request is rejected';
end if;

exception
		--When any parameter is larger than its type
		when val_large then
			raise_application_error(-20899, 'Parameter in procedure -addPurchases- is too large', true);
		--When the foreign key is not valid, has no matching parent key in table
		when not_parent then
			raise_application_error(-20291, 'No parent key found for procedure -addpurchases', true);
		--Any other exception
		when others then
			raise_application_error(-20999, 'SQL Exception caught', true);
	
end;

procedure delete_Purchases(pur_id in purchases.pur#%type) is
pur_cid purchases.cid%type; countPur_id int:=0;
begin
	--Check the null values
	if pur_id is NULL then
			raise_application_error(-20625, 'Purchase ID is null.');
	end if;

select count(*) into countPur_id from purchases where pur# = pur_id;

if (countPur_id < 1) then 
			raise_application_error(-20123, 'No record found of this id.');
	end if;

	delete from purchases where pur#=pur_id;
	     dbms_output.put_line('Purchase deleted is: ' ||  pur_id);   
end;
	
end;
/
show errors;
