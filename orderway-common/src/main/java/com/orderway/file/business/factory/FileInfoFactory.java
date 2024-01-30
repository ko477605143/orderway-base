package com.orderway.file.business.factory;///*

// */
//package com.orderway.file.business.factory;
//
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.NumberUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.extra.spring.SpringUtil;
//import com.orderway.file.api.FileOperatorApi;
//import com.orderway.file.api.enums.FileLocationEnum;
//import com.orderway.file.api.enums.FileStatusEnum;
//import com.orderway.file.api.exception.FileException;
//import com.orderway.file.api.exception.enums.FileExceptionEnum;
//import com.orderway.file.api.expander.FileConfigExpander;
//import com.orderway.file.api.pojo.request.SysFileInfoRequest;
//import com.orderway.file.business.entity.SysFileInfo;
//import com.orderway.file.business.service.SysFileStorageService;
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//
//import static com.orderway.file.api.constants.FileConstants.FILE_POSTFIX_SEPARATOR;
//
///**
// * 文件信息组装工厂
// *
// * @author fengshuonan
// * @date 2020/12/30 22:16
// */
//public class FileInfoFactory {
//
//    /**
//     * 封装附件信息
//     *
//     * @author majianguo
//     * @date 2020/12/27 12:55
//     */
//    public static SysFileInfo createSysFileInfo(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {
//
//        FileOperatorApi fileOperatorApi = SpringUtil.getBean(FileOperatorApi.class);
//        SysFileStorageService fileStorageService = SpringUtil.getBean(SysFileStorageService.class);
//
//        // 生成文件的唯一id
//        Long fileId = IdWorker.getId();
//
//        // 获取文件原始名称
//        String originalFilename = file.getOriginalFilename();
//
//        // 获取文件后缀（不包含点）
//        String fileSuffix = null;
//        if (ObjectUtil.isNotEmpty(originalFilename)) {
//            fileSuffix = StrUtil.subAfter(originalFilename, FILE_POSTFIX_SEPARATOR, true);
//        }
//
//        // 生成文件的最终名称
//        String finalFileName = fileId + FILE_POSTFIX_SEPARATOR + fileSuffix;
//
//        // 桶名
//        String fileBucket = FileConfigExpander.getDefaultBucket();
//
//        // 存储文件
//        byte[] bytes;
//        try {
//            bytes = file.getBytes();
//            if (StrUtil.isNotEmpty(sysFileInfoRequest.getFileBucket())) {
//                fileBucket = sysFileInfoRequest.getFileBucket();
//            }
//            // 如果是存在数据库库里，单独处理一下
//            if (FileLocationEnum.DB.getCode().equals(sysFileInfoRequest.getFileLocation())) {
//                fileStorageService.saveFile(fileId, bytes);
//            } else {
//                fileOperatorApi.storageFile(fileBucket, finalFileName, bytes);
//            }
//        } catch (IOException e) {
//            throw new FileException(FileExceptionEnum.ERROR_FILE, e.getMessage());
//        }
//
//        // 计算文件大小kb
//        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024)).setScale(0, BigDecimal.ROUND_HALF_UP));
//
//        // 计算文件大小信息
//        String fileSizeInfo = FileUtil.readableFileSize(file.getSize());
//
//        // 封装存储文件信息（上传替换公共信息）
//        SysFileInfo sysFileInfo = new SysFileInfo();
//        sysFileInfo.setFileId(fileId);
//        // 如果是存在数据库库里，单独处理一下
//        if (FileLocationEnum.DB.getCode().equals(sysFileInfoRequest.getFileLocation())) {
//            sysFileInfo.setFileLocation(FileLocationEnum.DB.getCode());
//        } else {
//            sysFileInfo.setFileLocation(fileOperatorApi.getFileLocationEnum().getCode());
//        }
//        sysFileInfo.setFileBucket(fileBucket);
//        sysFileInfo.setFileObjectName(finalFileName);
//        sysFileInfo.setFileOriginName(originalFilename);
//        sysFileInfo.setFileSuffix(fileSuffix);
//        sysFileInfo.setFileSizeKb(fileSizeKb);
//        sysFileInfo.setFileSizeInfo(fileSizeInfo);
//        sysFileInfo.setFileStatus(FileStatusEnum.NEW.getCode());
//        sysFileInfo.setSecretFlag(sysFileInfoRequest.getSecretFlag());
//
//        // 返回结果
//        return sysFileInfo;
//    }
//
//}
