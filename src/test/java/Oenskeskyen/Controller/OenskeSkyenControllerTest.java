package Oenskeskyen.Controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;

@WebMvcTest(OenskeSkyenController.class)
class OenskeSkyenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OenskeSkyenController controller;



}