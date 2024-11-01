create database if not exists OenskeSkyDB;
USE OenskeSkyDB;

drop table if exists UserCustomer;
create table UserCustomer(
	UserID int auto_increment primary key,
    FullName varchar(100) not null,
    Mail varchar(100) not null,
    UserPassWord varchar(100) not null
);

drop table if exists WishList;
create table WishList(
	WishListID int auto_increment primary key,
    WishListName varchar(100) not null
    );
    

drop table if exists Wish;
create table Wish(
WishID int auto_increment primary key, 
WishName varchar(100) not null, 
WishPrice Double not null,
WishLink varchar(100) not null
);

drop table if exists User_WishList;
create table User_WishList(
	UserID int, 
    WishListID int,
    FOREIGN KEY (UserID) REFERENCES UserCustomer(UserID) ON DELETE CASCADE,
    FOREIGN KEY (WishListID) REFERENCES WishList(WishListID) ON DELETE CASCADE
    );
    
drop table if exists WishList_Wishes;
create table Wishlist_Wishes(
	WishListID int,
    WishID int,
    FOREIGN KEY (WishListID) REFERENCES WishList(WishListID) ON DELETE CASCADE,
    FOREIGN KEY (WishID) REFERENCES Wish(WishID) ON DELETE CASCADE
    );



 


