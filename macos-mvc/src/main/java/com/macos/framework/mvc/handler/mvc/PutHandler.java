package com.macos.framework.mvc.handler.mvc;

import com.macos.common.util.ReflectionsUtil;
import com.macos.framework.annotation.Put;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.enums.HttpMethod;
import com.macos.framework.mvc.util.HttpServerUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Desc PutHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/14
 */
public class PutHandler extends BaseHandler {

    public PutHandler() {
        handleAnnotations = new Class[]{Put.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (doBefore(mainClass,handleClass,args)){
            BeanDefinition target = BeanManager.getBeanDefinition(null,handleClass,true);

            List<Method> methods = ReflectionsUtil.getMethodsByAnnotation(handleClass,Put.class);

            if (target == null || methods.size()==0){
                return true;
            }
            String path = target.getRequestPath();

            for (Method method : methods) {
                if (!method.isAnnotationPresent(Put.class)){
                    continue;
                }
                Put put = method.getAnnotation(Put.class);
                String requestUrl = path + put.value();
                HttpServerUtil.createBeanAndAddServletContext(handleClass,method,requestUrl,HttpMethod.PUT);
            }

        }
        return doAfter(mainClass,handleClass,args);
    }
}
