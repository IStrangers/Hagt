package com.hagt.uitl;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //合并排序
    public static class Merge
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
            branch(array);
            return array;
        }

        public static <T> void branch(T[] array)
        {
            int arrayLength = array.length;


        }

        public static void main(String[] args) {
            int [] arr = new int[]{165,2,1615,6,3516,516,5156,11,56,56};
            for (int i = 0 ; i < arr.length >> 1 ; i++ )
            {
                int arrOneIdx = i;
                int arrTowIdx = i << 1 + 1;
                int arrOneMaxIdx = arrOneIdx << 1;
                int judgeValue = arr[arrOneIdx];
                while (arrOneMaxIdx > arrOneIdx)
                {
                    if (judgeValue > arr[arrTowIdx])
                    {
                        int temp = arr[arrOneIdx];
                        arr[arrOneIdx] = arr[arrTowIdx];
                        arr[arrTowIdx] = temp;
                        arrOneIdx++;
                    }else{
                        arr[arrOneIdx] = judgeValue;
                        arrOneIdx++;
                    }
                }

            }
        }

        public static void mergeSort()
        {
            int [] arr = new int[]{165,2,1615,6,3516,516,5156,11,56,56};
            for (int i = 0 ; i < arr.length >> 1 ; i++ )
            {
                int arrOneIdx = i;
                int arrTowIdx = i << 1 + 1;
                int arrOneMaxIdx = arrOneIdx << 1;
                int arrTowMaxIdx = arrTowIdx << 1;
                while (arrOneMaxIdx < arrOneIdx && arrTowMaxIdx < arrTowIdx )
                {
                    if (arr[arrOneIdx] > arr[arrTowIdx])
                    {
                        int temp = arr[arrOneIdx];
                        arr[arrOneIdx] = arr[arrTowIdx];
                        arr[arrTowIdx] = temp;
                        arrOneIdx++;
                    }else{
                        arrTowMaxIdx++;
                    }
                }
            }
        }
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
