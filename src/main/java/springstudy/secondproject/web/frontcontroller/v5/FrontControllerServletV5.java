package springstudy.secondproject.web.frontcontroller.v5;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import springstudy.secondproject.web.frontcontroller.ModelView;
import springstudy.secondproject.web.frontcontroller.MyView;
import springstudy.secondproject.web.frontcontroller.v3.ControllerV3;
import springstudy.secondproject.web.frontcontroller.v3.controller.MemberFormControllerV3;
import springstudy.secondproject.web.frontcontroller.v3.controller.MemberListControllerV3;
import springstudy.secondproject.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import springstudy.secondproject.web.frontcontroller.v4.ControllerV4;
import springstudy.secondproject.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> myHandlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    private void initHandlerAdapters() {
        myHandlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 핸들러 조회. 없을 경우 404
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 핸들러 어댑터 조회 - 핸들러를 처리할 수 있는 어댑터
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 3. 핸들러 어댑터 실행 -> 핸들러 어댑터를 통해 핸들러 실행 -> ModelView 반환
        ModelView mv = adapter.handle(request, response, handler);

        // 4. ModelView에서 view 이름 조회
        String viewName = mv.getViewName();

        // 5. 뷰 리졸버를 통해서 MyView 생성 (4번에서 가져온 viewName으로 URI 경로 완성하여 생성)
        // 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 랜더링 역할을 담당하는 뷰 객체를 반환
        MyView view = viewResolver(viewName);

        // 6. 뷰 랜더링
        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : myHandlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
