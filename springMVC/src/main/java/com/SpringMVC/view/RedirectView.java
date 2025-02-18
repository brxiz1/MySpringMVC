package com.SpringMVC.view;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/19
 **/

/**
 * 重定向视图
 */
public class RedirectView extends AbstractView{

    /**
     * 重定向地址
     */
    private String url;

    /**
     * url既可以是相对路径，又可以是绝对路径
     * @param url
     */
    public RedirectView(String url){
        this.url=url;
    }

    /**
     * 生成和发送重定向响应
     * @param model
     * @param request
     * @param response
     */
    @Override
    public void renderMergeOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl=createTargetURL(model,request);
        response.sendRedirect(redirectUrl);
    }

    /**
     * 获取web应用的根目录即上下文路径
     * @param request
     * @return
     */
    private String getContextPath(HttpServletRequest request){
        String contextPath=request.getContextPath();
        if(contextPath.startsWith("//"))contextPath=contextPath.substring(1);
        return contextPath;
    }

    /**
     * 将model中的数据加入到转发URL的参数中
     * @param model
     * @param request
     * @return
     */
    private String createTargetURL(Map<String, Object> model, HttpServletRequest request){
        Assert.notNull(url,"url can not null");
        StringBuilder queryParams=new StringBuilder();
        for(String key: model.keySet()){
            queryParams.append(key).append("=").append(model.get(key)).append("&");
        }
        if(queryParams.length()!=0){
            queryParams.deleteCharAt(queryParams.length()-1);
        }
        StringBuilder fullURL=new StringBuilder();
        //如果url是相对路径
        if(url.startsWith("/")){
            fullURL.append(getContextPath(request));
        }
        fullURL.append(url);
        if(queryParams.length()!=0)fullURL.append("?").append(queryParams);
        return fullURL.toString();
    }

    public String getUrl(){
        return url;
    }
}
