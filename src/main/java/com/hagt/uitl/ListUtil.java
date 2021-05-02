package com.hagt.uitl;

import java.util.List;

public class ListUtil {

    public static byte[] listToArray(List<Byte> list)
    {
        if (JudgeUtil.isNull(list))
        {
            return null;
        }
        byte [] result = new byte[list.size()];
        int index = 0;
        for (byte b : list)
        {
            result[index++] = b;
        }
        return result;
    }
}
