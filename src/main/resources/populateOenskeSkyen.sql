use oenskeskydb;

insert into usercustomer 
(Fullname, Mail, UserPassWord) VALUES ('Oskar August', 'Oskar@Kea.com', 'Oskar1234');
insert into usercustomer 
(Fullname, Mail, UserPassWord) VALUES ('Rasmus Carlsen', 'Rasmus@Kea.com', 'Rasmus1234');
insert into usercustomer 
(Fullname, Mail, UserPassWord) VALUES ('Lucas Modin', 'Lucas@Kea.com', 'Lucas1234');

insert into WishList (WishListName, UserID) VALUES ('Lucas List', 3) ,('Oskar List', 1);

insert into Wish (WishName, WishPrice, WishLink) VALUES ('Sko',99.99,'URLLink1');  
insert into Wish (WishName, WishPrice, WishLink) VALUES ('bil',10000.01,'URLLinkBil');
insert into Wish (WishName, WishPrice, WishLink) VALUES ('GameBoy',9.99,'URLLinkGameBoy');    

insert into User_WishList (UserID, WishListID) values (1,2), (3,1);

insert into WishList_Wishes (WishListID, WishID) values (2,3), (2,1), (1,2);
