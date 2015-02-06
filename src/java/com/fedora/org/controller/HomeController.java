package com.fedora.org.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fedora.org.domain.User;
import com.fedora.org.repository.LoginRepository;
import com.fedora.org.repository.impl.LoginRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    @Autowired
    private LoginRepository loginRepository;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);
        model.addAttribute("greeting", "Welcome to Web Store!");
        model.addAttribute("tagline", "The one and only amazing web store");
        User user = loginRepository.getUserById(1L);

        List<User> list = loginRepository.selectList(20L);
        System.out.println(user);
        System.out.println(list);
        model.addAttribute("users", list);
        return "welcome";
    }


    @RequestMapping("/welcome/greeting")
    public String greeting() {
        return "welcome";
    }

}
