package Oenskeskyen.Repository;


import Oenskeskyen.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("h2")
public class OenskeSkyenRepositoryTest {

    @Autowired
    OenskeSkyenRepository repository;

    @Test
    void testSaveUsercustomer(){
        String Fullname = "Kea test";
        String mail = "Keatestmail";
        String password = "Kea1234";

        repository.saveNewUser(Fullname,mail,password);

        repository.getUser(Fullname);

    }
}
