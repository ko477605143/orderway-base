/*
create by orderway   add time  20220909
 */
package com.orderway.scanner;

import cn.hutool.core.util.ObjectUtil;
import com.orderway.rule.listener.ApplicationReadyListener;
import com.orderway.scanner.api.DevOpsReportApi;
import com.orderway.scanner.api.ResourceCollectorApi;
import com.orderway.scanner.api.ResourceReportApi;
import com.orderway.scanner.api.constants.ScannerConstants;
import com.orderway.scanner.api.holder.InitScanFlagHolder;
import com.orderway.scanner.api.pojo.devops.DevOpsReportProperties;
import com.orderway.scanner.api.pojo.resource.ReportResourceParam;
import com.orderway.scanner.api.pojo.resource.ResourceDefinition;
import com.orderway.scanner.api.pojo.resource.SysResourcePersistencePojo;
import com.orderway.scanner.api.pojo.scanner.ScannerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.Map;

/**
 * 监听项目初始化完毕，汇报资源到服务（可为远程服务，可为本服务）
 *
 * @author fengshuonan
 * @date 2020/10/19 22:27
 */
@Slf4j
public class ResourceReportListener extends ApplicationReadyListener implements Ordered {

    @Override
    public void eventCallback(ApplicationReadyEvent event) {

        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        // 获取有没有开资源扫描开关
        ScannerProperties scannerProperties = applicationContext.getBean(ScannerProperties.class);
        if (!scannerProperties.getOpen()) {
            // 设置已经扫描标识
            InitScanFlagHolder.setFlag();
            return;
        }

        // 如果项目还没进行资源扫描
        if (!InitScanFlagHolder.getFlag()) {

            // 获取当前系统的所有资源
            ResourceCollectorApi resourceCollectorApi = applicationContext.getBean(ResourceCollectorApi.class);
            Map<String, Map<String, ResourceDefinition>> modularResources = resourceCollectorApi.getModularResources();

            // 持久化资源，发送资源到资源服务或本项目
            ResourceReportApi resourceService = applicationContext.getBean(ResourceReportApi.class);
            List<SysResourcePersistencePojo> persistencePojos = resourceService.reportResourcesAndGetResult(new ReportResourceParam(scannerProperties.getAppCode(), modularResources));

            // 向DevOps一体化平台汇报资源
            DevOpsReportProperties devOpsReportProperties = applicationContext.getBean(DevOpsReportProperties.class);
            // 如果配置了相关属性则进行DevOps资源汇报
            if (ObjectUtil.isAllNotEmpty(devOpsReportProperties,
                    devOpsReportProperties.getServerHost(),
                    devOpsReportProperties.getProjectInteractionSecretKey(),
                    devOpsReportProperties.getProjectUniqueCode(),
                    devOpsReportProperties.getServerHost())) {
                DevOpsReportApi devOpsReportApi = applicationContext.getBean(DevOpsReportApi.class);
                try {
                    devOpsReportApi.reportResources(devOpsReportProperties, persistencePojos);
                } catch (Exception e) {
                    log.error("向DevOps平台汇报异常出现网络错误，如无法联通DevOps平台可关闭相关配置。", e);
                }
            }

            // 设置标识已经扫描过
            InitScanFlagHolder.setFlag();
        }

    }

    @Override
    public int getOrder() {
        return ScannerConstants.REPORT_RESOURCE_LISTENER_SORT;
    }

}
