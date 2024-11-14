package Oenskeskyen.Repository;

import Oenskeskyen.Model.User;



public interface InterfaceUserRepository {

    void setConn();

    /// **************************** Add and modify database functions ******************** ///

    void saveNewUser(String fullname, String mail, String userPassword);


    void deleteUserFromData(int id);


    User getUser(String mail);

}
