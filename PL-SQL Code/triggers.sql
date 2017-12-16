--this package is used to create all triggers in question 6
--whenever there is an insert into customer, purchase and supply
--the respective changes are inserted into the logs table
--whenever we update qoh or last_visit_date
--the rows are inserted into the logs table

drop trigger insertPurchaseTrigger;
drop trigger insertCustomersTrigger;
drop trigger updateLastVisitDateTrigger;
drop trigger updateQohTrigger;
drop trigger insertSuppliesTrigger;
drop trigger setQohTrigger;
drop trigger setVisitsTrigger;

---------------------------[1]--------------------------------------- 
--To insert a new entry to logs table after a new customer is added
create or replace trigger insertCustomersTrigger
after insert on customers
for each row
declare
cust_id customers.cid%type;
begin
--Record the customers entry as a log entry record
	insert into logs
	values
	(log#_seq.nextval, user, 'insert', sysdate, 'customers', :NEW.cid);
	dbms_output.put_line('New Customer entry is added ' || cust_id);
end;
/
show err;

------------------------------[2]------------------------------------ 
--To insert into logs table after last_visit_date is updated
create or replace trigger updateLastVisitDateTrigger
after update of last_visit_date on customers
for each row
begin
	insert into logs
	values
	(log#_seq.nextval, user, 'update', sysdate, 'customers', :NEW.cid);
	dbms_output.put_line('Customers last_visit_date is updated ');

end;
/
show err;


----------------------------[3]-----------------------------
--To insert a new entry to logs table after a new purchase is made

create or replace trigger insertPurchaseTrigger
after insert on purchases
FOR EACH ROW

declare
pur_id purchases.pid%type;
pro_id products.pid%type;	
new_Qoh products.qoh%type;
prod products%rowtype;
supplier_id supplies.sid%type;
supplier_count number(6);
cust_visits customers.visits_made%type;
last_visit customers.last_visit_date%type;
cust_id customers.cid%type;

begin
	pro_id := :NEW.pid;
	dbms_output.put_line('A Product just purchased: ' || pro_id);

	-- Insert the purchases into log record
	insert into logs
	VALUES
	(log#_seq.nextval, user, 'insert', sysdate, 'purchases', :NEW.pur#);

	--Select the products original values first
	select * 
	into prod
	from products p
	where p.pid = pro_id;	
	
	--Now update the product's qoh
	update products
	set qoh = (prod.qoh - :NEW.qty)
	where pid = pro_id;

	--Select the new products values
	select * 
	into prod
	from products p
	where p.pid = pro_id;	

	--Check the qoh is below qoh_threshold or not
	if(prod.qoh < prod.qoh_threshold) then
	--Here we want to add a new quantity of supply i.e. 1 and so difference between the threshold and qoh + 1 which is m
	--Now the amount to order is m + 10, so that gives a difference + 11.

	        new_Qoh := prod.qoh_threshold - prod.qoh + 11;
		dbms_output.put_line('The current qoh for ' || prod.name || ' is too low. A supply of at least ' || to_char(new_Qoh) || ' is needed to be above the threshold.');

		--Checking the total number of suppliers
		select count(*)
		into supplier_count
		from supplies
		where supplies.pid = pro_id;		

		--If no suppliers supplied for this product, then raise an application error
		if(supplier_count < 1) then
			raise_application_error(-20123, 'No supplier has ever supplied ' || prod.name || '. Unable to order supply.');
	--	dbms_output.put_line('Unable to order supply.');

		end if;
		
		--Find the topmost supplier
		select sid
		into supplier_id
		from supplies
		where pid = pro_id
		and rownum = 1
		order by sup# asc;

		--Now inserrt into supplies with this supplier id, sysdate, the new_Qoh 
		insert into supplies
		VALUES
		(sup#_seq.nextval, pro_id, supplier_id, sysdate, new_Qoh);

	end if;

	--If purchase is made on a new date then update the last_visit_date and visits_made of a customer
	select c.cid, last_visit_date, visits_made
	into cust_id, last_visit, cust_visits
	from customers c
	where :NEW.cid = cid;
	
	if(to_char(last_visit , 'DD-MON-YY') != to_char(:NEW.ptime, 'DD-MON-YY'))
	then 
		update customers c
		set c.visits_made = cust_visits + 1
		where c.cid = cust_id;
	
		update customers c
		set c.last_visit_date = :NEW.ptime
		where c.cid = cust_id;
	end if; 

        end;
/
show err;

------------------------------[4]------------------------------------------------ 
--To add a new entry into log table after qoh is updated
create or replace trigger updateQohTrigger
after update of qoh on products
for each row
begin
	insert into logs
	values
	(log#_seq.nextval, user, 'update', sysdate, 'products', :NEW.pid);
	dbms_output.put_line('QOH is updated ');
end;
/
show err

------------------------------[5]---------------------------------------------
--To insert into logs table after a new supply is inserted
create or replace trigger insertSuppliesTrigger
after insert on supplies
for each row
declare
pro_id products.pid%type;	
begin
	--Record the supply into the logs
	insert into logs
	values
	(log#_seq.nextval, user, 'insert', sysdate, 'supplies', :NEW.sup#);
	dbms_output.put_line('New supply for item : ' || :NEW.pid || ' with sup# : ' || :NEW.sup# || ' and qty :' || :NEW.quantity);

--update the products qoh based on the quantity of supply ordered
		update products 
		set qoh = (qoh + :NEW.quantity)
		where pid = :NEW.pid;
end;
/
show err
-------------------------------[6]---------------------------
create or replace trigger setQohTrigger
after delete on purchases
FOR EACH ROW
declare
ppid products.pid%type;	
prod products%rowtype;
begin
--	ppid := :OLD.pid;

	select * 
	into prod
	from products p
	where p.pid = :OLD.pid;	
	
         update products p
       	 set p.qoh = (p.qoh + :OLD.qty)
 	 where p.pid = :OLD.pid;
end;
/
show err;

-------------------------------[7]---------------------------

create or replace trigger setVisitsTrigger
after delete on purchases
FOR EACH ROW
declare
visits customers.visits_made%type;
last_visit customers.last_visit_date%type;
custID customers.cid%type;
begin

select c.cid, last_visit_date, visits_made
	into custID, last_visit, visits
	from customers c
	where :OLD.cid = cid;
	
--	if(to_char(last_visit , 'DD-MON-YY') = to_char(:OLD.ptime, 'DD-MON-YY'))
--	then 
		update customers c
		set c.visits_made = visits + 1
		where c.cid = custID;
	
		update customers c
		set c.last_visit_date = sysdate
		where c.cid = custID;
--	end if; 
end; 
/
show error;
