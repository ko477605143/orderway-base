/*
create by orderway   add time  20220909
 */
package com.orderway.file.api;

import com.orderway.file.api.pojo.AntdvFileInfo;
import com.orderway.file.api.pojo.response.SysFileInfoResponse;

/**
 * 获取文件信息的api
 *
 * @author fengshuonan
 * @date 2020/11/29 16:21
 */
public interface FileInfoApi {

    /**
     * 获取文件详情
     *
     * @param fileId 文件id，在文件信息表的id
     * @return 文件的信息，不包含文件本身的字节信息
     * @author fengshuonan
     * @date 2020/11/29 16:26
     */
    SysFileInfoResponse getFileInfoWithoutContent(Long fileId);

    /**
     * 获取文件的下载地址（带鉴权的），生成外网地址
     *
     * @param fileId 文件id
     * @return 外部系统可以直接访问的url
     * @author fengshuonan
     * @date 2020/10/26 10:40
     */
    String getFileAuthUrl(Long fileId);

    /**
     * 获取文件的下载地址（带鉴权的），生成外网地址
     *
     * @param fileId 文件id
     * @param token  用户的token
     * @return 外部系统可以直接访问的url
     * @author fengshuonan
     * @date 2020/10/26 10:40
     */
    String getFileAuthUrl(Long fileId, String token);

    /**
     * 获取文件的下载地址（不带鉴权的），生成外网地址
     *
     * @param fileId 文件id
     * @return 外部系统可以直接访问的url
     * @author fengshuonan
     * @date 2020/10/26 10:40
     */
    String getFileUnAuthUrl(Long fileId);

    /**
     * 获取AntdV组件格式对应的文件信息封装
     *
     * @author fengshuonan
     * @date 2022/3/28 14:32
     */
    AntdvFileInfo buildAntdvFileInfo(Long fileId);

    /**
     * 真实删除文件
     *
     * @author fengshuonan
     * @date 2022/7/22 23:19
     */
    void removeFile(Long fileId);

}
