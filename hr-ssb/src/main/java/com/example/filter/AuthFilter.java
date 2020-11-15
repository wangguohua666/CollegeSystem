package com.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@WebFilter(filterName = "authFilter",urlPatterns = "/*")
public class AuthFilter implements Filter {

    Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    @Value("${auth.login.pattern}")
    private String urlPattern;

    @Value("${auth.login.loginUrl}")
    private String loginUrl;

    @Value("${auth.login.host}")
    private String host;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String url = httpServletRequest.getRequestURI();

        LOG.info("=======url:"+url);
        if (url.matches(urlPattern)){
            String user = (String) httpServletRequest.getSession().getAttribute("user");
            if(user!=null){
                chain.doFilter(servletRequest,servletResponse);
                return;
            }else {
                String requestUrl = getRequestUrl(httpServletRequest);
                httpServletResponse.sendRedirect(loginUrl + "?url=" + requestUrl);
                return;
            }
        }
        chain.doFilter(httpServletRequest,httpServletResponse);
        return;
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

    @Override
    public void destroy() {

    }
}
