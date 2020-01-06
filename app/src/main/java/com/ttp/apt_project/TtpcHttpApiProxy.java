package com.ttp.apt_project;

import com.ttp.apt_project.bean.ChapterResult;
import com.ttp.apt_project.bean.ListProjectResult;
import com.ttp.apt_project.service.WanAndroidService;
import com.ttp.http.HttpManager;
import com.ttp.http.HttpTask;

import java.util.List;

/**
 * @author faqi.tao
 * @time 2019/12/30
 */
public class TtpcHttpApiProxy implements WanAndroidService {

    public TtpcHttpApiProxy() {
    }

    @Override
    public HttpTask<List<ChapterResult>> getChapterResult() {
        return HttpManager.getHttpService(WanAndroidService.class).getChapterResult();
    }

    @Override
    public HttpTask<List<ListProjectResult>> getListproject() {
        return HttpManager.getHttpService(WanAndroidService.class).getListproject();
    }
}
