create view product_all as
select product.ID as ProductID, product.Name as ProductName, product.Quantity as ProductQuantity, product.Barcode as ProductBarcode, product.Description as ProductDescription, product.Price as ProductPrice,
		manufacturer.ID as ProductManufacturerID, manufacturer.Name as ProductManufacturerName, manufacturer.Description as ProductManufacturerDescription, manufacturer.Headquarters as ProductManufacturerHeadquarters,
        city.ID as ProductCityID, city.Name as ProductCityName, city.Postcode as ProductCityPostcode, country.ID as ProductCountryID, country.Name as ProductCountryName, country.Abbreviation as ProductCountryAbbreviation,
        category.ID as ProductCategoryID, category.Name as ProductCategoryName
		from product
        inner join manufacturer on product.Manufacturer_ID = manufacturer.ID
        inner join city on manufacturer.City_ID = city.ID
        inner join country on city.Country_ID = country.ID
        inner join category on product.Category_ID = category.ID
        where product.IsActive = 1 and manufacturer.IsActive = 1 and city.IsActive = 1;
        
        
create view vehicle_all as 
select vehicle.ID as VehicleID, vehicle.Model as VehicleModel, vehicle.ProductionStart as vehicleProdStart, vehicle.ProductionEnd as vehicleProdEnd,
		manufacturer.ID as VehicleManufacturerID, manufacturer.Name as VehicleManufacturerName, manufacturer.Description as VehicleManufacturerDescription, manufacturer.Headquarters as VehicleManufacturerHeadquarters,
        city.ID as VehicleCityID, city.Name as VehicleCityName, city.Postcode as VehicleCityPostcode, country.ID as VehicleCountryID, country.Name as VehicleCountryName, country.Abbreviation as VehicleCountryAbbreviation,
        vehicle.Engine as VehicleEngine from vehicle
        inner join manufacturer on manufacturer.ID = vehicle.Manufacturer_ID
        inner join city on manufacturer.City_ID = city.ID
        inner join country on city.Country_ID = country.ID
        where manufacturer.IsActive = 1 and city.IsActive = 1 and vehicle.IsActive = 1;