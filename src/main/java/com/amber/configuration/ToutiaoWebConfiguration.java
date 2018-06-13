package com.amber.configuration;

import com.amber.requestinterceptor.LoginRequiredInterceptor;
import com.amber.requestinterceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        //全局拦截
        registry.addInterceptor(passportInterceptor);
        //只对setting页面进行访问拦截
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting","/user/addNews","/login");
        super.addInterceptors(registry);
    }
}
