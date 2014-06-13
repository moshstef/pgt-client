package pgtest.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pgtest.security.domain.User;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    private final static String LOGGED_IN_USER = "LOGGED_IN_USER";
    private static final Logger log = Logger.getLogger(MainController.class);



    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @ModelAttribute(value = "LOGGED_IN_USER")
    public User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute(LOGGED_IN_USER);
    }


    private void assertUserIsLoggedIn(HttpSession session) throws Exception {
        if (getLoggedInUser(session) == null) {
            throw new Exception("User is not logged in");
        }
    }

}
