package com.shaunwah.endcubeservicestatus.controller;

import com.shaunwah.endcubeservicestatus.model.VelorenStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainController {
    @GetMapping
    public String getMain(Model model) throws Exception {
        VelorenStatus velorenStatus = new VelorenStatus();
        model.addAttribute("velorenStatus", velorenStatus);

        return "main";
    }
}
