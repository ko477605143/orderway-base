package com.orderway.auth.bus.modular.data;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.orderway.auth.bus.core.cache.AppCache;
import com.orderway.auth.bus.core.jwt.TokenUtils;
import com.orderway.auth.bus.modular.data.mapper.DataHandlerMapper;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.pojo.response.ResponseData;
import com.orderway.core.pojo.response.SuccessResponseData;

import com.orderway.core.util.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(value = "app_key/app_id/app_token等相关的接口", tags = "app_key/app_id/app_token等相关的接口")
@RestController
@RequestMapping("/handler/app")
public class AppController {


    @Resource
    private AppCache appCache;

    @Resource
    private DataHandlerMapper dataHandlerMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AppHandler appHandler;

    /**
     * {
     * "appId": "11",
     * "appKey": "22"
     * }
     *
     * @return
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据appid/appkey返回token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "appId，必填", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "appKey", value = "appId，必填", dataType = "string", paramType = "query", required = true),
    })
    @GetMapping("/getToken")
    public ResponseData getToken(@RequestParam(name = "appId", defaultValue = "", required = true) String appId,
                                 @RequestParam(name = "appKey", defaultValue = "", required = true) String appKey) {
        String token = "";

//        String appId = arg.get("appId") == null ? "" : arg.get("appId").toString().trim();
//        String appKey = arg.get("appKey") == null ? "" : arg.get("appKey").toString().trim();

        if (appId == null || appId.trim().equals("") || appKey == null || appKey.trim().equals("")) {
            throw new HandlerException(AppHandlerExceptionEnum.PARAM_ERR);
        }

        //查询数据库
        List<Map> r = dataHandlerMapper.sel_data_by_appid_key(appId, appKey);
        if (r == null || r.size() <= 0) {
            throw new HandlerException(AppHandlerExceptionEnum.PARAM_ERR);
        }

        token = "app_" + tokenUtils.generateToken(null);

        Long expire_time = HandlerCommon.getPcTokenExpire();
        SysLoginUser s_tmp = new SysLoginUser();
        s_tmp.setClientType("app");
        appCache.put(token, appId + "_" + appKey, expire_time);

        //更新数据库
        dataHandlerMapper.up_app_token(appId, appKey, token);

        return new SuccessResponseData(token);
    }


    /**
     * {
     * "token": "app_005a0ee0051c44b4915d26db0a284602"
     * }
     *
     * @return
     * @throws IOException
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "验证token是否正常")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "获取的token", dataType = "string", paramType = "query", required = true),
    })
    @GetMapping("/checkToken")
    public ResponseData checkToken(@RequestParam(name = "token", defaultValue = "", required = true) String token) throws IOException {


        Map r = appHandler.check_app_token_by_token(token);
        if (ObjectUtil.isEmpty(r)) {
            throw new HandlerException(AppHandlerExceptionEnum.TOKEN_ERR);
        } else {
            return new SuccessResponseData(r);
        }

        // ResponseUtil.responseExceptionError(response, 4444, "app_token验证失败", "app_token验证失败");;

    }


    /**
     * {
     * "token": "app_005a0ee0051c44b4915d26db0a284602"
     * }
     *
     * @return
     * @throws IOException
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取app配置")
    @GetMapping("/getAppUrl")
    public ResponseData getAppUrl(){
        return new SuccessResponseData(appHandler.get_app_name_url());
    }
}
