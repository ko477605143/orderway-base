/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.emp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import com.orderway.system.bus.modular.auth.util.IDCommon;
import com.orderway.system.bus.modular.emp.entity.SysEmpPos;
import com.orderway.system.bus.modular.emp.mapper.SysEmpPosMapper;
import com.orderway.system.bus.modular.emp.service.SysEmpPosService;
import com.orderway.system.bus.modular.pos.entity.SysPos;
import com.orderway.system.bus.modular.pos.service.SysPosService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工职位service接口实现类
 *
 * @author oderway
 * @date 2020/3/13 15:11
 */
@Service
public class SysEmpPosServiceImpl extends ServiceImpl<SysEmpPosMapper, SysEmpPos> implements SysEmpPosService {

    @Resource
    private SysPosService sysPosService;

    /**
     * 职位id参数键
     */
    private static final String POS_ID_DICT_KEY = "posId";

    /**
     * 职位编码参数键
     */
    private static final String POS_CODE_DICT_KEY = "posCode";

    /**
     * 职位名称参数键
     */
    private static final String POS_NAME_DICT_KEY = "posName";

    @Override
    public void addOrEdit(Long empId, List<Long> posIdList) {
        LambdaQueryWrapper<SysEmpPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpPos::getEmpId, empId);

        //删除职位信息
        this.remove(queryWrapper);

        //保存职位信息
        posIdList.forEach(posId -> {
            SysEmpPos sysEmpPos = new SysEmpPos();
            sysEmpPos.setEmpId(empId);
            // Convert.toLong postId 转为换Long
            sysEmpPos.setPosId(Convert.toLong(posId));
            this.save(sysEmpPos);
        });
    }

    @Override
    public List<Dict> getEmpPosDictList(Long empId, boolean isFillId) {
        List<Dict> dictList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysEmpPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpPos::getEmpId, empId);

        this.list(queryWrapper).forEach(sysEmpPos -> {
            Dict dict = Dict.create();
            Long posId = sysEmpPos.getPosId();
            SysPos sysPos = sysPosService.getById(posId);
            if (isFillId) {
                dict.put(POS_ID_DICT_KEY, posId);
            }
            dict.put(POS_CODE_DICT_KEY, sysPos.getCode());
            dict.put(POS_NAME_DICT_KEY, sysPos.getName());
            dictList.add(dict);
        });
        return dictList;
    }

    @Override
    public boolean hasPosEmp(Long posId) {
        LambdaQueryWrapper<SysEmpPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpPos::getPosId, posId);
        List<SysEmpPos> list = this.list(queryWrapper);
        return list.size() != 0;
    }

    @Override
    public void deleteEmpPosInfoByUserId(Long empId) {
        LambdaQueryWrapper<SysEmpPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpPos::getEmpId, empId);
        this.remove(queryWrapper);
    }

    /**
     * 关联或修改员工职位信息
     * @param list 职位信息集合
     * @param empId 员工id
     */
    @Override
    public void addOrEditByList(List<Long> list, Long empId) {
        // 先删除员工职位信息
        this.deleteEmpPosInfoByUserId(empId);
        list.forEach(i ->{
            Long uuid = IDCommon.getUUID();
            this.baseMapper.addList(uuid,empId,i);
        });

    }
}
