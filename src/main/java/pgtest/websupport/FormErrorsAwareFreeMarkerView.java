package pgtest.websupport;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  Adds a flag to the request in case there are form errors
 *  Used for global page error notification
 */
public class FormErrorsAwareFreeMarkerView extends FreeMarkerView {
    private static final String HAS_ERRORS_FLAG = "hasFormErrors";

    protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        checkIfThereAreBindingErrors(model);
        super.doRender(model, request, response);
    }

    public void checkIfThereAreBindingErrors(Map<String, Object> model) {
        for (String key : model.keySet()) {
            Object value = model.get(key);
            if (value != null && value instanceof BindingResult) {
                if (((BindingResult) value).hasErrors()) {
                    model.put(HAS_ERRORS_FLAG, Boolean.TRUE);
                    break;
                }
            }
        }

    }
}
