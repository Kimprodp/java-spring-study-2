package springstudy.secondproject.web.frontcontroller.v3;

import java.util.Map;
import java.util.Objects;
import springstudy.secondproject.web.frontcontroller.ModelView;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);
}
