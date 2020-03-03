package com.ttp.http;

/**
 * @author faqi.tao
 * @time 2020/1/13
 */
public class Task1 implements IBaseTask{
    @Override
    public void doRealAction(String isTask, IBaseTask iBaseTask) {
        if ("no".equals(isTask)){
            System.out.println("任务节点一  。。。。");
        }else {
            iBaseTask.doRealAction(isTask,iBaseTask);
        }
    }
}
