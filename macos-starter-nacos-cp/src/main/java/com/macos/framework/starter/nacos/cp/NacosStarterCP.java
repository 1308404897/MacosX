package com.macos.framework.starter.nacos.cp;


import com.macos.framework.annotation.MacosXApplicationStarter;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.listener.manager.MacosXListerManager;
import com.macos.framework.starter.DefaultStarter;
import com.macos.framework.starter.nacos.cp.listener.DybootNacosStarterListener;


/**
 * @Desc nacos服务启动订阅与发现
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
@MacosXApplicationStarter(scannerPath = {"com.macos.framework.nacos"},order = Integer.MAX_VALUE-200)
public class NacosStarterCP implements DefaultStarter {
    @Override
    public void doStart(ApplicationENV env, Class main , String[] args) throws Exception {
        MacosXListerManager.registerLister(new DybootNacosStarterListener());
    }

}
