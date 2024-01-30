package com.orderway.file.business.service.impl;//package com.orderway.file.business.service.impl;
//
//import com.orderway.auth.api.context.LoginContext;
//import com.orderway.file.api.constants.FileConstants;
//import com.orderway.file.api.expander.FileConfigExpander;
//import com.orderway.file.business.entity.SysFileStorage;
//import com.orderway.file.business.mapper.SysFileStorageMapper;
//import com.orderway.file.business.service.SysFileStorageService;
//import com.orderway.base.util.HttpServletUtil;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.stereotype.Service;
//
///**
// * 文件存储信息业务实现层
// *
// * @author fengshuonan
// * @date 2022/01/08 15:53
// */
//@Service
//public class SysFileStorageServiceImpl extends ServiceImpl<SysFileStorageMapper, SysFileStorage> implements SysFileStorageService {
//
//    @Override
//    public void saveFile(Long fileId, byte[] bytes) {
//        SysFileStorage sysFileStorage = new SysFileStorage();
//        sysFileStorage.setFileId(fileId);
//        sysFileStorage.setFileBytes(bytes);
//        this.save(sysFileStorage);
//    }
//
//    @Override
//    public String getFileAuthUrl(String fileId) {
//        // 获取登录用户的token
//        String token = LoginContext.me().getToken();
//        // 获取context-path
//        String contextPath = HttpServletUtil.getRequest().getContextPath();
//        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PRIVATE_PREVIEW_URL + "?fileId=" + fileId + "&token=" + token;
//    }
//
//    @Override
//    public String getFileUnAuthUrl(String fileId) {
//        // 获取context-path
//        String contextPath = HttpServletUtil.getRequest().getContextPath();
//        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PUBLIC_PREVIEW_URL + "?fileId=" + fileId;
//    }
//
//}