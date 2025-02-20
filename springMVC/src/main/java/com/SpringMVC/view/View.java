package com.SpringMVC.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/
public interface View {
    /**
     * 获取视图支持的ContentType
     * @return
     */
    default String getContentType() {
        return null;
    }

    void render(Map<String,Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
