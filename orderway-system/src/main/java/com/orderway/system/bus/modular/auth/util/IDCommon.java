package com.orderway.system.bus.modular.auth.util;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 生成主键id （雪花算法）
 */
public class IDCommon {

    public final static IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();

    public static Long getUUID() {
        Long result = null;

        Number id = identifierGenerator.nextId(new Object());
        result = Long.parseLong(id + "");

        return result;
    }

}
