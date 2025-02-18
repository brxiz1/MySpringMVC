package com.SpringMVC;

import com.SpringMVC.http.HttpStatus;
import org.springframework.ui.Model;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
public class ModelAndView {
    private Object view;
    private Model model;
    private HttpStatus status;

    public String getViewName(){
        if(view instanceof String){
            return (String) view;
        }else{
            return null;
        }
    }

    public void setViewName(String name){
        view=name;
    }

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
}
