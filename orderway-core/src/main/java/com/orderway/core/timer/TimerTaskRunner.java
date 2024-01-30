/*
create by   orderway   add time 20220909
 */
package com.orderway.core.timer;

/**
 * 定时器执行者
 * <p>
 * Guns中定时器都要实现本接口，并需要把实现类加入到spring容器中
 *
 * @author oderway
 * @date 2020/6/28 21:28
 */
public interface TimerTaskRunner {

    /**
     * 任务执行的具体内容
     *
     * @author oderway
     * @date 2020/6/28 21:29
     */
    void action();

}
