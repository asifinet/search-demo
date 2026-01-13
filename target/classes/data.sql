insert into customers (name, email) values ('Alice Johnson', 'alice@example.com');
insert into customers (name, email) values ('Bob Smith', 'bob@example.com');
insert into customers (name, email) values ('Charlie Brown', 'charlie@example.com');

insert into orders (customer_id, product_name, total_amount, created_at)
values (1, 'Laptop', 1299.99, CURRENT_TIMESTAMP());

insert into orders (customer_id, product_name, total_amount, created_at)
values (1, 'Mouse', 25.50, CURRENT_TIMESTAMP());

insert into orders (customer_id, product_name, total_amount, created_at)
values (2, 'Keyboard', 75.00, CURRENT_TIMESTAMP());
