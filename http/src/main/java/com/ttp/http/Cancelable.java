package com.ttp.http;

/**
 * FileName: Cancelable
 * Author: zhihao.wu@ttpai.cn
 * Date: 2019/7/29
 * Description: 继承此接口，表明对象可取消
 */
public interface Cancelable {
    /**
     * 取消此任务
     */
    void cancel();

    /**
     * 检查此任务是否被取消
     * @return
     */
    boolean isCancel();
}
