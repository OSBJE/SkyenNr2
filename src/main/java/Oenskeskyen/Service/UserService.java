package Oenskeskyen.Service;

import Oenskeskyen.Model.User;
import Oenskeskyen.Repository.OenskeSkyenRepository;
import Oenskeskyen.Repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository repository){
        this.userRepository = repository;
    }




    /// **************************** Add and modify database functions ******************** ///
    public User getUser(String FullName){
        return userRepository.getUser(FullName);
    }

    public void saveUserCostumer(User obj){
        userRepository.saveNewUser(obj.getFullName(), obj.getMail(), obj.getPassWord());
    }


}
