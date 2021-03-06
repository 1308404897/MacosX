package com.macos.framework.starter.nacos.proxy;


import com.macos.aop.proxy.BeanProxy;
import com.macos.common.util.JsonUtil;
import com.macos.common.util.TypeUtil;
import com.macos.framework.nacos.rest.RestClient;
import com.macos.framework.starter.nacos.handle.NacosHandle;
import com.macos.framework.starter.nacos.handle.bean.HandleBeanInfo;
import com.macos.framework.starter.nacos.mapping.NacosMapping;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * @Desc DyNacosProxy
 * @Author Zheng.LiMing
 * @Date 2019/9/16
 */
public class NacosProxy extends BeanProxy<RestClient> {


    public NacosProxy(RestClient restClient){
        super(restClient);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        HandleBeanInfo info = NacosMapping.handleUrl(method);
        NacosMapping.initPathValut(info,method,args);
        String reuslt= (String) NacosHandle.execu(target,info);
        Class returnType=method.getReturnType();
        if (TypeUtil.isBaseType(returnType,true)){
            return TypeUtil.baseType(returnType.getSimpleName(),reuslt);
        }else{
            ParameterizedType type = (ParameterizedType)returnType.getGenericSuperclass();
            Class cl=type.getActualTypeArguments()[0].getClass();
            if (returnType==List.class){
                return JsonUtil.jsonToList(reuslt,cl);
            }else if (returnType.getSuperclass()==Object.class){
                return JsonUtil.jsonToBean(reuslt,cl);
            }else if (returnType==Map.class){
                return JsonUtil.jsonToMap(reuslt,cl, type.getActualTypeArguments()[1].getClass());
            }
        }
        return reuslt;
    }
}
