package com.fedora.org.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fedora.cache.impl.MemcachedService;
import com.fedora.org.domain.User;
import com.fedora.org.repository.LoginRepository;
import net.rubyeye.xmemcached.MemcachedClient;
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
    @Autowired
    private MemcachedService memcachedService;

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
        List<User> list = getUsers();
        System.out.println(user);
        System.out.println(list);
        model.addAttribute("users", list);
        return "welcome";
    }

    private List<User> getUsers() {
        List<User> list = (List<User>) memcachedService.get("userLIst");
        if(list != null){
            memcachedService.remove("userLIst");
            memcachedService.removeServer("localhost:12000");
        }
        if(list == null) {
           list = loginRepository.selectList(20L);
            memcachedService.set("userLIst",list);
        }
        return list;
    }


    @RequestMapping("/welcome/greeting")
    public String greeting() {
        return "welcome";
    }

}
