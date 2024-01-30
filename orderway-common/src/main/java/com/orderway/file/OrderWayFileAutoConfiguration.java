package com.orderway.file;///*

// */
//package com.orderway.file;
//
//import com.orderway.file.api.FileOperatorApi;
//import com.orderway.file.api.expander.FileConfigExpander;
//import com.orderway.file.api.pojo.props.LocalFileProperties;
//import com.orderway.file.sdk.local.LocalFileOperator;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 文件的自动配置
// *
// * @author fengshuonan
// * @date 2020/12/1 14:34
// */
//@Configuration
//public class OrderWayFileAutoConfiguration {
//
//    /**
//     * 本地文件操作
//     *
//     * @author fengshuonan
//     * @date 2020/12/1 14:40
//     */
//    @Bean
//    @ConditionalOnMissingBean(FileOperatorApi.class)
//    public FileOperatorApi fileOperatorApi() {
//
//        LocalFileProperties localFileProperties = new LocalFileProperties();
//
//        // 从系统配置表中读取配置
//        localFileProperties.setLocalFileSavePathLinux(FileConfigExpander.getLocalFileSavePathLinux());
//        localFileProperties.setLocalFileSavePathWin(FileConfigExpander.getLocalFileSavePathWindows());
//
//        return new LocalFileOperator(localFileProperties);
//    }
//
//}
