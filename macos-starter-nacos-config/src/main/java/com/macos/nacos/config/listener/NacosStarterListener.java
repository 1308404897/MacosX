package com.macos.nacos.config.listener;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.listener.api.MacosXListener;
import com.macos.nacos.config.NacosConfig;
import com.macos.nacos.config.service.NacosConfigService;
import com.macos.nacos.config.util.PropertiesUtil;
import lombok.SneakyThrows;

import java.util.Properties;

/**
 * @Desc NacosStarterListener
 * @Author Zheng.LiMing
 * @Date 2020/2/24
 */
public class NacosStarterListener implements MacosXListener {
    @Override
    public void notice() {
        BeanManager beanManager = new BeanManager();
        NacosConfig nacosConfig= null;
        try {
            ApplicationENV env = (ApplicationENV) beanManager.getBean(null,ApplicationENV.class);
            nacosConfig = (NacosConfig) beanManager.getBean(null,NacosConfig.class);
            NacosConfigService nacosConfigService =  NacosConfigService.getNaocsConfigService();
            nacosConfigService.init(nacosConfig);
            Properties properties = nacosConfigService.doPullConfig();
            env.addElementsByPropreties(properties);
            nacosConfigService.doListener();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(ApplicationENV evn) {

    }
}
