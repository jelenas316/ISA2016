drop table if exists GRADE;
drop table if exists RESERVATION_GUESTS;
drop table if exists RESERVATION_ORDERS;
drop table if exists RESERVATION_INVITED_FRIENDS;
drop table if exists RESERVATION_TABLES;
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
drop table if exists WAITER_SHIFTS;
drop table if exists WAITER;
drop table if exists BARTENDER_SHIFTS;
drop table if exists BARTENDER;
drop table if exists COOK_SHIFTS;
drop table if exists COOK;
drop table if exists SYSTEM_MANAGER;
drop table if exists OFFER_PRICES;
drop table if exists OFFER_GROCERY_LIST;
drop table if exists OFFER_FOOD;
drop table if exists OFFER_DRINKS;
drop table if exists OFFER_BIDDER;
drop table if exists OFFER;
drop table if exists GROCERY_LIST_DRINKS;
drop table if exists GROCERY_LIST_FOOD;
drop table if exists GROCERY_LIST;
drop table if exists BIDDER_OFFERS;
drop table if exists BIDDER;
drop table if exists DRINK_GROCERY;
drop table if exists FOOD_STUFF;
drop table if exists DRINK_OFFER;
drop table if exists FOOD_STUFF_OFFER;
drop table if exists PRICES;
drop table if exists DRINK_GROCERY_RESTAURANT;
drop table if exists FOOD_STUFF_RESTAURANT;
drop table if exists RESTAURANT_MANAGER;
drop table if exists RESTAURANT_DRINKS;
drop table if exists RESTAURANT_MENU;
drop table if exists RESTAURANT_TABLES;
drop table if exists SHIFT_REON;
drop table if exists SHIFT;
drop table if exists DRINK;
drop table if exists FOOD;
drop table if exists RESTAURANT_TABLE;
drop table if exists RESTAURANT;
drop table if exists PROFILE;
drop table if exists WORK_SCHEDULE;


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


alter table GUEST add constraint FK_G_FRIENDS foreign key (FRIENDS)
      references GUEST (EMAIL) on delete restrict on update restrict;
alter table GUEST add constraint FK_G_FRIEND_REQUESTS foreign key (FRIEND_REQUESTS)
      references GUEST (EMAIL) on delete restrict on update restrict;   
    
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
   DESCRIPTION                  		varchar(100)            		not null,
   PRICE								decimal 						not null
);
/*----------DRINK---------------------*/
create table DRINK
(
   DRINK_ID                   	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(100)            		not null,
   PRICE								decimal 						not null
);

/*----------RESTAURANT TABLE---------------------*/
create table RESTAURANT_TABLE
(
   RESTAURANT_TABLE_ID         	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NUMBER              					INT	                         	not null,
   POSITION                  			enum('SMOKEINSIDE', 'NOSMOKEINSIDE', 'SMOKEGARDEN', 'NOSMOKEGARDEN')      	not null,
   STATUS								enum('FREE', 'RESERVED')		not null,
   NUMBER_OF_SEATS						INT								not null
);

/*----------RESTAURANT---------------------*/
create table RESTAURANT
(
   RESTAURANT_ID                   	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   ADDRESS								varchar(100)					not null,
   DESCRIPTION                  		varchar(100)            		not null,
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
	RESTAURANT_TABLES_ID bigint not null AUTO_INCREMENT,
	RESTAURANT_RESTAURANT_ID bigint not null,
	TABLES_RESTAURANT_TABLE_ID bigint not null,
	primary key (RESTAURANT_TABLES_ID),
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

create table PROFILE
(
	PROFILE_ID							bigint							not null AUTO_INCREMENT,
   	EMAIL								varchar(50)						not null,
   	PASSWORD							varchar(50)						not null,
   	NAME								varchar(50)						not null,
   	SURNAME								varchar(50)						not null,	
    primary key (PROFILE_ID)
);

create table SHIFT
(
	SHIFT_ID							bigint							not null AUTO_INCREMENT,
	BEGIN_OF_SHIFT						TIME							not null,
	END_OF_SHIFT						TIME							not null,
	START_DATE							DATE							not null,
	END_DATE							DATE							not null,
	REON								bigint,
	primary key(SHIFT_ID),
	FOREIGN KEY (REON) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) on delete cascade on update cascade
);
create table SHIFT_REON
(
	SHIFT_REON_ID						bigint							not null AUTO_INCREMENT,
	SHIFT_SHIFT_ID						bigint							not null,
	REON_RESTAURANT_TABLE_ID			bigint							not null,
	primary key(SHIFT_REON_ID),
	FOREIGN KEY (SHIFT_SHIFT_ID) REFERENCES SHIFT(SHIFT_ID),
	FOREIGN KEY (REON_RESTAURANT_TABLE_ID) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID)
);

/*----------WAITER---------------------*/	
create table WAITER
(
   EMAIL                		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   DATE_OF_BIRTH				DATE               				not null,
   DRESS_SIZE					int              		    	not null,
   SHOES_SIZE					bigint             		    	not null,  
   RESTAURANT					bigint							not null,
   SHIFTS						bigint							,
   ROLE 						enum('WAITER')          			not null,
   ACTIVATED					boolean,
   primary key(EMAIL),
   FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (SHIFTS) REFERENCES SHIFT(SHIFT_ID) ON DELETE CASCADE ON UPDATE CASCADE
);
create table WAITER_SHIFTS
(
	WAITER_SHIFTS_ID					bigint							not null AUTO_INCREMENT,
    WAITER_EMAIL	 					varchar(50) 					not null,
	SHIFTS_SHIFT_ID						bigint							not null,
	primary key(WAITER_SHIFTS_ID),
	FOREIGN KEY (WAITER_EMAIL) REFERENCES WAITER(EMAIL),
	FOREIGN KEY (SHIFTS_SHIFT_ID) REFERENCES SHIFT(SHIFT_ID)
);

insert into isa2016.shift (SHIFT_ID,BEGIN_OF_SHIFT,END_OF_SHIFT, START_DATE, END_DATE) values (1,'8:00', '14:00', STR_TO_DATE('2017-3-10', '%Y-%m-%d'), STR_TO_DATE('2017-3-11', '%Y-%m-%d'));
insert into isa2016.shift (SHIFT_ID,BEGIN_OF_SHIFT,END_OF_SHIFT, START_DATE, END_DATE) values (2,'14:00', '20:00', STR_TO_DATE('2017-1-10', '%Y-%m-%d'), STR_TO_DATE('2017-3-10', '%Y-%m-%d'));
insert into isa2016.shift (SHIFT_ID,BEGIN_OF_SHIFT,END_OF_SHIFT, START_DATE, END_DATE) values (3,'10:00', '17:00', STR_TO_DATE('2017-2-10', '%Y-%m-%d'), STR_TO_DATE('2017-3-01', '%Y-%m-%d'));
insert into isa2016.shift (SHIFT_ID,BEGIN_OF_SHIFT,END_OF_SHIFT, START_DATE, END_DATE) values (4,'8:00', '14:00', STR_TO_DATE('2017-3-10', '%Y-%m-%d'), STR_TO_DATE('2017-3-11', '%Y-%m-%d'));
insert into isa2016.shift (SHIFT_ID,BEGIN_OF_SHIFT,END_OF_SHIFT, START_DATE, END_DATE) values (5,'14:00', '20:00', STR_TO_DATE('2017-1-10', '%Y-%m-%d'), STR_TO_DATE('2017-3-10', '%Y-%m-%d'));
insert into isa2016.shift (SHIFT_ID,BEGIN_OF_SHIFT,END_OF_SHIFT, START_DATE, END_DATE) values (6,'10:00', '17:00', STR_TO_DATE('2017-2-10', '%Y-%m-%d'), STR_TO_DATE('2017-3-01', '%Y-%m-%d'));

/*----------COOK---------------------*/

create table COOK
(
   EMAIL                		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   DATE_OF_BIRTH				DATE               				not null,
   DRESS_SIZE					int              		    	not null,
   SHOES_SIZE					bigint             		    	not null,  
   RESTAURANT					bigint							not null,
   SHIFTS						bigint							,
   ROLE 						enum('COOK')          			not null,
   ACTIVATED					boolean,
   primary key(EMAIL),
   FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (SHIFTS) REFERENCES SHIFT(SHIFT_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table COOK_SHIFTS
(
	COOK_SHIFTS_ID					bigint							not null AUTO_INCREMENT,
    COOK_EMAIL	 					varchar(50) 					not null,
	SHIFTS_SHIFT_ID						bigint							not null,
	primary key(COOK_SHIFTS_ID),
	FOREIGN KEY (COOK_EMAIL) REFERENCES COOK(EMAIL),
	FOREIGN KEY (SHIFTS_SHIFT_ID) REFERENCES SHIFT(SHIFT_ID)
);
/*----------BARTENDER---------------------*/

create table BARTENDER
(
   EMAIL                		varchar(50)                   	not null,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   SURNAME						varchar(50)            			not null,
   DATE_OF_BIRTH				DATE               				not null,
   DRESS_SIZE					int              		    	not null,
   SHOES_SIZE					bigint             		    	not null,  
   RESTAURANT					bigint							not null,
   SHIFTS						bigint							,
   ROLE 						enum('BARTENDER')          			not null,
   ACTIVATED					boolean,
   primary key(EMAIL),
   FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (SHIFTS) REFERENCES SHIFT(SHIFT_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table BARTENDER_SHIFTS
(
	BARTENDER_SHIFTS_ID					bigint							not null AUTO_INCREMENT,
    BARTENDER_EMAIL	 					varchar(50) 					not null,
	SHIFTS_SHIFT_ID						bigint							not null,
	primary key(BARTENDER_SHIFTS_ID),
	FOREIGN KEY (BARTENDER_EMAIL) REFERENCES BARTENDER(EMAIL),
	FOREIGN KEY (SHIFTS_SHIFT_ID) REFERENCES SHIFT(SHIFT_ID)
);

create table WORK_SCHEDULE
(
	WORK_SCHEDULE_ID					bigint							not null,
	primary key(WORK_SCHEDULE_ID)
);
/*----------FOOD STUFF---------------------*/
create table FOOD_STUFF
(
   FOOD_STUFF_ID               	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(100)            		not null,
   QUANTITY								int								not null,
   RESTAURANT							bigint  						not null,
   FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID)
);
/*----------DRINK GROCERY---------------------*/
create table DRINK_GROCERY
(
   DRINK_GROCERY_ID           	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(100)            		not null,
   QUANTITY								int								not null,
   RESTAURANT							bigint  						not null,
   FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID)
);
/*----------FOOD STUFF OFFER---------------------*/
create table FOOD_STUFF_OFFER
(
   FOOD_STUFF_OFFER_ID         	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(100)            		not null,
   QUANTITY								int								not null,
   PRICE     							decimal,
   FOOD_STUFF							bigint
);
/*----------DRINK OFFER---------------------*/
create table DRINK_OFFER
(
   DRINK_OFFER_ID           	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   NAME                					varchar(50)	                   	not null,
   DESCRIPTION                  		varchar(100)            		not null,
   QUANTITY								int								not null,
   PRICE								decimal,
   DRINK_GROCERY						bigint
);

/*----------GROCERY LIST---------------------*/
create table GROCERY_LIST
(
   GROCERY_LIST_ID            	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
   START_DATE         					DATE               				not null,
   END_DATE		                		DATE               				not null,
   DRINKS								bigint							,
   FOOD									bigint							,
   ACCEPTED								boolean,
   RESTAURANT							bigint							not null,
   FOREIGN KEY (DRINKS) REFERENCES DRINK_OFFER(DRINK_OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (FOOD) REFERENCES FOOD_STUFF_OFFER(FOOD_STUFF_OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table GROCERY_LIST_DRINKS
(
    GROCERY_LIST_DRINKS_ID      	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
    DRINKS_DRINK_OFFER_ID					bigint not null,
    GROCERY_LIST_GROCERY_LIST_ID	    	bigint not null,
   	FOREIGN KEY ( DRINKS_DRINK_OFFER_ID) REFERENCES DRINK_OFFER(DRINK_OFFER_ID),
	FOREIGN KEY (GROCERY_LIST_GROCERY_LIST_ID) REFERENCES GROCERY_LIST(GROCERY_LIST_ID)
);

create table GROCERY_LIST_FOOD
(
    GROCERY_LIST_FOOD_ID      	  	    bigint             				PRIMARY KEY AUTO_INCREMENT,
    GROCERY_LIST_GROCERY_LIST_ID						bigint not null,
    FOOD_FOOD_STUFF_OFFER_ID					bigint not null,
   	FOREIGN KEY (FOOD_FOOD_STUFF_OFFER_ID) REFERENCES FOOD_STUFF_OFFER(FOOD_STUFF_OFFER_ID),
	FOREIGN KEY (GROCERY_LIST_GROCERY_LIST_ID) REFERENCES GROCERY_LIST(GROCERY_LIST_ID)
);
create table PRICES
(
   PRICES_ID                		bigint                  	    PRIMARY KEY AUTO_INCREMENT,
   ITEM_ID                			bigint 							not null,
   PRICE                  			bigint 							not null
);
create table BIDDER
(
   EMAIL                		varchar(50)                   	not null  PRIMARY KEY,
   PASSWORD                		varchar(50)	                   	not null,
   NAME                  		varchar(50)            			not null,
   PASSWORD_STATUS				int								not null,
   RESTAURANT 					bigint 							not null,
   ROLE 						enum('BIDDER')      			not null,
   FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE
);
create table OFFER
(
   OFFER_ID                		bigint                	        PRIMARY KEY AUTO_INCREMENT,
   TOTAL_PRICE					bigint							not null,
   DELIVERY_TIME				int								not null,
   GUARANTEE					int								not null,
   DRINKS						bigint							,
   FOOD 						bigint							,
   ACCEPTED						enum('ACCEPTED', 'REJECTED', 'WAITING')  not null,
   GROCERY_LIST					bigint							,
   BIDDER						varchar(50)						not null,
   FOREIGN KEY (DRINKS) REFERENCES  DRINK_OFFER(DRINK_OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (FOOD) REFERENCES FOOD_STUFF_OFFER(FOOD_STUFF_OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (GROCERY_LIST) REFERENCES GROCERY_LIST(GROCERY_LIST_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (BIDDER) REFERENCES BIDDER(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE
);
create table OFFER_BIDDER
(
   OFFER_BIDDER_ID                		bigint                   	    PRIMARY KEY AUTO_INCREMENT,
   BIDDER_EMAIL			    			varchar(50)                      	not null,
   OFFER_OFFER_ID                  		bigint               			not null,
   FOREIGN KEY ( BIDDER_EMAIL  ) REFERENCES BIDDER(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY ( OFFER_OFFER_ID) REFERENCES OFFER(OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE
);
create table OFFER_GROCERY_LIST
(
   OFFER_GROCERY_LIST_ID                		bigint                   	    PRIMARY KEY AUTO_INCREMENT,
   GROCERY_LIST_GROCERY_LIST_ID    			bigint                      	not null,
   OFFER_OFFER_ID                  		bigint               			not null,
   FOREIGN KEY ( GROCERY_LIST_GROCERY_LIST_ID  ) REFERENCES GROCERY_LIST(GROCERY_LIST_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY ( OFFER_OFFER_ID) REFERENCES OFFER(OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

create table OFFER_DRINKS
(
   OFFER_DRINKS_ID                		bigint                   	    PRIMARY KEY AUTO_INCREMENT,
   DRINKS_DRINK_OFFER_ID    			bigint                      	not null,
   OFFER_OFFER_ID                  		bigint               			not null,
   FOREIGN KEY ( DRINKS_DRINK_OFFER_ID ) REFERENCES DRINK_OFFER(DRINK_OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY ( OFFER_OFFER_ID) REFERENCES OFFER(OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE
);
create table OFFER_FOOD
(
   OFFER_FOOD_ID                		bigint                   	    PRIMARY KEY AUTO_INCREMENT,
   FOOD_FOOD_STUFF_OFFER_ID           		bigint                      	not null,
   OFFER_OFFER_ID                  		bigint               			not null,
   FOREIGN KEY (FOOD_FOOD_STUFF_OFFER_ID) REFERENCES FOOD_STUFF_OFFER(FOOD_STUFF_OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   FOREIGN KEY (OFFER_OFFER_ID) REFERENCES OFFER(OFFER_ID) ON DELETE CASCADE ON UPDATE CASCADE
);
create table GRADE
(
	GRADE_ID					bigint							not null AUTO_INCREMENT,
   	GRADE_VALUE           		INT	                   			not null,
   	RESTAURANT					bigint							,
   	FOOD						bigint							,
   	WAITER						varchar(50)						,
   	GUEST						varchar(50)            			not null,
    primary key (GRADE_ID),
   	FOREIGN KEY (GUEST) REFERENCES GUEST(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (RESTAURANT) REFERENCES RESTAURANT(RESTAURANT_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (FOOD) REFERENCES FOOD(FOOD_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (WAITER) REFERENCES WAITER(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into isa2016.guest values ('email','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email3','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email4','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('emailTest','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email3Test1','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email4Test2','pass','name','surname','GUEST',null,null);


create table ORDERED_DRINK
(
	ORDERED_DRINK_ID					bigint													not null AUTO_INCREMENT,
   	QUANTITY							INT	            										not null,
   	DRINK								bigint													not null,
   	FOOD_STATUS							enum('ACCEPTED', 'WAITING', 'PREPARED', 'REJECTED')		not null,
   	BARTENDER							varchar(50)												,
    primary key (ORDERED_DRINK_ID),
   	FOREIGN KEY (DRINK) REFERENCES DRINK(DRINK_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (BARTENDER) REFERENCES BARTENDER(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE


);

create table ORDERED_FOOD
(
	ORDERED_FOOD_ID						bigint													not null AUTO_INCREMENT,
   	QUANTITY							INT	            										not null,
   	FOOD								bigint													not null,
   	FOOD_STATUS							enum('ACCEPTED', 'WAITING', 'PREPARED', 'REJECTED')		not null,
   	COOK								varchar(50)												,
    primary key (ORDERED_FOOD_ID),
   	FOREIGN KEY (FOOD) REFERENCES FOOD(FOOD_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (COOK) REFERENCES COOK(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE

);


create table ORDER_LIST
(
	ORDER_ID						bigint							not null AUTO_INCREMENT,
   	RESTAURANT_TABLE				bigint            				,
   	GUEST							varchar(50)						,
   	DRINKS							bigint							,
   	FOOD							bigint							,
    primary key (ORDER_ID),
   	FOREIGN KEY (RESTAURANT_TABLE) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (GUEST) REFERENCES GUEST(EMAIL) ON DELETE CASCADE ON UPDATE CASCADE,
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
   	ARRIVAL           				DATE 	              			not null,
   	ARRIVAL_TIME					TIME							not null,
   	DURATION						int								not null,
   	TABLES							bigint            				,
   	RESTAURANT						bigint							not null,
   	GUESTS							varchar(50)						,
   	INVITED_FRIENDS					varchar(50)						,
   	ORDERS							bigint							,
    primary key (RESERVATION_ID),
   	FOREIGN KEY (TABLES) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) ON DELETE CASCADE ON UPDATE CASCADE,
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

create table RESERVATION_TABLES
(
	RESERVATION_TABLES_ID					bigint							not null AUTO_INCREMENT,
   	RESERVATION_RESERVATION_ID				bigint							not null,
   	TABLES_RESTAURANT_TABLE_ID				bigint							not null,	
    primary key (RESERVATION_TABLES_ID),
   	FOREIGN KEY (RESERVATION_RESERVATION_ID) REFERENCES RESERVATION(RESERVATION_ID) ON DELETE CASCADE ON UPDATE CASCADE,
   	FOREIGN KEY (TABLES_RESTAURANT_TABLE_ID) REFERENCES RESTAURANT_TABLE(RESTAURANT_TABLE_ID) ON DELETE CASCADE ON UPDATE CASCADE
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


insert into restaurant (RESTAURANT_ID, NAME, DESCRIPTION, ADDRESS) values (1,'Restaurant 1','description1','Trg Slobode 4, Нови Сад');
insert into restaurant  (RESTAURANT_ID, NAME, DESCRIPTION, ADDRESS) values (2,'Restaurant 2','description2','Лазе Телечког 16, Нови Сад');
insert into restaurant  (RESTAURANT_ID, NAME, DESCRIPTION, ADDRESS) values (3,'Restaurant 3','description3','Змај Јовина 28, Нови Сад');

insert into isa2016.waiter (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED) values('waiter@waiter.com','pass','name','surname','1975-03-10',42,44,1,'WAITER',false);
insert into isa2016.waiter (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED)  values ('waiter1@waiter.com','pass1','name1','surname1','1961-10-11',40,42,1,'WAITER',false);
insert into isa2016.waiter (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED)  values ('waiter2@waiter.com','pass2','name2','surname2','1984-05-06',46,43,2,'WAITER',false);

insert into isa2016.bartender (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED) values('bartender@bartender.com','pass','name','surname','1975-03-10',42,44,1,'BARTENDER',false);
insert into isa2016.bartender (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED)  values ('bartender1@bartender.com','pass1','name1','surname1','1961-10-11',40,42,1,'BARTENDER',false);
insert into isa2016.bartender (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED)  values ('bartender2@bartender.com','pass2','name2','surname2','1984-05-06',46,43,2,'BARTENDER',false);

insert into isa2016.cook (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED) values('cook@cook.com','pass','name','surname','1975-03-10',38,44,1,'COOK',false);
insert into isa2016.cook (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED)  values ('cook1@cook.com','pass1','name1','surname1','1961-10-11',45,42,1,'COOK',false);
insert into isa2016.cook (EMAIL,PASSWORD,NAME,SURNAME,DATE_OF_BIRTH,DRESS_SIZE,SHOES_SIZE,RESTAURANT,ROLE, ACTIVATED)  values ('cook2@cook.com','pass2','name2','surname2','1984-05-06',40,43,2,'COOK',false);

insert into isa2016.bidder (EMAIL,PASSWORD,NAME,PASSWORD_STATUS, RESTAURANT, ROLE)  values ('bidder@bidder.com','pass2','name2',1,1,'BIDDER');
insert into isa2016.bidder (EMAIL,PASSWORD,NAME,PASSWORD_STATUS, RESTAURANT, ROLE)  values ('b','b','name',1,2,'BIDDER');

INSERT into  WAITER_SHIFTS values(1,'waiter@waiter.com',1);
INSERT into  BARTENDER_SHIFTS values(1,'bartender@bartender.com',2);
INSERT into  BARTENDER_SHIFTS values(2,'bartender@bartender.com',3);
INSERT into  COOK_SHIFTS values(1,'cook@cook.com',4);
INSERT into  COOK_SHIFTS values(2,'cook@cook.com',5);

insert into restaurant_manager (EMAIL, PASSWORD, NAME, SURNAME, ROLE, RESTAURANT_ID) values ('a','a','name','surname','RESTAURANT_MANAGER',1);

insert into drink values(1,'drink1','description drink1',89.99);
insert into food values (1,'food1','description food 1',300);
insert into drink values(2,'drink2','description drink2',76);
insert into food values (2,'food2','description food 2',43.2);
insert into drink values(3,'drink3','description drink3',86);
insert into food values (3,'food3','description food 3',165.2);


insert into food_stuff(FOOD_STUFF_ID, NAME, DESCRIPTION, QUANTITY, RESTAURANT) values(1,'food_stuff_1','description',3,1);
insert into food_stuff(FOOD_STUFF_ID, NAME, DESCRIPTION, QUANTITY, RESTAURANT) values (2,'food_stuff_2','description',4,1);
insert into drink_grocery(DRINK_GROCERY_ID, NAME, DESCRIPTION, QUANTITY, RESTAURANT) values(1,'drink_grocery_1','description',3,1);
insert into drink_grocery(DRINK_GROCERY_ID, NAME, DESCRIPTION, QUANTITY, RESTAURANT) values (2,'drink_grocery_2','description',15,1);

insert into food_stuff_offer(FOOD_STUFF_OFFER_ID, NAME, DESCRIPTION, QUANTITY, FOOD_STUFF) values(1,'food_stuff_1','description',5,1);
insert into food_stuff_offer(FOOD_STUFF_OFFER_ID, NAME, DESCRIPTION, QUANTITY, FOOD_STUFF) values (2,'food_stuff_2','description',3,2);
insert into drink_offer(DRINK_OFFER_ID, NAME, DESCRIPTION, QUANTITY, DRINK_GROCERY) values(1,'drink_grocery_1','description',2,1);
insert into drink_offer(DRINK_OFFER_ID, NAME, DESCRIPTION, QUANTITY, DRINK_GROCERY) values (2,'drink_grocery_2','description',4,2);

insert into grocery_list( GROCERY_LIST_ID, START_DATE, END_DATE,ACCEPTED, RESTAURANT) values (1,'2017-02-10','2017-03-13',false,1);
insert into grocery_list_food( GROCERY_LIST_FOOD_ID, GROCERY_LIST_GROCERY_LIST_ID, FOOD_FOOD_STUFF_OFFER_ID) values (1,1,1);
insert into grocery_list_drinks( GROCERY_LIST_DRINKS_ID, GROCERY_LIST_GROCERY_LIST_ID, DRINKS_DRINK_OFFER_ID) values (1,1,1);

insert into offer( OFFER_ID, TOTAL_PRICE, DELIVERY_TIME, GUARANTEE, ACCEPTED, GROCERY_LIST, BIDDER) 
values (1,1000,2,1,'REJECTED',1, 'b');

insert into RESTAURANT_DRINKS values(1,1,1);
insert into RESTAURANT_MENU values(1,1,1);
insert into RESTAURANT_DRINKS values(1,1,2);
insert into RESTAURANT_MENU values(1,1,2);
insert into RESTAURANT_DRINKS values(1,1,3);
insert into RESTAURANT_MENU values(1,1,3);

insert into grade(GRADE_ID, GRADE_VALUE, RESTAURANT, GUEST) values (1,3,1,'email');
insert into grade(GRADE_ID, GRADE_VALUE, RESTAURANT, GUEST) values (2,4,1,'email3');
insert into grade(GRADE_ID, GRADE_VALUE, RESTAURANT, GUEST) values (3,4,1,'email4');

insert into grade(GRADE_ID, GRADE_VALUE, WAITER, GUEST) values (4,3,'waiter@waiter.com','email');
insert into grade(GRADE_ID, GRADE_VALUE, WAITER, GUEST) values (5,4,'waiter@waiter.com','email3');
insert into grade(GRADE_ID, GRADE_VALUE, WAITER, GUEST) values (10,4,'waiter1@waiter.com','email3');
insert into grade(GRADE_ID, GRADE_VALUE, WAITER, GUEST) values (6,4,'waiter2@waiter.com','email4');

insert into grade(GRADE_ID, GRADE_VALUE, FOOD, GUEST) values (7,3,1,'email');
insert into grade(GRADE_ID, GRADE_VALUE, FOOD, GUEST) values (8,4,2,'email3');
insert into grade(GRADE_ID, GRADE_VALUE, FOOD, GUEST) values (9,5,3,'email4');

insert into restaurant_table values (1,1,'SMOKEINSIDE', 'FREE',2);    
insert into restaurant_table values (2,2,'SMOKEINSIDE', 'FREE',3); 
insert into restaurant_table values (3,3,'NOSMOKEINSIDE', 'FREE',4); 
insert into restaurant_table values (4,4,'NOSMOKEINSIDE', 'FREE',2); 
insert into restaurant_table values (5,5,'SMOKEGARDEN', 'FREE',2); 
insert into restaurant_table values (6,6,'SMOKEGARDEN', 'FREE',3); 
insert into restaurant_table values (7,7,'NOSMOKEGARDEN', 'FREE',5); 
insert into restaurant_table values (8,8,'NOSMOKEGARDEN', 'RESERVED',6); 
insert into grade(GRADE_ID, GRADE_VALUE, RESTAURANT, GUEST) values (11,3,1,'nevBon93@gmail.com');

insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(1,1);
insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(1,2);
insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(2,3);
insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(2,4);
insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(2,5);
insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(3,6);
insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(3,7);
insert into RESTAURANT_TABLES(RESTAURANT_RESTAURANT_ID, TABLES_RESTAURANT_TABLE_ID) values(3,8);

insert into shift_reon(SHIFT_REON_ID,SHIFT_SHIFT_ID,REON_RESTAURANT_TABLE_ID)values(1,1,1);


insert into reservation(RESERVATION_ID,ARRIVAL,ARRIVAL_TIME,DURATION,RESTAURANT) values(1, STR_TO_DATE('2016-3-10', '%Y-%m-%d'),'8:00',5,1);
insert into reservation(RESERVATION_ID,ARRIVAL,ARRIVAL_TIME,DURATION,RESTAURANT) values(2, STR_TO_DATE('2016-3-15', '%Y-%m-%d'),'8:00',5,1);
insert into reservation(RESERVATION_ID,ARRIVAL,ARRIVAL_TIME,DURATION,RESTAURANT) values(3, STR_TO_DATE('2016-3-17', '%Y-%m-%d'),'8:00',5,1);
insert into reservation(RESERVATION_ID,ARRIVAL,ARRIVAL_TIME,DURATION,RESTAURANT) values(4, STR_TO_DATE('2016-4-06', '%Y-%m-%d'),'8:00',5,1);
insert into reservation(RESERVATION_ID,ARRIVAL,ARRIVAL_TIME,DURATION,RESTAURANT) values(5, STR_TO_DATE('2016-4-21', '%Y-%m-%d'),'8:00',5,1);


insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(1,1,'email');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(2,1,'email3');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(3,1,'email4');

insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(4,2,'email');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(5,2,'email3');

insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(6,3,'email');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(7,3,'email3');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(8,3,'email4');

insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(9,4,'email');

insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(10,5,'email');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(11,5,'email3');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(12,3,'emailTest');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(13,3,'email3Test1');
insert into reservation_guests(RESERVATION_GUESTS_ID, RESERVATION_RESERVATION_ID,GUESTS_EMAIL)values(14,1,'email4Test2');
