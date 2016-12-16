
drop table if exists GUEST_FRIENDS;
drop table if exists GUEST;
drop table if exists USER;
drop table if exists SYSTEM_MANAGER;
drop table if exists RESTAURANT;
drop table if exists DRINK;
drop table if exists FOOD;
drop table if exists RESTAURANT_TABLE;


/*-----------GUEST-------------------*/

create table GUEST
(
   EMAIL                   		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   ROLE 						enum('GUEST','RESTAURANT_MANAGER', 'WAITER',' COOK', 'BARTENDER', 'SYSTEM_MANAGER', 'BIDDER')          			not null,
   FRIENDS						varchar(50)						,
   primary key (EMAIL)
);

alter table GUEST add constraint FK_FRIENDS foreign key (FRIENDS)
      references GUEST (EMAIL) on delete restrict on update restrict;

/*----------GUEST FRIENDS---------------------*/

create table GUEST_FRIENDS
(
   FRIENDS_ID					bigint							not null AUTO_INCREMENT,
   GUEST_EMAIL                	varchar(50)	                   	not null,
   FRIENDS_EMAIL                varchar(50)            			not null,
   primary key (FRIENDS_ID)
);

alter table GUEST_FRIENDS add constraint FK_GUEST foreign key (GUEST_EMAIL)
      references GUEST (EMAIL) on delete restrict on update restrict;
alter table GUEST_FRIENDS add constraint FK_FRIEND foreign key (FRIENDS_EMAIL)
      references GUEST (EMAIL) on delete restrict on update restrict;
      
/*----------System managers---------------------*/

create table SYSTEM_MANAGER
(
   EMAIL                   		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   ROLE 						enum('SYSTEM_MANAGER')    			,
   primary key (EMAIL)
);
/*----------RESTAURANT---------------------*/
create table RESTAURANT
(
   RESTAURANT_ID                   	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(50)            			not null
);
/*----------FOOD---------------------*/
create table FOOD
(
   FOOD_ID                   	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(50)            			not null,
   PRICE								decimal 						not null,
   FOOD_RESTAURANT							bigint							not null,
   FOREIGN KEY (FOOD_RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID)  ON DELETE CASCADE ON UPDATE CASCADE
);
/*----------DRINK---------------------*/
create table DRINK
(
   DRINK_ID                   	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(50)            			not null,
   PRICE								decimal 						not null,
   DRINK_RESTAURANT						bigint							not null,
   FOREIGN KEY (DRINK_RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE
);
/*----------RESTAURANT TABLE---------------------*/
create table RESTAURANT_TABLE
(
   RESTAURANT_TABLE_ID         	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					INT	                         	not null,
   POSITION                  			enum('SMOKE','NOSMOKE')      	not null,
   RESTAURANT_TABLE						bigint							not null,
   FOREIGN KEY (RESTAURANT_TABLE) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE
);


insert into system_manager(EMAIL, PASSWORD, NAME, SURNAME, ROLE)
VALUES('admin@admin.com','admin','pera','peric','SYSTEM_MANAGER');


