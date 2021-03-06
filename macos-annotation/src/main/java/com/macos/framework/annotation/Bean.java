package com.macos.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description 实例化一个bean
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    /**
     * bean的名字
     * @return
     */
    String value() default "";
}
