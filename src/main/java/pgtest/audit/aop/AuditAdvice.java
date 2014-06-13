package pgtest.audit.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import pgtest.audit.domain.AuditTrace;
import pgtest.audit.service.AuditService;
import pgtest.security.domain.User;
import pgtest.utils.Utilities;
import pgtest.utils.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Advice to auditTrace services
 */
@Aspect
public class AuditAdvice {
    protected Logger log = Logger.getLogger(AuditAdvice.class);

    private AuditService auditService;

    private ParameterNameDiscoverer parameterNameDiscoverer;

    @Pointcut("execution(* pgtest..*(..)) && @annotation(audit)")
    public void auditTrace(pgtest.audit.annotation.Audit audit) {
    }

    @Around(value = "auditTrace(audit)", argNames = "pjp,audit")
    public Object process(ProceedingJoinPoint pjp, pgtest.audit.annotation.Audit audit) throws Throwable {
        AuditTrace auditTrace = new AuditTrace();

        //the type name for the trace
        String name = audit.value();
        auditTrace.setName(name);

        User user = WebUtils.getLoggedInUser();
        auditTrace.setUserId(user == null ? -1L : user.getId());

        String methodName = pjp.getSignature().getName();
        auditTrace.setMethodName(methodName);

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Object[] parameterValues = pjp.getArgs();
        Map<String, Object> params = new HashMap<String, Object>();
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                if(parameterValues[i] instanceof Model
                        || parameterValues[i] instanceof HttpSession
                        || parameterValues[i] instanceof ServletRequest
                        || parameterValues[i] instanceof ServletResponse
                        || parameterValues[i] instanceof Errors){
                    continue;
                }
                params.put(parameterNames[i],parameterValues[i]);
            }
        }
        auditTrace.setMethodParameters(Utilities.objToJsonStringWithIndentation(params));

        HttpServletRequest request = WebUtils.getCurrentRequest();
        if (request != null) {
          //  auditTrace.setRequestParameters(request.getParameterMap().toString());
            auditTrace.setIp(request.getRemoteAddr());
            auditTrace.setSessionId(request.getSession().getId());
        } else {
            auditTrace.setSessionId("System Call");
            auditTrace.setIp("localhost");
        }

        Object output = pjp.proceed(pjp.getArgs());

        //catch the runtime exception so as not interfere with the regular method run
        try {
            auditService.insertAuditTrace(auditTrace);
        } catch (RuntimeException e) {
            log.error("AuditTrace insert into db failed for: " + auditTrace.toString());
        }

        return output;
    }


    public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

}
