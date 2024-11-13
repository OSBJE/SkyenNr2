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


    @PostMapping("/deleteWishList/{wishListId}")
    public String deleteWishList(@PathVariable("wishListId") int wishlistId){
        oenskeSkyenService.deleteWishList(wishlistId);
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

    @GetMapping("/edit-wish/{wishListId}/{wishId}")
    public String editWish(@PathVariable ("wishListId") int wishListId, @PathVariable("wishId") int wishId, Model model) {
        Wish wish = oenskeSkyenService.getWishByWishListID(wishListId, wishId);
        if (wish != null) {
            model.addAttribute("wish", wish);
            return "editWish";
        } else {
            return "redirect:/customer-page";
        }
    }

    @PostMapping("/delete-wish/{wishListId}/{wishId}")
    public String deleteWish(@PathVariable("wishId") int wishId,
                             @PathVariable("wishListId") int wishListId) {
        oenskeSkyenService.deleteWish(wishId);
        return "redirect:/viewWishList/" + wishListId;

    }

    @PostMapping("/update-wish")
    public String updateWish(@RequestParam("wishId") int wishId,
                             @RequestParam("name") String name,
                             @RequestParam("price") double price,
                             @RequestParam("urlLink") String urlLink,
                             @RequestParam("wishListId") int wishListId) {
        Wish updatedWish = new Wish(name, price, urlLink, wishId);
        oenskeSkyenService.editWish(wishId, updatedWish);
        return "redirect:/viewWishList/" + wishListId;
    }






    //talk to the others if this should belong in here, as the customer-page should be in sessions-controller
    //although to display the wishlist, it needs access to oenskeskyen repository since the logic belongs there
    @GetMapping("/customer-page")
    public String costumerPage(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            //model.addAttribute("user", user);
            model.addAttribute("getAllWishLists", oenskeSkyenService.getAllWishLists(user.getId()));
            model.addAttribute("wishCount", oenskeSkyenService.getCountOfWishes(user.getId()));
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
