package pgtest.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pgtest.audit.domain.AuditTrace;
import pgtest.audit.service.AuditService;
import pgtest.security.domain.User;
import pgtest.security.service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginCustomSuccessHandler implements AuthenticationSuccessHandler {

    private SecurityService securityService;
    private AuditService auditService;

    /**
     * Calls the parent class {@code handle()} method to forward or redirect to the target URL, and
     * then calls {@code clearAuthenticationAttributes()} to remove any leftover session data.
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        User user = ((User) authentication.getPrincipal());
        clearAuthenticationAttributes(request);

        securityService.updateLastLoginInfoForUser(user.getId());
        AuditTrace auditTrace = new AuditTrace();
        auditTrace.setName("USER_LOGIN");
        auditTrace.setUserId(user.getId());
        auditTrace.setIp(request.getRemoteAddr());
        auditTrace.setSessionId(request.getSession().getId());
        auditService.insertAuditTrace(auditTrace);

        response.sendRedirect(request.getSession().getServletContext().getContextPath() + "cgi/home");
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the session
     * during the authentication process.
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }


}
