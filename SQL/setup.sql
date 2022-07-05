drop table if exists customer;
CREATE TABLE customer(
	customer_id VARCHAR(36) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	phone_number VARCHAR(16) NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY (customer_id),
	CONSTRAINT customer_uq1 UNIQUE (phone_number)
);

drop table if exists account;
create table account (
	account_id  VARCHAR(36) not null,
	customer_id VARCHAR(36) not null,
	balance DOUBLE precision not null,
	description VARCHAR(128),
	constraint account_pk primary key (account_id),
	constraint account_fk1 foreign key (customer_id) references customer(customer_id)
)