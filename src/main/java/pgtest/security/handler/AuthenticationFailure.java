package pgtest.security.handler;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pgtest.security.domain.User;
import pgtest.security.service.ISecurityService;
import pgtest.websupport.Message;
import pgtest.websupport.MessageHolder;
import pgtest.websupport.MessageType;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AuthenticationFailure implements AuthenticationFailureHandler {
    private static final Logger log = Logger.getLogger(AuthenticationFailure.class);

    private ISecurityService ISecurityService;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.warn(e);


        if (e.getExtraInformation() != null) {
            User user = (User) e.getExtraInformation();
            if (e instanceof BadCredentialsException) {
                ISecurityService.increaseLoginTries(user.getId());
                putFlashMessage("bad.credentials", request);
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/cgi/login");
                return;
            } else if (!user.isAccountNonLocked()) {
                putFlashMessage("account.isLocked", request);
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/cgi/login");
                return;
            } else if (!user.isEnabled()) {
                putFlashMessage("account.isInactive", request);
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/cgi/login");
                return;
            }

        } else {
            if (e instanceof BadCredentialsException) {
                putFlashMessage("bad.credentials", request);
            } else {
                log.error("Unexpected authentication error",e);
                putFlashMessage("problem.withRetrievingUserAccount", request);

            }
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/cgi/login");
        }
        return;

    }

    private void putFlashMessage(String messageText, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        Message message = new Message(MessageType.error, messageText);
        map.put(Message.MESSAGE_KEY, message);
        request.getSession().setAttribute(MessageHolder.FLASH_MAP_ATTRIBUTE, map);
    }

    public void setSecurityService(ISecurityService ISecurityService) {
        this.ISecurityService = ISecurityService;
    }
}
