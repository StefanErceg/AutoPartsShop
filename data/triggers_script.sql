drop trigger if exists deactivate_supplier;

delimiter $$;

create trigger deactivate_supplier after update on supplier_city
	for each row 
    begin
	if new.IsActive = 0 and (select exists (select * from supplier_city where supplier_city.Supplier_ID = new.Supplier_ID and supplier_city.IsActive = 1)) = 0
    then 
    begin
    update supplier 
    set supplier.isActive = 0
    where supplier.ID = old.Supplier_ID;
    end;
    end if;
    end $$;
    delimiter ;
