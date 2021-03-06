package com.macos.framework.starter.handler;

import com.macos.framework.annotation.Order;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.starter.bean.WebBeanDefinition;


/**
 * @Desc OrderHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class OrderHandler extends BaseHandler {

    public OrderHandler() {
        handleAnnotations = new Class[]{Order.class};
    }


    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        if (!needToHandle(handleClass)){
            return true;
        }

        if (doBefore(mainClass,handleClass,args)){
            Order order = (Order)handleClass.getAnnotation(Order.class);
            Integer orderNum=(int)Math.random()*10000;;
            if (handleClass.isAnnotationPresent(Order.class)){
                Order dyOrder=(Order)handleClass.getAnnotation(Order.class);
                orderNum=dyOrder.value();
            }
            WebBeanDefinition beanDefinition = (WebBeanDefinition) BeanManager.getBeanDefinition(null,handleClass,true);
            beanDefinition.setOrder(orderNum);
        }

        return doAfter(mainClass,handleClass,args);
    }
}
