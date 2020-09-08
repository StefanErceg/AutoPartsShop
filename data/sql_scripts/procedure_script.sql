delimiter $$

create procedure number_of_purchases (in Customer_ID int, out Result int) 
begin
	select count(bill.ID) into Result from bill
    inner join user_bank on bill.User_Bank_ID = user_bank.ID 
    inner join customer on user_bank.User_ID = customer.ID
    where customer.ID = Customer_ID;

end$$
delimiter ;