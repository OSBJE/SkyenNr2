package Oenskeskyen.Controller;

import Oenskeskyen.Model.User;
import Oenskeskyen.Service.OenskeSkyenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class OenskeSkyenController {

    private final OenskeSkyenService oenskeSkyenService;

    public OenskeSkyenController(OenskeSkyenService service){
        this.oenskeSkyenService = service;
    }

    @GetMapping("/newUser")
    public String creatNewUser(Model model){
        User obj = new User();
        model.addAttribute("obj", obj);
        return "newUser";
    }

    @PostMapping("/saveUser")
    public String saveNewUser(@ModelAttribute User userObj){
        oenskeSkyenService.saveUserCostumer(userObj);
        return "newUser";
    }


}
