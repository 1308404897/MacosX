package com.macos.framework.core.handle.base;
import com.macos.aop.factory.ProxyFacotry;
import com.macos.common.util.ReflectionsUtil;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.context.ApplicationContextImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc 处理器
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public abstract class BaseHandler {

    /**前置处理集合*/
    protected Set<BaseHandler> beforeHandleMap = new HashSet<>();

    /**后置处理集合*/
    protected Set<BaseHandler> afterHandleMap = new HashSet<>();

    /**需要处理的注解*/
    protected Class[] handleAnnotations;


    /**
     * 应用程序上下文
     */
    protected static ApplicationContextApi applicationContextApi = ApplicationContextImpl.Builder.getApplicationContext();


    /**
     * 执行处理,返回true，则继续调用下个处理器
     * @param mainClass 程序入口对象
     * @param handleClass 当前处理对象
     * @param args
     * @return
     * @throws Exception
     */
    public abstract boolean doHandle(Class mainClass,Class handleClass,String[] args) throws Exception;

    /**
     * 执行前置处理
     * @param mainClass
     * @param handleClass
     * @param args
     * @throws Exception
     */
    protected boolean doBefore(Class mainClass,Class handleClass,String[] args) throws Exception {
        if (beforeHandleMap.size()==0){
            return true;
        }
       return execute(mainClass,handleClass,args, beforeHandleMap);
    }

    /**
     * 执行后置处理
     * @param mainClass
     * @param handleClass
     * @param args
     * @throws Exception
     */
    protected boolean doAfter(Class mainClass,Class handleClass,String[] args) throws Exception {
        if (afterHandleMap.size()==0){
            return true;
        }
       return execute(mainClass,handleClass, args,afterHandleMap);
    }

    /**
     * 执行处理
     * @param mainClass
     * @param handleClass
     * @param args
     * @param handleSet
     * @throws Exception
     */
    protected boolean execute(Class mainClass,Class handleClass, String[] args , Set<BaseHandler> handleSet) throws Exception {
        if (handleSet.size()>0) {
            for (BaseHandler handle : handleSet) {
                if (handle != null) {
                    boolean result = handle.doHandle(mainClass, handleClass, args);
                    if (!result) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /***
     * 注册前置处理器
     * @param value
     */
    public  void registerBeforeHandle(BaseHandler value) {
        beforeHandleMap.add(value);
    }

    /***
     * 注册后置处理器
     * @param value
     */
    public  void registerAfterHandle(BaseHandler value) {
        afterHandleMap.add(value);
    }


    /**
     * 注册开始调用方的类信息，也就是main的class信息
     * @param c
     * @throws Exception
     */
    public void registePathBeanDefinition(Class c) throws Exception {
        BeanManager beanManager =  new BeanManager();
        beanManager.registerBean(null,c);
    }

    /**
     * 创建类实例，并注册到上下文中
     * @param beanDefinition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ContextException
     */
    public Object newInstance(BeanDefinition beanDefinition,String beanName) throws ContextException {
        Class target = beanDefinition.getTarget();
        if (target.isInterface()){
            return null;
        }
        beanDefinition.setBeanName(beanName);
        beanDefinition.setContextApi(applicationContextApi);
        return applicationContextApi.registerBean(beanName,target);
    }

    public boolean needToHandle(Class handlerClass){
        for (Class annotation : handleAnnotations){
            if (handlerClass.isAnnotationPresent(annotation)){
                return true;
            }
        }
        return false;
    }


    public  Set<BaseHandler> getBeforeHandleMap() {
        return beforeHandleMap;
    }

    public  Set<BaseHandler> getAfterHandleMap() {
        return afterHandleMap;
    }

}
