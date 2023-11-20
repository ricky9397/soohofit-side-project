package com.project.soohofit.common;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public class BaseUtils {

    /**+
     * IP 공통
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * String 타입 null 체크
     * @param string
     * @return
     */
    public static boolean isNull(String string) {
        return "".equals(string) || string.equalsIgnoreCase("null") || string.isEmpty();
    }

    /**
     * Object null, 공백 체크 공통
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null || obj.equals("") || obj.toString().length() == 0) return true;
        if ((obj instanceof String) && (((String) obj).trim().length() == 0))
            return true;
        if (obj instanceof Map)
            return ((Map<?, ?>) obj).isEmpty();
        if (obj instanceof Map)
            return ((Map<?, ?>) obj).isEmpty();
        if (obj instanceof List)
            return ((List<?>) obj).isEmpty();
        if (obj instanceof Object[])
            return (((Object[]) obj).length == 0);

        return false;
    }





}
