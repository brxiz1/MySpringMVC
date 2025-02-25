package com.SpringMVC.view.resolver;

/**
 * @author ZhangYihe
 * @since 2025/2/20
 **/

import com.SpringMVC.utils.RequestContextHolder;
import com.SpringMVC.view.RedirectView;
import com.SpringMVC.view.View;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * 视图协同器，定义了所有支持的viewResolver和默认的View,
 * 根据视图名和用户请求头信息中的Accept字段匹配出最佳视图
 */
public class ContentNegotiatingViewResolver implements ViewResolver, InitializingBean {
    /**
     * 所有视图解析器列表
     */
    List<ViewResolver> viewResolvers=new ArrayList<>();
    /**
     * 默认视图列表
     */
    List<View> defaultViews;

    /**
     * 选择合适的视图解析器解析出对应视图
     * @param viewName
     * @return
     * @throws Exception
     */
    @Override
    public View resolveViewName(String viewName) throws Exception {
        List<View> candidates=getCandidateViews(viewName);
        View bestView=getBestView(candidates);
        return bestView;
    }

    /**
     * 根据视图名称，获取所有候选视图
     * @param viewName
     * @return
     */
    private List<View> getCandidateViews(String viewName) throws Exception {
        List<View> candidates=new ArrayList<>();
        for(ViewResolver resolver:viewResolvers){
            View candidate=resolver.resolveViewName(viewName);
            if(candidate!=null){
                candidates.add(candidate);
            }
        }
        if(!CollectionUtils.isEmpty(defaultViews)){
            candidates.addAll(defaultViews);
        }
        return candidates;
    }

    /**
     * 从所有视图中，根据request的Accept字段，匹配出最佳视图
     * @param candidateViews
     * @return
     */
    private View getBestView(List<View> candidateViews){
        //重定向视图拥有最高优先级
        for(View view:candidateViews){
            if(view instanceof RedirectView){
                return view;
            }
        }
        //根据request的Accept字段选取合适的视图
        HttpServletRequest request= RequestContextHolder.getRequest();
        Enumeration<String> accepts=request.getHeaders("Accept");
        while(accepts.hasMoreElements()){
            String accept= accepts.nextElement();
            for(View view:candidateViews){
                if(accept.equals(view.getContentType())){
                    return view;
                }
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(viewResolvers,"viewResolvers can not null");
    }

    public List<ViewResolver> getViewResolvers() {
        return viewResolvers;
    }

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    public List<View> getDefaultViews() {
        return defaultViews;
    }

    public void setDefaultViews(List<View> defaultViews) {
        this.defaultViews = defaultViews;
    }
}
