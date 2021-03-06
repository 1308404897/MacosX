package com.macos.framework.starter.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.starter.handle.AutoConfigurationHandler;

/**
 * @Desc 自动配置包装器
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class AutoConfigurationCombiner extends BaseCombiner {

    private AutoConfigurationCombiner(){
        inint(new AutoConfigurationHandler());
    }

    private static final AutoConfigurationCombiner autoConfigurationCombiner = new AutoConfigurationCombiner();

    public static AutoConfigurationCombiner getCombiner(){
        return autoConfigurationCombiner;
    }
}
