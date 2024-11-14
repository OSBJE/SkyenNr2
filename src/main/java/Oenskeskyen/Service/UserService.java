package Oenskeskyen.Service;

import Oenskeskyen.Model.User;

import Oenskeskyen.Repository.InterfaceUserRepository;

import Oenskeskyen.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;




@Service
public class UserService {

    private InterfaceUserRepository userRepository;

    public UserService(ApplicationContext context, @Value("${userprofile.repository.impl}") String impl){
        userRepository = (InterfaceUserRepository) context.getBean(impl);
    }


    /// **************************** Add and modify database functions ******************** ///
    public User getUser(String mail){
        return userRepository.getUser(mail);
    }

    public void saveUserCostumer(User obj){
        userRepository.saveNewUser(obj.getFullName(), obj.getMail(), obj.getPassWord());

    }

    public void deleteUser(int id){
        userRepository.deleteUserFromData(id);
    }




}
