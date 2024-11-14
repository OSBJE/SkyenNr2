package Oenskeskyen.Repository;

import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
import java.util.List;

public interface InterfaceOenskeSkyenRepository {


    void setConn();

    void saveNewWishList(String wishListName, String description, int userID);

    void saveWish(String name, double price, String urlLink, int wishListID);

    List<WishList> getAllWishLists(int userID);

    WishList getWishListById(int wishListID);

    List<Wish> getAllWishes(int wishListId);

    void deleteWishListFromData(int wishListID);

    Wish getWishById(int wishId);

    Integer getWishIdFromWishlist(int wishListId, int wishId);

    void editWish(int wishId, Wish wish);

    void deleteWish(int wishId);

}

