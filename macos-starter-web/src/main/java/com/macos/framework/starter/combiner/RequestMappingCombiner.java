package com.macos.framework.starter.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.mvc.handler.mvc.RequestMappingHandler;

/**
 * @Desc RequestMappingCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class RequestMappingCombiner extends BaseCombiner {

    private RequestMappingCombiner(){
        inint(new RequestMappingHandler());
    }

    private static final RequestMappingCombiner requestMappingCombiner = new RequestMappingCombiner();

    public static RequestMappingCombiner getCombiner(){
        return requestMappingCombiner;
    }

}
