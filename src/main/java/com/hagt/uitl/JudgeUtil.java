package com.hagt.uitl;

import java.util.Arrays;

public class JudgeUtil {

    public static boolean isNull(Object o)
    {
        return  o == null;
    }

    public static boolean isNotNull(Object o)
    {
        return  o != null;
    }

    public static boolean isNotNull(String v)
    {
        if (v == null)
        {
            return false;
        }
        if ("".equals(v.trim()))
        {
            return false;
        }
        return true;
    }

    public static boolean isFileString(String v)
    {
        return "file".equals(v);
    }

    public static boolean isNull(String v)
    {
        if (v == null)
        {
            return true;
        }
        if ("".equals(v.trim()))
        {
            return true;
        }
        return false;
    }

    public static boolean isEquals(Object o1,Object o2)
    {
        return o1 == o2;
    }

    public static boolean isEmptyArray(Object v)
    {
        if (v == null)
        {
            return true;
        }
        if (!v.getClass().isArray())
        {
            return true;
        }
        if (Arrays.asList(v).size() <= 0)
        {
            return true;
        }
        return false;
    }

}

