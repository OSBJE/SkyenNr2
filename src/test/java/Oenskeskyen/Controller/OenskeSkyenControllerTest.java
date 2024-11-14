package Oenskeskyen.Controller;

import static org.junit.jupiter.api.Assertions.*;

import Oenskeskyen.Model.User;
import Oenskeskyen.Model.WishList;
import Oenskeskyen.Model.Wish;
import Oenskeskyen.Service.OenskeSkyenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OenskeSkyenController.class)
class OenskeSkyenControllerTest {

    private WishList wishList = new WishList();
    private User user = new User();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OenskeSkyenService service;

    @Test
    void testCreateNewWishList() throws Exception {
        mockMvc.perform(get("/wishlists"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("obj"))
                .andExpect(view().name("addWishList"));

    }

    @Test
    void testSaveWishList() throws Exception {
        mockMvc.perform(post("/saveWishList").sessionAttr("wishListObj", this.wishList).sessionAttr("user", this.user))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer-page"))
                .andExpect(redirectedUrl("/customer-page"));
    }

    @Test
    void testSaveWishListUserNotFound() throws Exception {
        mockMvc.perform(post("/saveWishList").sessionAttr("wishListObj", this.wishList))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testCreateNewWish() throws Exception {
        int wishListID = 1;
        mockMvc.perform(get("/addWish/{wishListID}", wishListID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("obj"))
                .andExpect(model().attribute("wishListID", wishListID))
                .andExpect(view().name("addWish"));
    }

    @Test
    void testSaveWish() throws Exception {
        Wish wish = new Wish("new wish", 100.0, "www.example.com");
        int wishListID = 1;

        mockMvc.perform(post("/saveWish")
                        .flashAttr("wishObj", wish) //flashAttri is used to simulate data being passed through the thymeleaf form submission
                                                            // (form submissions with the @ModelAttribute in our controller)
                        .param("wishListID", String.valueOf(wishListID)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/viewWishList/" + wishListID))
                .andExpect(redirectedUrl("/viewWishList/" + wishListID));
    }

    @Test
    void testDeleteWishList() throws Exception {
        int wishListId = 1;

        mockMvc.perform(post("/deleteWishList/{wishListId}", wishListId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer-page"))
                .andExpect(redirectedUrl("/customer-page"));
    }

    @Test
    void testViewWishList() throws Exception {
        int wishListID = 1;
        WishList wishList = new WishList("birthday", "gifts");
        when(service.getWishListById(wishListID)).thenReturn(wishList);
        when(service.getAllWishes(wishListID)).thenReturn(List.of(new Wish("toy", 20.0, "www.toy.com")));

        mockMvc.perform(get("/viewWishList/{wishListID}", wishListID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("wishlist", wishList))
                .andExpect(model().attributeExists("getAllWishes"))
                .andExpect(view().name("viewWishList"));
    }

    @Test
    void testViewWishListNotFound() throws Exception {
        int wishListID = 1;
        when(service.getWishListById(wishListID)).thenReturn(null);

        mockMvc.perform(get("/viewWishList/{wishListID}", wishListID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer-page"))
                .andExpect(redirectedUrl("/customer-page"));
    }

    @Test
    void testEditWish() throws Exception {
        int wishListId = 1;
        int wishId = 2;
        Wish wish = new Wish("updated wish", 50.0, "www.updated.com");
        when(service.getWishByWishListID(wishListId, wishId)).thenReturn(wish);

        mockMvc.perform(get("/edit-wish/{wishListId}/{wishId}", wishListId, wishId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("wish", wish))
                .andExpect(view().name("editWish"));
    }

    @Test
    void testEditWishNotFound() throws Exception {
        int wishListId = 1;
        int wishId = 2;
        when(service.getWishByWishListID(wishListId, wishId)).thenReturn(null);

        mockMvc.perform(get("/edit-wish/{wishListId}/{wishId}", wishListId, wishId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer-page"))
                .andExpect(redirectedUrl("/customer-page"));
    }

    @Test
    void testDeleteWish() throws Exception {
        int wishId = 2;
        int wishListId = 1;

        mockMvc.perform(post("/delete-wish/{wishListId}/{wishId}", wishListId, wishId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/viewWishList/" + wishListId))
                .andExpect(redirectedUrl("/viewWishList/" + wishListId));
    }

    @Test
    void testUpdateWish() throws Exception {
        int wishId = 2;
        int wishListId = 1;
        String name = "updated wish";
        double price = 150.0;
        String urlLink = "www.updated.com";

        mockMvc.perform(post("/update-wish")
                        .param("wishId", String.valueOf(wishId))
                        .param("name", name)
                        .param("price", String.valueOf(price))
                        .param("urlLink", urlLink)
                        .param("wishListId", String.valueOf(wishListId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/viewWishList/" + wishListId))
                .andExpect(redirectedUrl("/viewWishList/" + wishListId));
    }

    @Test
    void testCustomerPageWithUser() throws Exception {
        user.setUserId(1);
        user.setFullName("test user");
        user.setMail("lol@gmail.com");
        user.setPassWord("password");
        when(service.getAllWishLists(user.getId())).thenReturn(List.of(new WishList("list 1", "description")));

        mockMvc.perform(get("/customer-page").sessionAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("getAllWishLists"))
                .andExpect(view().name("customer-page"));
    }

    @Test
    void testCustomerPageWithoutUser() throws Exception {
        mockMvc.perform(get("/customer-page"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andExpect(redirectedUrl("/login"));
    }

 }