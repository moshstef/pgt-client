package pgtest.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import pgtest.security.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    private final static String LOGGED_IN_USER = "LOGGED_IN_USER";
    private static final Logger log = Logger.getLogger(MainController.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String accessDenied() {
        return "access_denied";
    }

    @RequestMapping(value = "/internal_error", method = RequestMethod.GET)
    public String internalError() {
        return "internal_error";
    }

    @RequestMapping(value = "/secured/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @ModelAttribute(value = "LOGGED_IN_USER")
    public User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute(LOGGED_IN_USER);
    }


    @ExceptionHandler(Exception.class)
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
     /*   if (e instanceof AccessDeniedException) {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
                    return new ModelAndView("redirect:/cgi/access_denied");
                else
                    return new ModelAndView("redirect:/cgi/login");

            }
        } else if (e instanceof MaxUploadSizeExceededException) {
//TODO
            return new ModelAndView("redirect:/cgi/internal_error");

        } else {
            log.error(e, e);
            e.printStackTrace();
        }*/
        return new ModelAndView("redirect:/cgi/internal_error");

    }

    private void assertUserIsLoggedIn(HttpSession session) throws Exception {
        if (getLoggedInUser(session) == null) {
            throw new Exception("User is not logged in");
        }
    }

}
