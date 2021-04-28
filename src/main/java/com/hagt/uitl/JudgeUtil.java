package com.hagt.uitl;

public class JudgeUtil {

    public static boolean isNull(Object o){
        return  o == null;
    }

    public static boolean isNotNull(Object o){
        return  o != null;
    }

    public static boolean isFileString(String v){
        return "file".equals(v);
    }

    public static boolean isNull(String v){
        return v == null ? true : "".equals(v.trim());
    }

    public static boolean isEquals(Object o1,Object o2){
        return o1 == o2;
    }

    public static boolean isEmptyArray(Object [] v)
    {
        return v == null ? true : v.length > 0;
    }
}
