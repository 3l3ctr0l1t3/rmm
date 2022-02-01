insert into system_type (id, type) values (1,'Mac');
insert into system_type (id, type) values (2,'Windows Server');
insert into system_type (id, type) values (3,'Windows Workstation');

insert into customer (id, alias, email, password) values (1, 'Customer 1', 'customer@company.com', '$2a$10$h89v.8tVpGpM2Wu2lqGBAO2xy9VYfKXOFv9NfByTEC/SYTKPw7z2K');
insert into customer (id, alias, email, password) values (2, 'Customer 2', 'customer2@company.com', '$2a$10$GCMLeRFDgDj2IP3UZYgmu.inK9EYZ0bRw5b4Em0za00XwiDeiqaWG');

insert into device (id, system_name, system_type_id, customer_id)
values (1, 'Customer 1 Device 1', 2, 1);
insert into device (id, system_name, system_type_id, customer_id)
values (2, 'Customer 1 Device 2', 1, 1);
insert into device (id, system_name, system_type_id, customer_id)
values (3, 'Customer 1 Device 3', 3, 1);
insert into device (id, system_name, system_type_id, customer_id)
values (4, 'Customer 1 Device 4', 3, 1);
insert into device (id, system_name, system_type_id, customer_id)
values (5, 'Customer 1 Device 5', 1, 1);
insert into device (id, system_name, system_type_id, customer_id)
values (6, 'Customer 2 Device 1', 3, 2);
insert into device (id, system_name, system_type_id, customer_id)
values (7, 'Customer 2 Device 2', 1, 2);

insert into service (id, default_price, name)
values (1, 100, 'Antivirus');
insert into service (id, default_price,  name)
values (2, 33, 'Cloudberry');
insert into service (id, default_price, name)
values (3, 21, 'TeamViewer');
insert into service (id, default_price, name)
values (4, 200, 'Antivirus');
insert into service (id, default_price,  name)
values (5, 133,'Cloudberry');
insert into service (id, default_price, name)
values (6, 121, 'TeamViewer');

insert into subscription (id,customer_id,service_id)
values (1,1,2);
insert into subscription (id,customer_id,service_id)
values (2,2,2);
insert into subscription (id,customer_id,service_id)
values (3,2,1);
insert into subscription (id,customer_id,service_id)
values (4,1,4);
insert into subscription (id,customer_id,service_id)
values (5,1,3);
insert into subscription (id,customer_id,service_id)
values (6,1,1);

insert into price (id,price,service_id,system_type_id)
values (1,20,2,1);
insert into price (id,price,service_id,system_type_id)
values (2,21,2,3);
insert into price (id,price,service_id,system_type_id)
values (3,0,1,1);
