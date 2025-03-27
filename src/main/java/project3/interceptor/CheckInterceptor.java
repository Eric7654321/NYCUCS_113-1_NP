package project3.interceptor;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import project3.pojo.Result;
import project3.utils.JwtUtils;

@Slf4j
@Component
public class CheckInterceptor implements HandlerInterceptor {
//    @Autowired
//    UtilsMapper utilsMapper;
//    @Autowired
//    UtilsService utilsService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        log.info("攔截器已被觸發");

        String url = req.getRequestURI();
        log.info("請求的url: {}", url);

        // 放行登入和註冊請求
        if (url.contains("login") || url.contains("register")) {
            log.info("放行操作: {}", url);
            return true;
        }

        String jwt = req.getHeader("token");

        if (!StringUtils.hasLength(jwt)) {
            log.info("請求token為null,未登入");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }

        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) { // 解析失敗
            e.printStackTrace();
            log.info("解析失敗，返回錯誤訊息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }

        log.info("合法!");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        //System.out.println("postHandle!");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        //System.out.println("afterCompletion!");

        String ipAddress = request.getRemoteAddr();
        //utilsMapper.setDeviceTime(ipAddress, LocalDateTime.now());
    }
}
