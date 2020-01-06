package com.ttp.http.config;

import android.util.Log;


/**
 * <li>Package:com.ttp.http_core </li>
 * <li>Author: yhj </li>
 * <li>Date: 2017/2/15 </li>
 * <li>Description: 打印日志 </li>
 */
public class BlueLog {
    private final static String AppName = "AutoChecker";
    private static boolean IsDebug = true; //控制开关

    public static void e(String tag, String logMsg) {
        if (IsDebug) {
            if (null == logMsg) {
                Log.e(tag, "No error message got,considering NullPointerException");
                return;
            }
            int length = logMsg.length();
            int offset = 3000;
            if (length > offset) {
                int num = 0;
                for (int i = 0; i < length; i += offset) {
                    num += offset;
                    if (num > length)
                        num = length;
                    Log.e(AppName, "<<" + tag + ">> " + logMsg.substring(i, num));
                }
            } else {
                Log.e(AppName, "<<" + tag + ">> " + logMsg);

            }
        }
    }

    public static void e(String tag, String logMsg, Throwable throwable) {
        if (IsDebug && throwable != null) {
            Log.e(AppName, tag + logMsg, throwable);
        }
    }

    public static void d(String tag, String logMsg) {
        if (IsDebug) {
            Log.d(AppName, tag + logMsg);
        }
    }

    public static void d(String tag, String logMsg, Throwable throwable) {
        if (IsDebug && throwable != null) {
            Log.d(AppName, tag + logMsg, throwable);
        }
    }

    public static void i(String tag, String logMsg) {
        if (IsDebug) {
            Log.i(AppName, tag + logMsg);
        }
    }

    public static void i(String tag, String logMsg, Throwable throwable) {
        if (IsDebug && throwable != null) {
            Log.i(AppName, tag + logMsg, throwable);
        }
    }

    public static void w(String tag, String logMsg) {
        if (IsDebug) {
            Log.w(AppName, tag + logMsg);
        }
    }

    public static void w(String tag, String logMsg, Throwable throwable) {
        if (IsDebug && throwable != null) {
            Log.w(AppName, tag + logMsg, throwable);
        }
    }

    public static void v(String tag, String logMsg) {
        if (IsDebug) {
            Log.v(AppName, tag + logMsg);
        }
    }

    public static void v(String tag, String logMsg, Throwable throwable) {
        if (IsDebug && throwable != null) {
            Log.v(AppName, tag + logMsg, throwable);
        }
    }

    public static void out(Object mObject) {
        if (IsDebug) {
            System.out.println(mObject);
        }
    }

    public static void err(Object mObject) {
        if (IsDebug) {
            System.err.println(mObject);
        }
    }

    public static void printStackTrace(Throwable throwable) {
        if (IsDebug && throwable != null) {
            throwable.printStackTrace();
        }
    }

    public static void setIsDebug(boolean isDebug) {
        IsDebug = isDebug;
    }
}
