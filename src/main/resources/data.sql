insert into system_type (id, type) values (1,'Mac');
insert into system_type (id, type) values (2,'Windows Server');
insert into system_type (id, type) values (3,'Windows Workstation');

insert into customer (id, alias, email, password) values (1, 'Customer 1', 'customer@company.com', 'secure_password');
insert into customer (id, alias, email, password) values (2, 'Customer 2', 'customer2@company.com', 'secure_password');

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

insert into service (id, price, system_type_id, name)
values (1, 100, 1, 'Antivirus');
insert into service (id, price, system_type_id,  name)
values (2, 33, 1,'Cloudberry');
insert into service (id, price, system_type_id, name)
values (3, 21, 1, 'TeamViewer');
insert into service (id, price, system_type_id, name)
values (4, 200, 2, 'Antivirus');
insert into service (id, price, system_type_id,  name)
values (5, 133, 2,'Cloudberry');
insert into service (id, price, system_type_id, name)
values (6, 121, 2, 'TeamViewer');