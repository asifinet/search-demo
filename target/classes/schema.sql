drop table if exists orders;
drop table if exists customers;

create table customers (
  id bigint auto_increment primary key,
  name varchar(255) not null,
  email varchar(255) not null unique
);

create table orders (
  id bigint auto_increment primary key,
  customer_id bigint not null,
  product_name varchar(255) not null,
  total_amount decimal(12,2) not null,
  created_at timestamp not null,
  constraint fk_orders_customer foreign key (customer_id) references customers(id)
);
