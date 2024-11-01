package Oenskeskyen.Controller;

import Oenskeskyen.Service.OenskeSkyenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class OenskeSkyenController {

    private final OenskeSkyenService oenskeSkyenService;

    public OenskeSkyenController(OenskeSkyenService service){
        this.oenskeSkyenService = service;
    }
}
