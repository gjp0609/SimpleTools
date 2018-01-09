package me.rainbow.controller;

import me.rainbow.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author guojinpeng
 * @date 18.1.3 14:09
 */
@Controller
public class BaseController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest() : null;
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected String getUserIp() {
        return HttpUtil.getIp(getRequest());
    }

//    protected User getUser() {
//        return (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
//    }
}
