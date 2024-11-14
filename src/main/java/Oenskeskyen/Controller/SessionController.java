package Oenskeskyen.Controller;
import Oenskeskyen.Model.User;



import Oenskeskyen.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SessionController {

    private final UserService userService;


    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/newUser")
    public String creatNewUser(Model model){
        User obj = new User();
        model.addAttribute("obj", obj);
        return "create-user";
    }

    @PostMapping("/saveUser")
    public String saveNewUser(@ModelAttribute User userObj){
        userService.saveUserCostumer(userObj);
        return "redirect:/";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(HttpSession session){
        User obj = (User) session.getAttribute("user");
        if(obj != null){
            userService.deleteUser(obj.getId());
            session.invalidate();
            return "redirect:/";
        }
        return "redirect:/login";
    }
    //need one for updating the user profile
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session){
        User obj = (User) session.getAttribute("user");
        model.addAttribute("obj", obj);
        return "profile";
    }


    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @PostMapping("/loginValidation")
    public String loginCheck (@RequestParam("mail") String mail, @RequestParam("password") String password,
                              HttpSession session, Model model){
       User user = userService.getUser(mail);
       if (user != null && user.getPassWord().equals(password)) {
           session.setAttribute("user", user);
           return "redirect:/customer-page";
       } else {
           model.addAttribute("error", "Invalid username or password");
           return "login";
       }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }



    @GetMapping("/set_session_id")
    public String setSessionId(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("sessionId", session.getId());
        return "redirect:";
    }

//    @GetMapping("/customer-page-load")
//    public String costumerPage(Model model, HttpSession session){
//        User user = (User) session.getAttribute("user");
//        if(user != null){
//            model.addAttribute("user", user);
//            model.addAttribute("getAllWishLists", oenskeSkyenService.getAllWishLists(user.getId()));
//            return "customer-page";
//        } else {
//            return "redirect:/login";
//        }
//    }


}
