package com.siam.package_merchant.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.siam.package_common.util.StringUtils;
import com.siam.package_common.util.ToolUtil;
import com.siam.package_merchant.entity.Merchant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token工具类
 *
 * @author 暹罗
 */
public class TokenUtil {

    public static final String TOKEN_HEADER_NAME = "Authorization";

    /**
     * 生成商家token
     *
     * @author 暹罗
     */
    public static String generateToken(Merchant merchant) {
        String token = "";
        token = JWT.create().withAudience(merchant.getId().toString())
                .sign(Algorithm.HMAC256(merchant.getPassword()));
        return token;
    }

    /**
     * 获取token
     *
     * @author 暹罗
     */
    public static String getToken() {
        String authToken = null;
        HttpServletRequest request = HttpContext.getRequest();

        //1、从header中获取
        authToken = request.getHeader(TOKEN_HEADER_NAME);
        if (StringUtils.isEmpty(authToken)) {
            authToken = request.getHeader("token");
        }
        //2、从cookie中获取
        if (StringUtils.isEmpty(authToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (TOKEN_HEADER_NAME.equals(cookie.getName())) {
                        authToken = cookie.getValue();
                    }
                }
            }
        }
        //3、从URL参数中获取
        if (StringUtils.isEmpty(authToken)) {
            authToken = request.getParameter("token");
        }
//        if (StringUtils.isNotEmpty(authToken) && !authToken.equals("null")) {
//            addLoginCookie(authToken);
//        }
        return authToken;
    }

    /**
     * 创建登录cookie
     */
    public static void addLoginCookie(String token) {
        Cookie authorization = new Cookie(TOKEN_HEADER_NAME, token);
        authorization.setMaxAge(getJwtSecretExpireSec().intValue());
        authorization.setHttpOnly(false);
        authorization.setPath("/");

        Cookie randomKey_ = new Cookie("randomKey", ToolUtil.getRandomString(6));
        randomKey_.setMaxAge(getJwtSecretExpireSec().intValue());
        randomKey_.setHttpOnly(false);
        randomKey_.setPath("/");

        HttpServletResponse response = HttpContext.getResponse();
        response.addCookie(authorization);
        response.addCookie(randomKey_);
    }

    /**
     * 删除登录cookie
     */
    public static void deleteLoginCookie() {
        Cookie[] cookies = HttpContext.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_HEADER_NAME.equalsIgnoreCase(cookie.getName())) {
                    Cookie temp = new Cookie(cookie.getName(), "");
                    temp.setMaxAge(0);
                    temp.setPath("/");
                    HttpContext.getResponse().addCookie(temp);
                }
            }
        }
    }

    /**
     * 获取系统地密钥过期时间（单位：秒）
     */
    public static Long getJwtSecretExpireSec() {
        //设置7天过期
        Long defaultSecs = 7 * 24 * 60 * 60L;
        return defaultSecs;
    }
}