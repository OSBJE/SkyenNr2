package Oenskeskyen.Controller;

import Oenskeskyen.Model.User;
import Oenskeskyen.Model.Wish;
import Oenskeskyen.Model.WishList;
import Oenskeskyen.Service.OenskeSkyenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class OenskeSkyenController {


    private final OenskeSkyenService oenskeSkyenService;


    public OenskeSkyenController(OenskeSkyenService oenskeSkyenService) {
        this.oenskeSkyenService = oenskeSkyenService;
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
            oenskeSkyenService.saveWishList(wishListObj, userID);
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
        oenskeSkyenService.saveWish(wishObj, wishListID);
        return "redirect:/viewWishList/" + wishListID;
    }

    @PostMapping("/deleteWishList/{wishListID}")
    public String deleteWishList(@PathVariable("wishListID") int wishlistID){
        oenskeSkyenService.deleteWishList(wishlistID);
        return "redirect:/customer-page";
    }

    @GetMapping("/viewWishList/{wishListID}")
    public String viewWishList(@PathVariable("wishListID") int wishListID, Model model){
        WishList wishList = oenskeSkyenService.getWishListById(wishListID);
        if(wishList != null){
            model.addAttribute("wishlist", wishList);
            model.addAttribute("getAllWishes", oenskeSkyenService.getAllWishes(wishListID));
            return "viewWishList";
        } else {
            return "redirect:/customer-page";
        }


    }

    //talk to the others if this should belong in here, as the customer-page should be in sessions-controller
    //although to display the wishlist, it needs access to oenskeskyen repository since the logic belongs there
    @GetMapping("/customer-page")
    public String costumerPage(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            //model.addAttribute("user", user);
            model.addAttribute("getAllWishLists", oenskeSkyenService.getAllWishLists(user.getId()));
            return "customer-page";
        } else {
            return "redirect:/login";
        }
    }

//    @GetMapping("/customer-page")
//    public String costumerPage(Model model, HttpSession session){
//            User user = (User) session.getAttribute("user");
//            model.addAttribute("getAllWishLists", oenskeSkyenService.getAllWishLists(user.getId()));
//            return "customer-page";
//    }


    @GetMapping("/sessionId")
    public String getSessionId(Model model, HttpSession session){
        session.setAttribute("user","getmail");
        String sessionId = (String) session.getAttribute("user");
        model.addAttribute("sessionId",sessionId);
        return "customer-page";
    }


}
