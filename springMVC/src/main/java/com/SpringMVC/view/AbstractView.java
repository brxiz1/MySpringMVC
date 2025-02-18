package com.SpringMVC.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/19
 **/

/**
 * View的抽象实现，将render方法分为了返回体准备和实际处理两步完成
 */
public abstract class AbstractView implements View{
    @Override
    public void render(Map<String,Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        prepareResponse(request,response);
        renderMergeOutputModel(model,request,response);
    }

    /**
     * 进行视图渲染前需要完成的预处理工作
     * @param request
     * @param response
     */
    public void prepareResponse(HttpServletRequest request,HttpServletResponse response){

    }

    /**
     *执行渲染的具体逻辑
     * @param model
     * @param request
     * @param response
     */
    public abstract void renderMergeOutputModel(Map<String,Object> model,HttpServletRequest request,HttpServletResponse response) throws Exception;
}
