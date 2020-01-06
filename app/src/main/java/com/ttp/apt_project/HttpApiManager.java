package com.ttp.apt_project;

import com.ttp.apt_project.service.WanAndroidService;
import com.ttp.apt_project.service.WanServicePost;

/**
 * @author faqi.tao
 * @time 2019/12/30
 */
public class HttpApiManager {
    private static TtpcHttpApiProxy sInstance;

    private HttpApiManager() {
    }

    public static <T extends WanAndroidService & WanServicePost> T getService() {
        if (sInstance == null) {
            synchronized (HttpApiManager.class) {
                if (sInstance == null) {
                    sInstance = new TtpcHttpApiProxy();
                }
            }
        }
        return (T) sInstance;
    }
}
