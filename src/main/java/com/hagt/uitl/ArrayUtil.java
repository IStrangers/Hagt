package com.hagt.uitl;

public class ArrayUtil {

    public static  <T> T[] concat(T[] first, T[] second) {
        T[] newV = (T[]) new Object[first.length + second.length];
        System.arraycopy(first, 0, newV, 0, first.length);
        System.arraycopy(second, 0, newV, first.length, second.length);
        return newV;
    }
}
