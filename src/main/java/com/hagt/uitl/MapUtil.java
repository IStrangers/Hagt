package com.hagt.uitl;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public static <T1,T2> Map<T1,T2> copyMap(Map<T1,T2> map)
    {
        HashMap<T1, T2> newMap = new HashMap<>();
        for (Map.Entry<T1,T2> entry : map.entrySet())
        {
            newMap.put(entry.getKey(),entry.getValue());
        }
        return newMap;
    }
}
