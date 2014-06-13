package pgtest.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pgtest.security.domain.Authority;
import pgtest.security.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class WebUtils {

    private static ApplicationContext ctx;

    public static ApplicationContext getSpringContext() {
        return ctx;
    }

    public static Object getSpringBean(String id) {
        return ctx.getBean(id);
    }

    public static <T> T getSpringBean(String id, Class<T> clazz) {
        return ctx.getBean(id, clazz);
    }

    public static boolean existsSpringBean(String id) {
        return ctx.containsBean(id);
    }


    public static SimpleDateFormat getIsoDateFormat() {
        //return (SimpleDateFormat)getSpringBean("isoDateFormat");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        simpleDateFormat.setLenient(false);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public static HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public static User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }
        return null;
    }

    public static boolean userHasAuthority(String authority) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Authority(authority));
        }
        return false;
    }

    public static boolean userHasAuthoritiesOr(String[] authorities) {
        List<Authority> authList = new ArrayList<Authority>(authorities.length);
        for (int i = 0; i < authorities.length; i++) {
            authList.add(new Authority(authorities[i]));
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return CollectionUtils.containsAny(SecurityContextHolder.getContext().getAuthentication().getAuthorities(), authList);
        }
        return false;
    }

    public static boolean userHasAuthoritiesAnd(String[] authorities) {
        List<Authority> authList = new ArrayList<Authority>(authorities.length);
        for (int i = 0; i < authorities.length; i++) {
            authList.add(new Authority(authorities[i]));
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getAuthorities().containsAll(authList);
        }
        return false;
    }


    public static String getRealFilePath(String filePath){
        return ((WebApplicationContext)WebUtils.getSpringContext()).getServletContext().getRealPath(filePath);
    }

    /**
     * Injected from the class "ApplicationContextProvider" which is automatically
     * loaded during Spring-Initialization.
     *
     * @param applicationContext the spring context
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static DateFormat getDefaultDateFormat() {
        //TODO
         return null;
    }

    public static DateFormat getDefaultDateTimeFormat() {
        //TODO
        return null;
    }
}
