package com.ttp.apt_project.service;

import com.ttp.annotation.Aspect;
import com.ttp.apt_project.bean.UserResult;
import com.ttp.http.HttpTask;

import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * @author faqi.tao
 * @time 2020/1/2
 */
@Aspect
public interface WanServicePost {

    @POST("/user/login")
    HttpTask<UserResult> login(@Field("username") String username, @Field("password")String password);
}
