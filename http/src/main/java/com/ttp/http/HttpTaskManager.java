package com.ttp.http;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * FileName: HttpTaskManager
 * Author: zhihao.wu@ttpai.cn
 * Date: 2019/5/29
 * Description:
 */
public class HttpTaskManager<T> {
    private static final String TAG = "HttpTaskManager";
    public static HttpTaskManager sInstance;

    /**
     * 后台http任务专用code,请确保在请求回调中不会操作ui，否则可能会出现空指针或内存泄露
     * 适用于：如一个上传任务，但是上传完不需要更新Ui。
     */
    public static final int CODE_BACKGROUND_TASK = -1;
    //任务管理集合,code为key,一个key可以有多个请求任务
    private Map<Integer, List<ITask>> mMap;

    private HttpTaskManager() {
        mMap = new ConcurrentHashMap<>();
    }

    public static HttpTaskManager getInstance() {
        if (sInstance == null) {
            synchronized (HttpTaskManager.class) {
                if (sInstance == null)
                    sInstance = new HttpTaskManager();
            }
        }
        return sInstance;
    }

    /**
     * 添加 任务 进 指定code组
     *
     * @param code 一般为activity,fragment 对象的hasCode
     */
    public void addHttpTask(int code, ITask task) {
        if (task == null)
            return;
        List<ITask> list = mMap.get(code);
        if (list == null) {
            list = new CopyOnWriteArrayList<>();
            mMap.put(code, list);
        }
        list.add(task);
    }

    /**
     * 取消目标下所有任务，一般放在baseActivity onDestroy中
     *
     * @param target
     */
    public void cancelByTarget(Object target) {
        if (target == null)
            return;
        cancelByCode(target.hashCode());
    }

    /**
     * 取消code下所有任务
     *
     * @param code
     */
    public void cancelByCode(int code) {
        List<ITask> list = mMap.get(code);
        cancelList(list);
    }

    /**
     * 此方法不会取消请求
     */
    public void removeHttpTask(int code, ITask task) {
        if (task == null)
            return;
        List<ITask> list = mMap.get(code);
        if (list != null) {
            list.remove(task);
        }
    }

    private void cancelList(List<ITask> list) {
        if (list == null || list.size() <= 0)
            return;
        for (int i = 0; i < list.size(); i++) {
            ITask task = list.remove(i);
            task.cancel();
            i--;
        }
    }

    /**
     * 取消所有任务，不包含 后台任务
     */
    public void cancelAllHttpTask() {
        Set<Integer> codes = mMap.keySet();
        List<ITask> backs = mMap.get(CODE_BACKGROUND_TASK);
        for (Integer code : codes) {
            if (code != CODE_BACKGROUND_TASK)
                cancelByCode(code);
        }
        mMap.clear();
        mMap.put(CODE_BACKGROUND_TASK, backs);
    }

    /**
     * 取消所有任务，包含 后台任务
     */
    public void cancelAllWithBackgroundHttpTask() {
        Set<Integer> codes = mMap.keySet();
        for (Integer code : codes) {
            cancelByCode(code);
        }
        mMap.clear();
    }

}
