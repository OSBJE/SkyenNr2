package Oenskeskyen.Repository;

import Oenskeskyen.Model.DBConnection;
import Oenskeskyen.Model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class OenskeSkyenRepository {


    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    private Connection conn;



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

    public User getUser(String UserFullName){
        User obj = null;

        try{
            String sqlString ="SELECT UserID, FullName, Mail, UserPassWord FROM usercustomer where FullName = ?";

            PreparedStatement stmt = conn.prepareStatement(sqlString);
            stmt.setString(1,UserFullName);

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
