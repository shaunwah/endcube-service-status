package com.shaunwah.endcubeservicestatus.controller;

import com.shaunwah.endcubeservicestatus.model.VelorenAuthServer;
import com.shaunwah.endcubeservicestatus.model.VelorenGameServer;
import com.shaunwah.endcubeservicestatus.service.VelorenAuthServerService;
import com.shaunwah.endcubeservicestatus.service.VelorenGameServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping
public class MainController {
    @Autowired
    VelorenGameServerService velorenGameServerService;
    @Autowired
    VelorenAuthServerService velorenAuthServerService;

    @GetMapping
    public String getMain(Model model) {
        List<String> unavailableServices = new LinkedList<>();
        VelorenGameServer velorenGameServer = velorenGameServerService.pingAndHandleData();
        VelorenAuthServer velorenAuthServer = velorenAuthServerService.pingAndHandleData();
        if (!velorenGameServer.getIsOnline()) {
            unavailableServices.add(velorenGameServer.getName());
        }
        if (!velorenAuthServer.getIsOnline()) {
            unavailableServices.add(velorenAuthServer.getName());
        }
        model.addAttribute("unavailableServices", unavailableServices);
        model.addAttribute("velorenGameServer", velorenGameServer);
        model.addAttribute("velorenAuthServer", velorenAuthServer);
        return "main";
    }
}
