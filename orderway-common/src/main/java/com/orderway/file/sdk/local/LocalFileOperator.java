/*
create by orderway   add time  20220909
 */
package com.orderway.file.sdk.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
//import com.orderway.auth.api.context.LoginContext;
import com.orderway.file.api.FileOperatorApi;
import com.orderway.file.api.constants.FileConstants;
import com.orderway.file.api.enums.BucketAuthEnum;
import com.orderway.file.api.enums.FileLocationEnum;
import com.orderway.file.api.exception.FileException;
import com.orderway.file.api.exception.enums.FileExceptionEnum;
import com.orderway.file.api.expander.FileConfigExpander;
import com.orderway.file.api.pojo.props.LocalFileProperties;
//import com.orderway.base.util.HttpServletUtil;

import java.io.File;
import java.io.InputStream;

/**
 * 本地文件的操作
 *
 * @author fengshuonan
 * @date 2020/10/26 13:38
 */
public class LocalFileOperator implements FileOperatorApi {

    private final LocalFileProperties localFileProperties;

    private String currentSavePath = "";

    public LocalFileOperator(LocalFileProperties localFileProperties) {
        this.localFileProperties = localFileProperties;
        initClient();
    }

    @Override
    public void initClient() {
        if (SystemUtil.getOsInfo().isWindows()) {
            String savePathWindows = localFileProperties.getLocalFileSavePathWin();
            if (!FileUtil.exist(savePathWindows)) {
                FileUtil.mkdir(savePathWindows);
            }
            currentSavePath = savePathWindows;
        } else {
            String savePathLinux = localFileProperties.getLocalFileSavePathLinux();
            if (!FileUtil.exist(savePathLinux)) {
                FileUtil.mkdir(savePathLinux);
            }
            currentSavePath = savePathLinux;
        }
    }

    @Override
    public void destroyClient() {
        // empty
    }

    @Override
    public Object getClient() {
        // empty
        return null;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        String absolutePath = currentSavePath + File.separator + bucketName;
        return FileUtil.exist(absolutePath);
    }

    @Override
    public void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum) {
        // empty
    }

    @Override
    public boolean isExistingFile(String bucketName, String key) {
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        return FileUtil.exist(absoluteFile);
    }

    @Override
    public void storageFile(String bucketName, String key, byte[] bytes) {

        // 判断bucket存在不存在
        String bucketPath = currentSavePath + File.separator + bucketName;
        if (!FileUtil.exist(bucketPath)) {
            FileUtil.mkdir(bucketPath);
        }

        // 存储文件
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        FileUtil.writeBytes(bytes, absoluteFile);
    }

    @Override
    public void storageFile(String bucketName, String key, InputStream inputStream) {

        // 判断bucket存在不存在
        String bucketPath = currentSavePath + File.separator + bucketName;
        if (!FileUtil.exist(bucketPath)) {
            FileUtil.mkdir(bucketPath);
        }

        // 存储文件
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        FileUtil.writeFromStream(inputStream, absoluteFile);
    }

    @Override
    public byte[] getFileBytes(String bucketName, String key) {

        // 判断文件存在不存在
        String absoluteFile = currentSavePath + File.separator + bucketName + File.separator + key;
        if (!FileUtil.exist(absoluteFile)) {
            // 组装返回信息
            String errorMessage = StrUtil.format("bucket={},key={}", bucketName, key);
            throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
        } else {
            return FileUtil.readBytes(absoluteFile);
        }
    }

    @Override
    public void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum) {
        // empty
    }

    @Override
    public void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey) {

        // 判断文件存在不存在
        String originFile = currentSavePath + File.separator + originBucketName + File.separator + originFileKey;
        if (!FileUtil.exist(originFile)) {
            // 组装返回信息
            String errorMessage = StrUtil.format("bucket={},key={}", originBucketName, originFileKey);
            throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
        } else {

            // 拷贝文件
            String destFile = currentSavePath + File.separator + newBucketName + File.separator + newFileKey;
            FileUtil.copy(originFile, destFile, true);
        }
    }

    @Override
    public String getFileAuthUrl(String bucketName, String key, Long timeoutMillis) {

        // 获取登录用户的token
       String token = "";//LoginContext.me().getToken();

        // 获取context-path
        String contextPath = "";//HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PREVIEW_BY_OBJECT_NAME + "?fileBucket=" + bucketName + "&fileObjectName=" + key + "&token=" + token;
    }

    @Override
    public String getFileUnAuthUrl(String bucketName, String key) {
        // 获取context-path
        String contextPath ="";// HttpServletUtil.getRequest().getContextPath();

        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PREVIEW_BY_OBJECT_NAME + "?fileBucket=" + bucketName + "&fileObjectName=" + key;
    }

    @Override
    public void deleteFile(String bucketName, String key) {

        // 判断文件存在不存在
        String file = currentSavePath + File.separator + bucketName + File.separator + key;
        if (!FileUtil.exist(file)) {
            return;
        }

        // 删除文件
        FileUtil.del(file);

    }

    @Override
    public FileLocationEnum getFileLocationEnum() {
        return FileLocationEnum.LOCAL;
    }
    
}
