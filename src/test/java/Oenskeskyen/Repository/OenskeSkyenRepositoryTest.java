package Oenskeskyen.Repository;


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

    }
}
