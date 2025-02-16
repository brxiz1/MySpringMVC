package com.SpringMVC.handler;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

import com.SpringMVC.http.HttpStatus;
import org.springframework.ui.Model;

/**
 * 保存Handler处理过程中的Model和View
 */
public class ModelAndViewContainer {
    /**
     * view对象或者名称
     */
    private Object view;

    private Model model;

    private HttpStatus status;

    /**
     * 是否已经被handler处理完毕
     */
    private boolean requestHandled=false;


    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public boolean isRequestHandled() {
        return requestHandled;
    }

    public void setRequestHandled(boolean requestHandled) {
        this.requestHandled = requestHandled;
    }

    /**
     * 获取视图名称，如果view是视图对象，则返回null
     * @return
     */
    public String getViewName(){
        return view instanceof String?(String) view:null;
    }

    public void setViewName(String name){
        this.view=name;
    }

    /**
     * view是视图名称（true）还是视图对象（false）
     * @return
     */
    public boolean isViewReference(){
        return view instanceof String;
    }
}
