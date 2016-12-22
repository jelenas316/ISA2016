drop table if exists GRADE;
drop table if exists RESERVATION_TABLE;
drop table if exists RESERVATION_GUESTS;
drop table if exists RESERVATION_ORDERS;
drop table if exists RESERVATION_INVITED_FRIENDS;
drop table if exists RESERVATION;
drop table if exists ORDER_LIST_DRINKS;
drop table if exists ORDER_LIST_FOOD;
drop table if exists ORDER_LIST;
drop table if exists ORDERED_DRINK;
drop table if exists ORDERED_FOOD;
drop table if exists GUEST_FRIEND_REQUESTS;
drop table if exists GUEST_REQUESTS_FOR_FRIENDS;
drop table if exists GUEST_RESERVATION_REQUESTS;
drop table if exists GUEST_FRIENDS;
drop table if exists GUEST;
drop table if exists USER;
drop table if exists SYSTEM_MANAGER;
drop table if exists RESTAURANT_MANAGER;
drop table if exists RESTAURANT_DRINKS;
drop table if exists RESTAURANT_MENU;
drop table if exists RESTAURANT_TABLES;
drop table if exists RESTAURANT;
drop table if exists DRINK;
drop table if exists FOOD;
drop table if exists RESTAURANT_TABLE;
drop table if exists PROFILE;


create table GUEST
(
   EMAIL                   		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   ROLE 						enum('GUEST','RESTAURANT_MANAGER', 'WAITER',' COOK', 'BARTENDER', 'SYSTEM_MANAGER', 'BIDDER')          			not null,
   FRIENDS						varchar(50)						,
   FRIEND_REQUESTS				varchar(50)						,
   primary key (EMAIL)
);

create table GUEST_FRIENDS
(
   FRIENDS_ID					bigint							not null AUTO_INCREMENT,
   GUEST_EMAIL                	varchar(50)	                   	not null,
   FRIENDS_EMAIL                varchar(50)            			not null,
   primary key (FRIENDS_ID)
);


create table GUEST_FRIEND_REQUESTS
(
   REQUESTS_ID					bigint							not null AUTO_INCREMENT,
   GUEST_EMAIL                	varchar(50)	                   	not null,
   FRIEND_REQUESTS_EMAIL        varchar(50)            			not null,
   primary key (REQUESTS_ID)
);  


/*-------------------------------------------------------------*/

alter table GUEST add constraint FK_G_FRIENDS foreign key (FRIENDS)
      references GUEST (EMAIL) on delete restrict on update restrict;
alter table GUEST add constraint FK_G_FRIEND_REQUESTS foreign key (FRIEND_REQUESTS)
      references GUEST (EMAIL) on delete restrict on update restrict;   
      

alter table GUEST_FRIENDS add constraint FK_GF_GUEST foreign key (GUEST_EMAIL)
      references GUEST (EMAIL) on delete restrict on update restrict;
alter table GUEST_FRIENDS add constraint FK_GF_FRIEND foreign key (FRIENDS_EMAIL)
      references GUEST (EMAIL) on delete restrict on update restrict;

alter table GUEST_FRIEND_REQUESTS add constraint FK_GFR_GUEST foreign key (GUEST_EMAIL)
      references GUEST (EMAIL) on delete restrict on update restrict;
alter table GUEST_FRIEND_REQUESTS add constraint FK_GFR_FRIEND_REQUESTS foreign key (FRIEND_REQUESTS_EMAIL)
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

/*----------FOOD---------------------*/
create table FOOD
(
   FOOD_ID                   	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(50)            			not null,
   PRICE								decimal 						not null
);
/*----------DRINK---------------------*/
create table DRINK
(
   DRINK_ID                   	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(50)            			not null,
   PRICE								decimal 						not null
);

/*----------RESTAURANT TABLE---------------------*/
create table RESTAURANT_TABLE
(
   RESTAURANT_TABLE_ID         	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NUMBER              					INT	                         	not null,
   POSITION                  			enum('SMOKE','NOSMOKE')      	not null
);
/*----------RESTAURANT---------------------*/
create table RESTAURANT
(
   RESTAURANT_ID                   	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(50)            			not null,
   DRINKS								bigint,
   TABLES								bigint,
   MENU									bigint,
   FOREIGN KEY (DRINKS) REFERENCES RESTAURANT(RESTAURANT_ID) on delete cascade on update cascade  ,
   FOREIGN KEY (TABLES) REFERENCES RESTAURANT(RESTAURANT_ID) on delete cascade on update cascade  ,
   FOREIGN KEY (MENU) REFERENCES RESTAURANT(RESTAURANT_ID) on delete cascade on update cascade  
);
create table RESTAURANT_DRINKS 
(
	RESTAURANT_DRINKS_ID bigint,
	RESTAURANT_RESTAURANT_ID bigint,
	DRINKS_DRINK_ID bigint,
    FOREIGN KEY (RESTAURANT_RESTAURANT_ID) REFERENCES RESTAURANT(RESTAURANT_ID) on delete cascade on update cascade  ,
    FOREIGN KEY (DRINKS_DRINK_ID) REFERENCES DRINK(DRINK_ID) on delete cascade on update cascade  
);
create table RESTAURANT_MENU 
(
	RESTAURANT_MENU_ID bigint,
	RESTAURANT_RESTAURANT_ID bigint,
	MENU_FOOD_ID bigint,
    FOREIGN KEY (RESTAURANT_RESTAURANT_ID) REFERENCES RESTAURANT(RESTAURANT_ID) on delete cascade on update cascade  ,
    FOREIGN KEY (MENU_FOOD_ID) REFERENCES FOOD(FOOD_ID) on delete cascade on update cascade  
);
create table RESTAURANT_TABLES 
(
	RESTAURANT_TABLES_ID bigint,
	RESTAURANT_RESTAURANT_ID bigint,
	TABLES_RESTAURANT_TABLE_ID bigint,
    FOREIGN KEY (RESTAURANT_RESTAURANT_ID) REFERENCES RESTAURANT(RESTAURANT_ID) on delete cascade on update cascade  ,
    FOREIGN KEY (TABLES_RESTAURANT_TABLE_ID) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) on delete cascade on update cascade  
);
/*----------RESTAURANT MANAGER---------------------*/
create table RESTAURANT_MANAGER
(
   EMAIL                   		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   ROLE 						enum('RESTAURANT_MANAGER')    			,
   RESTAURANT_ID				bigint							not null UNIQUE,
   FOREIGN KEY (RESTAURANT_ID) REFERENCES RESTAURANT(RESTAURANT_ID) on delete restrict on update restrict,
   primary key (EMAIL)
);
insert into system_manager(EMAIL, PASSWORD, NAME, SURNAME, ROLE)
VALUES('admin@admin.com','admin','pera','peric','SYSTEM_MANAGER');

create table GRADE
(
	GRADE_ID					bigint							not null AUTO_INCREMENT,
   	GRADE_VALUE           		INT	                   			not null,
   	RESTAURANT					bigint							,
   	GUEST						varchar(50)            			not null,
    primary key (GRADE_ID),
   	FOREIGN KEY (GUEST) REFERENCES GUEST(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table ORDERED_DRINK
(
	ORDERED_DRINK_ID					bigint													not null AUTO_INCREMENT,
   	QUANTITY							INT	            										not null,
   	DRINK								bigint													not null,
   	FOOD_STATUS							enum('ACCEPTED', 'WAITING', 'PREPARED', 'REJECTED')		not null,
    primary key (ORDERED_DRINK_ID),
   	FOREIGN KEY (DRINK) REFERENCES DRINK(DRINK_ID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table ORDERED_FOOD
(
	ORDERED_FOOD_ID						bigint													not null AUTO_INCREMENT,
   	QUANTITY							INT	            										not null,
   	FOOD								bigint													not null,
   	FOOD_STATUS							enum('ACCEPTED', 'WAITING', 'PREPARED', 'REJECTED')		not null,
    primary key (ORDERED_FOOD_ID),
   	FOREIGN KEY (FOOD) REFERENCES FOOD(FOOD_ID) ON DELETE CASCADE ON UPDATE CASCADE

);


create table ORDER_LIST
(
	ORDER_ID						bigint							not null AUTO_INCREMENT,
   	RESTAURANT_TABLE				bigint            				not null,
   	DRINKS							bigint							,
   	FOOD							bigint							,
    primary key (ORDER_ID),
   	FOREIGN KEY (RESTAURANT_TABLE) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (DRINKS) REFERENCES ORDERED_DRINK(ORDERED_DRINK_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (FOOD) REFERENCES ORDERED_FOOD(ORDERED_FOOD_ID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table ORDER_LIST_DRINKS
(
	ORDER_LIST_DRINKS_ID					bigint							not null AUTO_INCREMENT,
   	ORDER_ORDER_ID							bigint            				not null,
   	DRINKS_ORDERED_DRINK_ID					bigint							not null,
    primary key (ORDER_LIST_DRINKS_ID),
   	FOREIGN KEY (ORDER_ORDER_ID) REFERENCES ORDER_LIST(ORDER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (DRINKS_ORDERED_DRINK_ID) REFERENCES ORDERED_DRINK(ORDERED_DRINK_ID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table ORDER_LIST_FOOD
(
	ORDER_LIST_FOOD_ID						bigint							not null AUTO_INCREMENT,
   	ORDER_ORDER_ID							bigint            				not null,
   	FOOD_ORDERED_FOOD_ID					bigint							not null,
    primary key (ORDER_LIST_FOOD_ID),
   	FOREIGN KEY (ORDER_ORDER_ID) REFERENCES ORDER_LIST(ORDER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (FOOD_ORDERED_FOOD_ID) REFERENCES ORDERED_FOOD(ORDERED_FOOD_ID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table RESERVATION
(
	RESERVATION_ID					bigint							not null AUTO_INCREMENT,
   	ARRIVAL           				DATE	                   		not null,
   	DURATION						int								,
   	RESTAURANT_TABLE				bigint            				,
   	RESTAURANT						bigint							not null,
   	GUESTS							varchar(50)						,
   	INVITED_FRIENDS					varchar(50)						,
   	ORDERS							bigint							,
    primary key (RESERVATION_ID),
   	FOREIGN KEY (RESTAURANT_TABLE) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (GUESTS) REFERENCES GUEST(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (INVITED_FRIENDS) REFERENCES GUEST(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (ORDERS) REFERENCES ORDER_LIST(ORDER_ID) ON DELETE CASCADE ON UPDATE CASCADE

);

create table RESERVATION_GUESTS
(
	RESERVATION_GUESTS_ID			bigint							not null AUTO_INCREMENT,
   	RESERVATION_RESERVATION_ID		bigint							not null,
   	GUESTS_EMAIL					varchar(50)						not null,	
    primary key (RESERVATION_GUESTS_ID),
   	FOREIGN KEY (RESERVATION_RESERVATION_ID) REFERENCES RESERVATION(RESERVATION_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (GUESTS_EMAIL) REFERENCES GUEST(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE
);

create table RESERVATION_INVITED_FRIENDS
(
	RESERVATION_INVITED_FRIENDS_ID		bigint							not null AUTO_INCREMENT,
   	RESERVATION_RESERVATION_ID			bigint							not null,
   	INVITED_FRIENDS_EMAIL				varchar(50)						not null,	
    primary key (RESERVATION_INVITED_FRIENDS_ID),
   	FOREIGN KEY (RESERVATION_RESERVATION_ID) REFERENCES RESERVATION(RESERVATION_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (INVITED_FRIENDS_EMAIL) REFERENCES GUEST(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE
);

create table RESERVATION_TABLE
(
	RESERVATION_RESTAURANT_TABLE_ID			bigint							not null AUTO_INCREMENT,
   	RESERVATION_RESERVATION_ID				bigint							not null,
   	TABLE_RESTAURANT_TABLE_ID				bigint							not null,	
    primary key (RESERVATION_RESTAURANT_TABLE_ID),
   	FOREIGN KEY (RESERVATION_RESERVATION_ID) REFERENCES RESERVATION(RESERVATION_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (TABLE_RESTAURANT_TABLE_ID) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table RESERVATION_ORDERS
(
	RESERVATION_ORDERS_ID					bigint							not null AUTO_INCREMENT,
   	RESERVATION_RESERVATION_ID				bigint							not null,
   	ORDERS_ORDER_ID							bigint							not null,	
    primary key (RESERVATION_ORDERS_ID),
   	FOREIGN KEY (RESERVATION_RESERVATION_ID) REFERENCES RESERVATION(RESERVATION_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (ORDERS_ORDER_ID) REFERENCES ORDER_LIST(ORDER_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table PROFILE
(
	PROFILE_ID							bigint							not null AUTO_INCREMENT,
   	EMAIL								varchar(50)						not null,
   	PASSWORD							varchar(50)						not null,
   	NAME								varchar(50)						not null,
   	SURNAME								varchar(50)						not null,	
    primary key (PROFILE_ID)
);


insert into isa2016.guest values ('email','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email3','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email4','pass','name','surname','GUEST',null,null);


insert into restaurant (RESTAURANT_ID, NAME, DESCRIPTION) values (1,'name1','description');
insert into restaurant  (RESTAURANT_ID, NAME, DESCRIPTION) values (2,'name2','description');
insert into restaurant  (RESTAURANT_ID, NAME, DESCRIPTION) values (3,'name3','description');

insert into grade(GRADE_ID, GRADE_VALUE, RESTAURANT, GUEST) values (1,3,1,'email');
insert into grade(GRADE_ID, GRADE_VALUE, RESTAURANT, GUEST) values (2,4,1,'email3');
insert into grade(GRADE_ID, GRADE_VALUE, RESTAURANT, GUEST) values (3,5,1,'email4');

insert into restaurant_table values (1,5,'SMOKE');      


insert into drink values(1,'drink1','description',34.2);
insert into food values (1,'food1','description',43.2);




