package Oenskeskyen.Repository;

import Oenskeskyen.Model.DBConnection;
import Oenskeskyen.Model.User;
import Oenskeskyen.Model.WishList;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserRepository {


    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    private Connection conn;



    /// ********************************* Constructor and set-up ************************** ///
    public UserRepository(){
    }

    //PostConstruct runes functions after we have generated the constructor
    //remove problem with beans.

    @PostConstruct
    public void setConn() {
        this.conn = DBConnection.getConnection(dbUrl,dbUsername,dbPassword);
    }

    /// **************************** Add and modify database functions ******************** ///

    public void saveNewUser(String fullname, String mail, String userPassword){

        try{
            String sqlString ="insert into usercustomer (FullName, Mail, UserPassWord) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlString);
            stmt.setString(1,fullname);
            stmt.setString(2,mail);
            stmt.setString(3,userPassword);
            stmt.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //to create a new wishlist and assign it to a users userid. It is not visual
    public void saveNewWishList(String wishListName, String description, int userID){
        try{
            String sqlString = "insert into WishList (WishListName, WishListDescription, UserID) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlString);
            stmt.setString(1, wishListName);
            stmt.setString(2, description);
            stmt.setInt(3, userID);
            stmt.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<WishList> getAllWishLists(int userID){
        List<WishList> usersWishLists = new ArrayList<>();
        String sql = "SELECT WishListID, WishListName, WishListDescription FROM WishList WHERE userid = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);

                WishList obj = new WishList();
                obj.setWishListId(id);
                obj.setName(name);
                obj.setDescription(description);

                usersWishLists.add(obj);
            }


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return usersWishLists;
    }

    public WishList getWishById(int wishListID) {
        String sql = "SELECT WishListName, WishListDescription, WishListID FROM WishList WHERE WishListID = ?";
        WishList wishList = null;
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishListID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                wishList = new WishList();
                wishList.setName(resultSet.getString("WishListName"));
                wishList.setDescription(resultSet.getString("WishListDescription"));
                wishList.setWishListId(resultSet.getInt("WishListID"));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return wishList;
    }

    public User getUser(String mail){
        User obj = null;

        try{
            String sqlString ="SELECT UserID, FullName, Mail, UserPassWord FROM usercustomer WHERE Mail = ?";

            PreparedStatement stmt = conn.prepareStatement(sqlString);
            stmt.setString(1,mail);

            ResultSet resultSet = stmt.executeQuery();


            while(resultSet.next()) {
                int userID = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String userMail = resultSet.getString(3);
                String userPassword = resultSet.getString(4);


                if (obj == null){
                    obj = new User(userID,userName,userMail,userPassword);
                }

            }

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;

    }

}
