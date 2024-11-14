package Oenskeskyen.Controller;

import Oenskeskyen.Model.User;
import Oenskeskyen.Service.UserService;
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


@WebMvcTest(SessionController.class)
class SessionControllerTest {

    private User user = new User();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginValidation() throws Exception {
        when(userService.getUser("lucasmodj@gmail.com")).thenReturn(user);
        user.setMail("lucasmodj@gmail.com");
        user.setPassWord("GoodPassword1234");
        mockMvc.perform(post("/loginValidation")
                        .param("mail", user.getMail())
                        .param("password", user.getPassWord())
                        .sessionAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer-page"));

    }

}