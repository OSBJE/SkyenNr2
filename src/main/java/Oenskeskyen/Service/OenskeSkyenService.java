package Oenskeskyen.Service;

import Oenskeskyen.Model.User;
import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
import Oenskeskyen.Repository.InterfaceOenskeSkyenRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OenskeSkyenService {

    private InterfaceOenskeSkyenRepository oenskeSkyenRepository;

    public OenskeSkyenService(ApplicationContext context, @Value("${oenskeskyen.repository.impl}") String impl){
        oenskeSkyenRepository = (InterfaceOenskeSkyenRepository) context.getBean(impl);
    }

    /// **************************** Add and modify database functions ******************** ///

    public void saveWishList(WishList obj, int userID){
        oenskeSkyenRepository.saveNewWishList(obj.getName(), obj.getDescription(), userID);
    }

    public void saveWish(Wish obj, int wishListID){
        oenskeSkyenRepository.saveWish(obj.getName(), obj.getPrice(), obj.getUrlLink(), wishListID);
    }

    public Wish getWishByWishListID(int wishListID, int wishId){
        Integer wishGotten = oenskeSkyenRepository.getWishIdFromWishlist(wishListID, wishId);
        if (wishGotten != null){
            return oenskeSkyenRepository.getWishById(wishGotten);
        } else {
            return null;
        }
    }

    public List<WishList> getAllWishLists(int userID){
        return oenskeSkyenRepository.getAllWishLists(userID);
    }

    public WishList getWishListById(int wishListID){
        return oenskeSkyenRepository.getWishListById(wishListID);
    }

    public List<Wish> getAllWishes(int wishListID){
        return oenskeSkyenRepository.getAllWishes(wishListID);
    }

    public void deleteWishList(int wishListId){
        oenskeSkyenRepository.deleteWishListFromData(wishListId);
    }

    public void editWish(int wishId, Wish updatedWish) {
        oenskeSkyenRepository.editWish(wishId, updatedWish);
    }

    public void deleteWish(int wishId){
        oenskeSkyenRepository.deleteWish(wishId);
    }


}
