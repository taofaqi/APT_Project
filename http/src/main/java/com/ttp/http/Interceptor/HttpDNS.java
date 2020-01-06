package com.ttp.http.Interceptor;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;

/**
 * @author faqi.tao
 * @time 2019/12/27
 */
public class HttpDNS implements Dns {
    @NotNull
    @Override
    public List<InetAddress> lookup(@NotNull String hostname) throws UnknownHostException {

//        返回系统解析的ip
        return SYSTEM.lookup(hostname);
    }
}
