package Oenskeskyen.Repository;

import Oenskeskyen.Model.DBConnection;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

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



}
