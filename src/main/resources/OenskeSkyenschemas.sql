create database if not exists OenskeSkyDB;
USE OenskeSkyDB;

drop table if exists WishList_Wishes;
drop table if exists User_WishList;
drop table if exists WishList;
drop table if exists Wish;
drop table if exists usercustomer;


create table usercustomer(
	UserID int auto_increment primary key,
    FullName varchar(100) not null,
    Mail varchar(100) not null,
    UserPassWord varchar(100) not null
);
  
create table WishList(
	WishListID int auto_increment primary key,
    WishListName varchar(100) not null,
    WishListDescription varchar(100) NOT NULL,
    UserID INT,
    constraint fk_UserId FOREIGN KEY (UserID) REFERENCES UserCustomer(UserID) ON DELETE CASCADE
    );

create table Wish(
WishID int auto_increment primary key, 
WishName varchar(100) not null, 
WishPrice Double not null,
WishLink varchar(100) not null
);

create table User_WishList(
	UserID int, 
    WishListID int,
    FOREIGN KEY (UserID) REFERENCES UserCustomer(UserID) ON DELETE CASCADE,
    FOREIGN KEY (WishListID) REFERENCES WishList(WishListID) ON DELETE CASCADE
    );
    
create table Wishlist_Wishes(
	WishListID int,
    WishID int,
    FOREIGN KEY (WishListID) REFERENCES WishList(WishListID) ON DELETE CASCADE,
    FOREIGN KEY (WishID) REFERENCES Wish(WishID) ON DELETE CASCADE
    );