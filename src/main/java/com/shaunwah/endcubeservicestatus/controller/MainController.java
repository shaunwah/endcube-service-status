package com.shaunwah.endcubeservicestatus.controller;

import com.shaunwah.endcubeservicestatus.service.VelorenAuthServerService;
import com.shaunwah.endcubeservicestatus.service.VelorenGameServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MainController {
    @Autowired
    VelorenGameServerService velorenGameServerService;
    @Autowired
    VelorenAuthServerService velorenAuthServerService;

    @GetMapping
    public String getMain(Model model) {
        model.addAttribute("velorenGameServer", velorenGameServerService.ping());
        model.addAttribute("velorenAuthServer", velorenAuthServerService.ping());
        return "main";
    }
}
