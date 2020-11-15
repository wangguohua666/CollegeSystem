package com.zb.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Value("${auth.login.pattern}")
    private String urlPattern;

    @Value("${auth.login.loginUrl}")
    private String loginUrl;

    @Value("${auth.login.host}")
    private String host;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        HttpServletRequest httpServletRequest = request;
        HttpServletResponse httpServletResponse = response;
        String url = request.getRequestURI();
        LOG.info("=============url:"+url);

        if(url.matches(urlPattern)){
            String user = (String) httpServletRequest.getSession().getAttribute("user");
            if(user!=null){
                //不需要拦截
                return true;
            }else {
                String requestUrl = getRequestUrl(httpServletRequest);
                httpServletResponse.sendRedirect(loginUrl+"?url="+requestUrl);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图渲染之前（controller调用之后）
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行了postHandle方法");
    }

    /**
     *在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图后执行 （主要用于资源清理工作）
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("执行了afterCompletion方法");
    }


    private String getRequestUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String requestURL = host + request.getRequestURI();
        String queryStirng = request.getQueryString();
        if(StringUtils.isEmpty(queryStirng)){
            return requestURL;
        }else {
            StringBuilder result = new StringBuilder();
            result.append(URLEncoder.encode(requestURL + "?","utf-8"));
            String[] qsArray = queryStirng.split("&");
            String[] qsPair = null;
            if(qsArray!=null && qsArray.length>0){
                for (String qsKeyValue : qsArray){
                    qsPair = qsKeyValue.split("=");
                    if(qsPair!=null && qsPair.length==2){
                        result.append(qsPair[0]).append(URLEncoder.encode("=","utf-8"));
                        result.append(URLEncoder.encode(qsPair[1],"utf-8"));
                        result.append(URLEncoder.encode("&","utf-8"));
                    }
                }
            }
            return result.toString();
        }
    }
}
