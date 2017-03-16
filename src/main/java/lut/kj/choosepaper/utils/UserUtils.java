package lut.kj.choosepaper.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpSession;


/**
 * Created by kj on 2017/3/16.
 */
public class UserUtils {

    private UserUtils(){

    }
    public static HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public static String  getUserId(){
        HttpSession httpSession=getSession();
        return httpSession.getAttribute("userId").toString();
    }
}

