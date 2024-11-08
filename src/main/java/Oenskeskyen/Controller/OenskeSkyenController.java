package Oenskeskyen.Controller;

import Oenskeskyen.Model.User;
import Oenskeskyen.Service.OenskeSkyenService;
import Oenskeskyen.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class OenskeSkyenController {


    private final UserService userService;


    public OenskeSkyenController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sessionId")
    public String getSessionId(Model model, HttpSession session){
        session.setAttribute("user","getmail");
        String sessionId = (String) session.getAttribute("user");
        model.addAttribute("sessionId",sessionId);
        return "costumer-page";
    }


}
