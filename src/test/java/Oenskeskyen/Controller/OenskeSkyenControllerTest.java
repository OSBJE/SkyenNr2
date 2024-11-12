package Oenskeskyen.Controller;

import static org.junit.jupiter.api.Assertions.*;

import Oenskeskyen.Model.User;
import Oenskeskyen.Model.WishList;
import Oenskeskyen.Service.OenskeSkyenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
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

 }