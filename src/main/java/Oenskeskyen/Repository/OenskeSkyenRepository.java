package Oenskeskyen.Repository;

import Oenskeskyen.Model.DBConnection;
import Oenskeskyen.Model.User;
import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void saveWish(String name, double price, String urlLink, int wishListID){
        try{
            String sqlString = "INSERT INTO Wish (WishName, WishPrice, WishLink) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setString(3, urlLink);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int wishID = -1;
            if(generatedKeys.next()){
                wishID = generatedKeys.getInt(1);
            }

            if(wishID != -1){
                String sqlLinking = "INSERT INTO Wishlist_Wishes (WishListID, WishID) VALUES (?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(sqlLinking);
                stmt2.setInt(1, wishListID);
                stmt2.setInt(2, wishID);
                stmt2.executeUpdate();
            }
        }catch(SQLException e){
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

    public WishList getWishListById(int wishListID) {
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

    public List<Wish> getAllWishes(int wishListId){
        List<Wish> listOfWishes = new ArrayList<>();
        String sql1 = "SELECT WishID FROM Wishlist_Wishes WHERE WishListID = ?";

        try{
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setInt(1, wishListId);
            ResultSet resultSet1 = stmt1.executeQuery();

            String sql2 = "SELECT WishID, WishName, WishPrice, WishLink FROM Wish WHERE WishID = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);

            while(resultSet1.next()){
                stmt2.setInt(1, resultSet1.getInt(1));
                ResultSet resultSet2 = stmt2.executeQuery();
                while(resultSet2.next()){
                    int wishid = resultSet2.getInt(1);
                    String wishName = resultSet2.getString(2);
                    double wishPrice = resultSet2.getDouble(3);
                    String wishLink = resultSet2.getString(4);
                    Wish wish = new Wish(wishName, wishPrice, wishLink, wishid);
                    listOfWishes.add(wish);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return listOfWishes;
    }

    public void deleteWishListFromData(int wishListID){
        String sqlString = "DELETE FROM WishList WHERE WishListID = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sqlString);
            stmt.setInt(1, wishListID);
            stmt.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    //Helper method for getWishByWishListId
    public Wish getWishById(int wishId) {
        String sql = "SELECT WishID, WishName, WishPrice, WishLink FROM Wish WHERE WishID = ?";
        Wish wish = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("WishID");
                String name = resultSet.getString("WishName");
                double price = resultSet.getDouble("WishPrice");
                String link = resultSet.getString("WishLink");
                wish = new Wish(name, price, link, id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching wish by ID: " + e.getMessage(), e);
        }
        return wish;
    }


    public Integer getWishIdFromWishlist(int wishListId, int wishId) {
        String sql = "SELECT WishID FROM Wishlist_Wishes WHERE WishListID = ? AND WishID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishListId);
            stmt.setInt(2, wishId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("WishID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching WishID from Wishlist_Wishes: " + e.getMessage(), e);
        }
        return null;
    }

    public void editWish(int wishId, Wish wish) {
        String sql = "UPDATE Wish SET WishName = ?, WishPrice = ?, WishLink = ? WHERE WishID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, wish.getName());
            stmt.setDouble(2, wish.getPrice());
            stmt.setString(3, wish.getUrlLink());
            stmt.setInt(4, wishId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating wish: " + e.getMessage(), e);
        }
    }

    public void deleteWish(int wishId) {
        String sql = "DELETE FROM WISH WHERE WishID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, wishId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting wish: " + e.getMessage(), e);
        }
    }

}
