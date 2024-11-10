package Oenskeskyen.Controller;
import Oenskeskyen.Model.User;


import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
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

    @GetMapping("/wishlists")
    public String createNewWishList(Model model){
        WishList obj = new WishList();
        model.addAttribute("obj", obj);
        return "addWishList";
    }

    @PostMapping("/saveWishList")
    public String saveWishList(@ModelAttribute WishList wishListObj, HttpSession session){
        User user =  (User)session.getAttribute("user");
        if(user != null){
            int userID = user.getId();
            userService.saveWishList(wishListObj, userID);
            return "redirect:/customer-page";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/addWish/{wishListID}")
    public String createNewWish(@PathVariable("wishListID") int wishListID, Model model){
        Wish obj = new Wish();
        model.addAttribute("obj", obj);
        model.addAttribute("wishListID", wishListID);
        return "addWish";
    }

    @PostMapping("/saveWish")
    public String saveWish(@ModelAttribute Wish wishObj, @RequestParam("wishListID") int wishListID){
        userService.saveWish(wishObj, wishListID);
        return "redirect:/viewWishList/" + wishListID;
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }


    @GetMapping("/profile")
    public String profile(Model model){
        return "profile";
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

    @GetMapping("/customer-page")
    public String costumerPage(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("getAllWishLists", userService.getAllWishLists(user.getId()));
            return "customer-page";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/viewWishList/{wishListID}")
    public String viewWishList(@PathVariable("wishListID") int wishListID, Model model){
        WishList wishList = userService.getWishListById(wishListID);
        if(wishList != null){
            model.addAttribute("wishlist", wishList);
            model.addAttribute("getAllWishes", userService.getAllWishes(wishListID));
            return "viewWishList";
        } else {
            return "redirect:/customer-page";
        }
    }


    @GetMapping("/set_session_id")
    public String setSessionId(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("sessionId", session.getId());
        return "redirect:";
    }


}
