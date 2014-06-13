package pgtest.websupport;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public final class MessageHolder {
	
	public static final String FLASH_MAP_ATTRIBUTE = MessageHolder.class.getName();
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getCurrent(HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		Map<String, Object> flash = (Map<String, Object>) session.getAttribute(FLASH_MAP_ATTRIBUTE);
		if (flash == null) {
			flash = new HashMap<String, Object>();
			session.setAttribute(FLASH_MAP_ATTRIBUTE, flash);
		}
		return flash;
	}
	
	private MessageHolder() {
	}

	public static void put(String key, Object value, boolean persist) {
        HttpServletRequest request = getRequest(RequestContextHolder.currentRequestAttributes());
        if(persist) {
            getCurrent(request).put(key, value);
        } else {

            request.setAttribute(key, value);
        }
	}

	public static void setInfoMessage(String info, boolean persist) {
		put(Message.MESSAGE_KEY, new Message(MessageType.information, info), persist);
	}

	public static void setWarningMessage(String warning, boolean persist) {
		put(Message.MESSAGE_KEY, new Message(MessageType.attention, warning), persist);
	}

	public static void setErrorMessage(String error, boolean persist) {
		put(Message.MESSAGE_KEY, new Message(MessageType.error, error), persist);
	}

	public static void setSuccessMessage(String success, boolean persist) {
		put(Message.MESSAGE_KEY, new Message(MessageType.success, success), persist);
	}

	private static HttpServletRequest getRequest(RequestAttributes requestAttributes) {
		return ((ServletRequestAttributes)requestAttributes).getRequest();
	}

}