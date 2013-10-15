package de.smava.slowmotion.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/15/13
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView printWelcome(ModelMap model) {
        ModelAndView result = new ModelAndView("status");
        result.addObject("status", "running");
        return result;
    }
}
