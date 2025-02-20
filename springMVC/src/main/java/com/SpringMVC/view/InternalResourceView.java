package com.SpringMVC.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/19
 **/

/**
 * 支持JSP、HTML的渲染，核心作用是将请求转发到服务器内部的资源
 */
public class InternalResourceView extends AbstractView{
    /**
     * 服务端转发的目的url，即要求的JSP或HTML资源的位置
     */
    private String url;

    public InternalResourceView(String url){
        this.url=url;
    }

    /**
     * 转发请求到对应资源的url
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    public void renderMergeOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        exposeModelAsRequestAttributes(model,request);

        //请求转发
        RequestDispatcher dispatcher=request.getRequestDispatcher(url);
        dispatcher.forward(request,response);
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    /**
     * 将model中的数据作为参数加入request中。注意如果数据的值为空，则会从request中删去对应名称的值
     * @param model
     * @param request
     */
    private void exposeModelAsRequestAttributes(Map<String,Object> model,HttpServletRequest request){
        for(String key:model.keySet()){
            if(model.get(key)!=null){
                request.setAttribute(key,model.get(key));
            }else{
                request.removeAttribute(key);
            }
        }
    }
}
