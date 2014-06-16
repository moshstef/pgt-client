package pgtest.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 *
 */
@ControllerAdvice
public class WebExceptionHandler implements HandlerExceptionResolver {
    private Logger log = Logger.getLogger(WebExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
/*        if (e instanceof AccessDeniedException) {
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
}
