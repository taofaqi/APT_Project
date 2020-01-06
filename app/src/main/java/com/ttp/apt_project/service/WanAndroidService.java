package com.ttp.apt_project.service;

import com.ttp.annotation.Aspect;
import com.ttp.apt_project.bean.ChapterResult;
import com.ttp.apt_project.bean.ListProjectResult;
import com.ttp.http.HttpTask;

import java.util.List;

import retrofit2.http.GET;

/**
 * @author faqi.tao
 * @time 2019/12/27
 */

@Aspect
public interface WanAndroidService {

    //获取公众号列表
    @GET("/wxarticle/chapters/json")
    HttpTask<List<ChapterResult>> getChapterResult();

    @GET("/article/listproject/0/json")
    HttpTask<List<ListProjectResult>> getListproject();
}
