package Oenskeskyen.Repository;

import Oenskeskyen.Model.DBConnection;
import Oenskeskyen.Model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class OenskeSkyenRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    private Connection conn;
    private PreparedStatement stmt;

    /// ********************************* Constructor and set-up ************************** ///
    public OenskeSkyenRepository(){
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
            String sqlString ="insert into usercustomer (FullName, Mail, UserPassWord) VALUES (?, ?,?)";

            PreparedStatement stmt = conn.prepareStatement(sqlString);
            stmt.setString(1,fullname);
            stmt.setString(2,mail);
            stmt.setString(3,userPassword);
            stmt.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public User getUser(){
        User userObj = null;

        try{
            String sqlString ="SELECT a.Fullname as name a.Mail as mail, c.UserPassWord as password FROM usercustomer a";

            PreparedStatement prep = conn.prepareStatement(sqlString);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return
    }

}
