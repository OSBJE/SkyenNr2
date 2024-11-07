package Oenskeskyen.Controller;
import Oenskeskyen.Model.User;


import Oenskeskyen.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    private final UserService userService;


    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String frontPage(){
        return "index";
    }

    @GetMapping("/newUser")
    public String creatNewUser(Model model){
        User obj = new User();
        model.addAttribute("obj", obj);
        return "newUser";
    }

    @PostMapping("/saveUser")
    public String saveNewUser(@ModelAttribute User userObj){
        userService.saveUserCostumer(userObj);
        return "newUser";
    }

    /*
    @GetMapping("/login")
    public String loginCheck (@RequestParam("mail") String mail, @RequestParam("password") String password, HttpSession session){
        if(userService.getUser(mail)){
            session.setAttribute("user",userService.getUser(mail));
            return "redirect:/CostumerPage";
        } else {
            return "redirect:/index";
        }
    }
    */


}
