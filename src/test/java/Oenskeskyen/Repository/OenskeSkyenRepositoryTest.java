package Oenskeskyen.Repository;


import Oenskeskyen.Model.User;
import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("h2")
public class OenskeSkyenRepositoryTest {

    @Autowired
    OenskeSkyenRepository oenskeSkyenRepository;


    @Test
    @DirtiesContext
    void saveNewWishListTest(){
    WishList expectedWishList = new WishList("bday1", "gifts");

    oenskeSkyenRepository.saveNewWishList(expectedWishList.getName(), expectedWishList.getDescription(), 2);

    WishList actualWishList = oenskeSkyenRepository.getWishListById(3);

    assertEquals(expectedWishList.getName(), actualWishList.getName());
    assertEquals(expectedWishList.getDescription(), actualWishList.getDescription());

    }

    @Test
    @DirtiesContext
    void getWishListByIdTest(){
        WishList wishList = new WishList("bob", "1");
        wishList.setWishListId(3);

        oenskeSkyenRepository.saveNewWishList(wishList.getName(), wishList.getDescription(), 2);
        WishList actual = oenskeSkyenRepository.getWishListById(3);

        int expectedValue = actual.getWishListId();
        String expectedName = actual.getName();

        assertEquals(wishList.getWishListId(), expectedValue);
        assertEquals(wishList.getName(), expectedName);
    }

    @Test
    @DirtiesContext
    void saveWishTest(){
        Wish wish = new Wish("ball", 10, "www.bob.dk");
        WishList wishList = oenskeSkyenRepository.getWishListById(1);
        oenskeSkyenRepository.saveWish(wish.getName(), wish.getPrice(), wish.getUrlLink(), wishList.getWishListId());
        Wish actual = oenskeSkyenRepository.getWishById(4);

        String expectedName = "ball";
        double expectedPrice = 10;
        String expectedURL = "www.bob.dk";

        assertEquals(actual.getName(), expectedName);
        assertEquals(actual.getPrice(), expectedPrice);
        assertEquals(actual.getUrlLink(), expectedURL);
        assertEquals(actual.getWishId(), 4);
    }

    @Test
    @DirtiesContext
    void getAllWishListsTest(){
        //Arrange
        List<WishList> expectedList = new ArrayList<>();

        expectedList.add(new WishList("Oskar List", "cake"));


        //Act
        List<WishList> actual = oenskeSkyenRepository.getAllWishLists(1);


        //Assert
        assertEquals(expectedList.get(0).getName(), actual.get(0).getName());
    }
}
