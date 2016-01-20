/**
 * 
 */
package com.razorthink.rzt.internal.management.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shams
 *
 */
public class CookiesUtil {

    public static void setCookies( HttpServletResponse httpServletResponse, String name, String value )
    {
        Cookie userName = new Cookie(name, value);
        userName.setMaxAge(-1);
        httpServletResponse.addCookie(userName);
    }

    public static void removeCookie( HttpServletResponse httpServletResponse, String name )
    {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setValue(null);
        httpServletResponse.addCookie(cookie);
    }

    public static String getCookie( HttpServletRequest request, String sessionid )
    {
        if( request != null && request.getCookies() != null )
        {
            Cookie[] cookies = request.getCookies();

            for( int i = 0; i < cookies.length; i++ )
            {
                Cookie cookie = cookies[i];
                if( cookie.getName().equalsIgnoreCase(sessionid) )
                {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
