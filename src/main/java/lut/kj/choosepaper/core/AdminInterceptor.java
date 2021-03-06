package lut.kj.choosepaper.core;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by kj on 2017/4/14.
 */
@Component
public class AdminInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession httpSession=httpServletRequest.getSession();
        String userId=(String) httpSession.getAttribute("userId");
        if("13240206".equals(userId)){
            System.out.println("++++++++管理员拦截器++++++");
            System.out.println("当前管理员用户为：【"+userId+"】");
            return true;
        }
        else{
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/index.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
