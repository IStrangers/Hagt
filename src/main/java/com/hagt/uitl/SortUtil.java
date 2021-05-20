package com.hagt.uitl;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SortUtil
{

    /**
     * 冒泡排序
     */
    public static class Bubble
    {
        public static int[] sort(int[] array)
        {
            Integer[] integerArray = Arrays.stream(array).boxed().toArray(Integer[]::new);
            if ( isEmptyArray(integerArray) )
            {
                return array;
            }
            int[] intArray = Arrays.stream(sort(integerArray)).mapToInt(Integer::intValue).toArray();
            return intArray;
        }

        public static Integer[] sort(Integer[] array)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            return sort(array,SortUtil::fromSmallToLarge);
        }

        public static <T> T[] sort(T[] array, BiFunction<T,T,Boolean> biFunction)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            for ( int i = 0 ; i < array.length ; i++ )
            {
                for ( int o = 0 ; o < array.length - i - 1; o++ )
                {
                    int sufO = o + 1;
                    if ( biFunction.apply(array[o],array[sufO]) )
                    {
                        T temp = array[o];
                        array[o] = array[sufO];
                        array[sufO] = temp;
                    }
                }
            }
            return array;
        }
    }

    /***
     * 选择排序
     */
    public static class Selection
    {
        public static int[] sort(int[] array)
        {
            Integer[] integerArray = Arrays.stream(array).boxed().toArray(Integer[]::new);
            if ( isEmptyArray(integerArray) )
            {
                return array;
            }
            int[] intArray = Arrays.stream(sort(integerArray)).mapToInt(Integer::intValue).toArray();
            return intArray;
        }

        public static Integer[] sort(Integer[] array)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            return sort(array,SortUtil::fromBigToSmall);
        }

        public static <T> T[] sort(T[] array, BiFunction<T,T,Boolean> biFunction)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            for ( int i = 0 ; i < array.length ; i++ )
            {
                for ( int o = 0 ; o < array.length ; o++ )
                {
                    if ( biFunction.apply(array[i],array[o]) )
                    {
                        T temp = array[i];
                        array[i] = array[o];
                        array[o] = temp;
                    }
                }
            }
            return array;
        }
    }

    /**
     * 插入排序
     */
    public static class Insertion
    {
        public static int[] sort(int[] array)
        {
            Integer[] integerArray = Arrays.stream(array).boxed().toArray(Integer[]::new);
            if ( isEmptyArray(integerArray) )
            {
                return array;
            }
            int[] intArray = Arrays.stream(sort(integerArray)).mapToInt(Integer::intValue).toArray();
            return intArray;
        }

        public static Integer[] sort(Integer[] array)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            return sort(array,SortUtil::fromSmallToLarge);
        }

        public static <T> T[] sort(T[] array, BiFunction<T,T,Boolean> biFunction)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            for ( int i = 0 ; i < array.length - 1; i++ )
            {
                int p = i ;
                T v = array[p + 1];
                for ( ; p >= 0 && biFunction.apply(array[p],v) ; p-- )
                {
                    array[p + 1] = array[p];
                }
                array[p + 1] = v;
            }
            return array;
        }
    }

    /**
     * 希尔排序
     */
    public static class Shell
    {
        public static int[] sort(int[] array)
        {
            Integer[] integerArray = Arrays.stream(array).boxed().toArray(Integer[]::new);
            if ( isEmptyArray(integerArray) )
            {
                return array;
            }
            int[] intArray = Arrays.stream(sort(integerArray)).mapToInt(Integer::intValue).toArray();
            return intArray;
        }

        public static Integer[] sort(Integer[] array)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            return sort(array,SortUtil::fromSmallToLarge);
        }

        public static <T> T[] sort(T[] array, BiFunction<T,T,Boolean> biFunction)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            for ( int i = array.length >> 1 ; i > 0 ; i >>= 1 )
            {
                for ( int o = i ; o < array.length ; o++ )
                {
                    int p = o - i ;
                    T v = array[o];
                    for ( ; p >= 0 && biFunction.apply(array[p],v) ; p -= i )
                    {
                        array[p + i] = array[p];
                    }
                    array[p + i] = v;
                }
            }
            return array;
        }
    }

    public static class Merge
    {

    }

    public static class Quick
    {

    }

    public static class Bucket
    {

    }

    public static class Counting
    {

    }

    public static class Radix
    {

    }

    public static class Heap
    {

    }

    public static <T> boolean isEmptyArray(T[] array)
    {
        if ( array == null || array.length <= 0 )
        {
            return true;
        }
        return false;
    }

    public static Boolean fromSmallToLarge(Integer arr1,Integer arr2)
    {
        return arr1 > arr2;
    }

    public static Boolean fromBigToSmall(Integer arr1,Integer arr2)
    {
        return arr1 < arr2;
    }
}
