package Oenskeskyen.Service;

import Oenskeskyen.Model.User;
import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
import Oenskeskyen.Repository.OenskeSkyenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OenskeSkyenService {

    private OenskeSkyenRepository oenskeSkyenRepository;

    public OenskeSkyenService(OenskeSkyenRepository repository){
        this.oenskeSkyenRepository = repository;
    }

    /// **************************** Add and modify database functions ******************** ///

    public void saveWishList(WishList obj, int userID){
        oenskeSkyenRepository.saveNewWishList(obj.getName(), obj.getDescription(), userID);
    }

    public void saveWish(Wish obj, int wishListID){
        oenskeSkyenRepository.saveWish(obj.getName(), obj.getPrice(), obj.getUrlLink(), wishListID);
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


}
