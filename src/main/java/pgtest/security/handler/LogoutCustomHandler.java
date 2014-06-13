package pgtest.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCustomHandler extends SecurityContextLogoutHandler {
    private TokenBasedRememberMeServices forgetMe;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        super.logout(request, response, authentication);
      //  forgetMe.logout(request, response, authentication);
    }

    public void setForgetMe(TokenBasedRememberMeServices forgetMe) {
        this.forgetMe = forgetMe;
    }

}
