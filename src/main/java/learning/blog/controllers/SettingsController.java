package learning.blog.controllers;

import learning.blog.config.ConfigDAO;
import learning.blog.config.ConfigData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SettingsController {
    @RequestMapping(value = "/admin/settings", method = RequestMethod.GET)
    public String getSettingsForm(Model model){
        ConfigDAO data = new ConfigDAO();
        model.addAttribute("data", data);
        return "admin/settingsForm";
    }
    @RequestMapping(value = "admin/settings", method = RequestMethod.POST)
    public String postSettings(ConfigDAO data){
        ConfigData.BACKGROUND_URL =data.getUrl();
        ConfigData.HOME_QUOTE = data.getQuote();
        return "redirect:/home";
    }
}
