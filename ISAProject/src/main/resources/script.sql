
drop table if exists GUEST_FRIENDS;
drop table if exists GUEST;
drop table if exists USER;

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



