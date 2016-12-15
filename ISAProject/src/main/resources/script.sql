
drop table if exists GUEST_FRIEND_REQUESTS;
drop table if exists GUEST_REQUESTS_FOR_FRIENDS;
drop table if exists GUEST_FRIENDS;
drop table if exists GUEST;
drop table if exists USER;


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

insert into isa2016.guest values ('email','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email3','pass','name','surname','GUEST',null,null);
insert into isa2016.guest values ('email4','pass','name','surname','GUEST',null,null);


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

        
      
      
      
      
      
      
      
      
      
      
      
      


