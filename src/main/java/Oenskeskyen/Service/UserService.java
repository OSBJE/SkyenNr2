package Oenskeskyen.Service;

import Oenskeskyen.Model.User;
import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
import Oenskeskyen.Repository.OenskeSkyenRepository;
import Oenskeskyen.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository repository){
        this.userRepository = repository;
    }




    /// **************************** Add and modify database functions ******************** ///
    public User getUser(String mail){
        return userRepository.getUser(mail);
    }

    public void saveUserCostumer(User obj){
        userRepository.saveNewUser(obj.getFullName(), obj.getMail(), obj.getPassWord());
    }

    public void saveWishList(WishList obj, int userID){
        userRepository.saveNewWishList(obj.getName(), obj.getDescription(), userID);
    }

    public List<WishList> getAllWishLists(int userID){
        return userRepository.getAllWishLists(userID);
    }

    public WishList getWishById(int wishListID){
        return userRepository.getWishById(wishListID);
    }
}
