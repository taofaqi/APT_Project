package com.ttp.http;

import java.util.ArrayList;

/**
 * @author faqi.tao
 * @time 2020/1/13
 */
public class ChainManager implements IBaseTask {

    ArrayList<IBaseTask> iBaseTasks = new ArrayList<>();

    public void addTask(IBaseTask iBaseTask) {
        iBaseTasks.add(iBaseTask);
    }

    private int index;

    @Override
    public void doRealAction(String isTask, IBaseTask iBaseTask) {
        if (iBaseTasks.isEmpty()) {
            return;
        }
        if (index >= iBaseTasks.size()) {
            return;
        }

        index++;

        IBaseTask baseTask = iBaseTasks.get(index);
        baseTask.doRealAction(isTask, iBaseTask);

    }
}
