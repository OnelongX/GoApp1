package com.ways2u.android.common;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Onelong on 16/3/3.
 */
public class ConfigUtil {
    /**
     *
     * @param mContext 上下文，来区别哪一个activity调用的
     * @param fileName 使用的SharedPreferences的名字
     * @param key SharedPreferences的哪一个字段
     * @return
     */

    public static String getString(Context mContext,String fileName,String key){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        String s=sp.getString(key, null);
        return s;
    }

    public static String getString(Context mContext,String fileName,String key,String defaultValue){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        String s=sp.getString(key, defaultValue);
        return s;
    }

    public static int getInt(Context mContext,String fileName,String key){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        int i=sp.getInt(key, 0);
        return i;
    }

    public static int getInt(Context mContext,String fileName,String key,int defaultValue){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        int i=sp.getInt(key, defaultValue);
        return i;
    }

    public static long getLong(Context mContext,String fileName,String key){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        long i=sp.getLong(key, 0);
        return i;
    }

    public static long getLong(Context mContext,String fileName,String key,long defaultValue){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        long i=sp.getLong(key, defaultValue);
        return i;
    }

    public static float getFloat(Context mContext,String fileName,String key){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        float i=sp.getFloat(key, 0);
        return i;
    }

    public static float getFloat(Context mContext,String fileName,String key,float defaultValue){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        float i=sp.getFloat(key, defaultValue);
        return i;
    }

    public static boolean getBoolean(Context mContext,String fileName,String key){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        boolean i=sp.getBoolean(key, false);
        return i;
    }

    public static boolean getBoolean(Context mContext,String fileName,String key,boolean defaultValue){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        boolean i=sp.getBoolean(key, defaultValue);
        return i;
    }

    public static void set(Context mContext,String fileName,String key,String value){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        sp.edit().putString(key, value).commit();
    }

    public static void set(Context mContext,String fileName,String key,int value){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        sp.edit().putInt(key, value).commit();
    }

    public static void set(Context mContext,String fileName,String key,boolean value){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        sp.edit().putBoolean(key, value).commit();
    }

    public static void set(Context mContext,String fileName,String key,long value){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        sp.edit().putLong(key, value).commit();
    }

    public static void set(Context mContext,String fileName,String key,float value){
        SharedPreferences sp=(SharedPreferences) mContext.getSharedPreferences(fileName, 0);
        sp.edit().putFloat(key, value).commit();
    }

}
