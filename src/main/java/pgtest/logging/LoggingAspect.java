package pgtest.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import pgtest.exception.BusinessException;
import pgtest.utils.Utilities;

import java.util.Collection;

/**
 *
 */
@Aspect
public class LoggingAspect {


    private static Logger log;
//
//    @Pointcut("within(velti.tech.pms..*) && !within(velti.tech.pms..conversion.*) && !within(velti.tech.pms..log.*)")
//    public void log() {
//    }

    @Pointcut("within(pgtest..service.*)")
    public void log() {
    }

    @Around("log()")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        log = Logger.getLogger(pjp.getTarget().getClass());
        String callShortString = null;
        if (log.isDebugEnabled()) {
            callShortString = pjp.toShortString();
            StringBuilder sb = new StringBuilder("[ENTER] ");
            sb.append(callShortString).append(" args=(");
            for (int i = 0; i < pjp.getArgs().length; i++) {
                sb.append(pjp.getArgs()[i]);
                if (i != pjp.getArgs().length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");

            log.debug(sb.toString());
        }

        Object output = null;
        try {
            output = pjp.proceed();
            if (log.isDebugEnabled()) {
                String returnValue = "";
                if (output != null) {
                    if (output instanceof Collection) {
                        returnValue = ((Collection) output).size() + " elements";
                    } else {
                        returnValue = output.toString();
                    }
                }
                returnValue = Utilities.shorten(returnValue, 50);
                long elapsedTime = System.currentTimeMillis() - start;
                log.debug("[LEAVE] " + callShortString + " (" + elapsedTime + "ms): " + returnValue);
            }
        } catch (Throwable t) {
            if (t instanceof BusinessException || t.getCause() instanceof BusinessException) {
                log.debug(t.getMessage());
            } else {
                log.error("Uncaught exception: ", t);
            }
            throw (t);
        }
        return output;

    }
}