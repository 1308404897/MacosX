package com.macos.framework.core.handle;
import com.macos.common.util.ScannerUtil;
import com.macos.framework.annotation.MacosXScanner;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;


/**
 * @Desc 扫描处理器，用于加载Class
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
@Slf4j
@SuppressWarnings("all")
public class MacosXScannerHandler extends BaseHandler {

    public MacosXScannerHandler(){
        handleAnnotations = new Class[]{MacosXScanner.class};
    }

    /**缓存已经执行的class，避免死循环*/
    private static Set<Class> cache = new HashSet<>();

    /**
     * 扫描class，并注册到BeanManager中
     * @param mainClass 程序入口对象
     * @param handleClass 当前处理对象
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public boolean doHandle(Class mainClass,Class handleClass,String[] args) throws Exception {

        if (!needToHandle(handleClass)) {
            return true;
        }

        //如果处理对象已经被处理过，则跳过
        if (cache.contains(handleClass)){
            return true;
        }
        cache.add(handleClass);
        //获取处理对象的扫描路径
        String[] basePath = getScannerPath(handleClass);
        if (basePath==null){
            return true;
        }
        if (doBefore(mainClass,handleClass,args)){
            Set<Class> result = ScannerUtil.scanner(basePath, null);
            if (result.size() > 0) {
                BeanManager.registerClassBySet(result);
            }
        }
        return doAfter(mainClass,handleClass,args);
    }

    /**
     * 获取扫描路径
     * @param c
     * @return
     * @throws Exception
     */
    private String[] getScannerPath(Class c) throws Exception {
        if (c.isAnnotationPresent(MacosXScanner.class)) {
            MacosXScanner macosXScanner = (MacosXScanner) c.getAnnotation(MacosXScanner.class);
            String[] packageNames = macosXScanner.packageNames();
            if (packageNames.length == 0) {
                log.error("扫描路径不允许为空或\"  \"");
                throw new Exception("扫描路径不允许为空或\"  \"");
            }
            return packageNames;
        }
        return null;
    }

}
