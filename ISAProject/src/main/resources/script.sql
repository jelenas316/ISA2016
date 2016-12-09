
drop table if exists GUEST;
drop table if exists USER;

/*-----------GUEST-------------------*/

create table GUEST
(
   GUEST_ID						bigint							not null AUTO_INCREMENT,
   EMAIL                   		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   ROLE 						enum('GUEST','RESTAURANT_MANAGER', 'WAITER',' COOK', 'BARTENDER', 'SYSTEM_MANAGER', 'BIDDER')          			not null,
   primary key (GUEST_ID)
);

insert into isa2016.guest values (1,'email','pass','name','surname','GUEST');
insert into isa2016.guest values (2,'email2','pass2','name2','surname2','GUEST');



