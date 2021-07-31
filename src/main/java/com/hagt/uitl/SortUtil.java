package com.hagt.uitl;

import hagtMvc.entity.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;
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
            return sort(array,SortUtil::fromBigAndEqualToSmall);
        }

        public static <T> T[] sort(T[] array,BiFunction<T,T,Boolean> biFunction)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            branchMergeSort(array,0,array.length - 1,array.clone(),biFunction);
            return array;
        }

        public static <T> void branchMergeSort(T[] array,int left,int right,T[] tempArray,BiFunction<T,T,Boolean> biFunction)
        {
            if (left < right)
            {
                int midIndex = (left + right) >> 1;
                branchMergeSort(array,left,midIndex,tempArray,biFunction);
                branchMergeSort(array,midIndex + 1,right,tempArray,biFunction);
                mergeSort(array,left,midIndex,right,tempArray,biFunction);
            }
        }

        public static <T> void mergeSort(T[] array,int left,int midIndex,int right,T[] tempArray,BiFunction<T,T,Boolean> biFunction)
        {
            int l = left;
            int mid = midIndex + 1;
            int t = 0;

            while (l <= midIndex && mid <= right)
            {
                //array[l] <= array[mid]
                if (biFunction.apply(array[l],array[mid]))
                {
                    tempArray[t++] = array[l++];
                } else {
                    tempArray[t++] = array[mid++];
                }
            }

            while(l <= midIndex){
                tempArray[t++] = array[l++];
            }

            while(mid <= right){
                tempArray[t++] = array[mid++];
            }

            t = 0;
            while(left <= right){
                array[left++] = tempArray[t++];
            }
        }

    }

    public static class Quick
    {

    }

    public static class Bucket
    {

        public static <T extends BucketSortITF> T[] sort(T[] array,BiFunction<T,T,Boolean> biFunction)
        {
            if ( isEmptyArray(array) )
            {
                return array;
            }
            Integer max = Stream.of(array).mapToInt(e -> ((BucketSortITF) e).SortV().intValue()).max().getAsInt();
            Integer min = Stream.of(array).mapToInt(e -> ((BucketSortITF) e).SortV().intValue()).min().getAsInt();
            BigDecimal bucketLength = new BigDecimal((max - min) / array.length + 1);
            BucketNode<T>[] bucketArray = new BucketNode[bucketLength.intValue()];
            for (T t : array) {
                BucketSortITF bucketSortITF = (BucketSortITF) t;
                int bucketIndex = bucketSortITF.SortV().divide(bucketLength,BigDecimal.ROUND_HALF_UP).intValue();
                if (bucketArray[bucketIndex] == null){
                    bucketArray[bucketIndex] = new BucketNode(null,t,null);
                }else{
                    BucketNode<T> bucketNode = bucketArray[bucketIndex];
                    while (bucketNode != null){
                        T bucketNodeV = bucketNode.getV();
                        if (biFunction.apply(bucketNodeV,t)){
                            BucketNode<T> prv = bucketNode.getPrv();
                            BucketNode<T> insertBucketNode = new BucketNode(prv,t, bucketNode);
                            bucketNode.setPrv(insertBucketNode);
                            if (prv == null){
                                bucketArray[bucketIndex] = insertBucketNode;
                                break;
                            }else{
                                prv.setNext(insertBucketNode);
                            }
                        }
                        bucketNode = bucketNode.getNext();
                    }
                }
            }
            int index = 0;
            for (BucketNode<T> bucketNode : bucketArray){
                if (bucketNode == null){
                    continue;
                }
                array[index++] = bucketNode.getV();
                BucketNode<T> nextNode = bucketNode.getNext();
                while (nextNode != null){
                    array[index++] = nextNode.getV();
                    nextNode = nextNode.getNext();
                }
            }
            return array;
        }

        public interface BucketSortITF {
            BigDecimal SortV();
        }

        public static class BucketNode<T>{
            private BucketNode<T> prv;
            private T v;
            private BucketNode<T> next;

            public BucketNode(BucketNode<T> prvV,T v,BucketNode<T> nextV){
                this.prv = prvV;
                this.v = v;
                this.next = nextV;
            }

            public T getV() {
                return v;
            }

            public void setV(T v) {
                this.v = v;
            }

            public BucketNode<T> getPrv() {
                return prv;
            }

            public void setPrv(BucketNode<T> prv) {
                this.prv = prv;
            }

            public BucketNode<T> getNext() {
                return next;
            }

            public void setNext(BucketNode<T> nextV) {
                this.next = nextV;
            }
        }
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

    public static Boolean fromSmallAndEqualToLarge(Integer arr1,Integer arr2)
    {
        return arr1 >= arr2;
    }

    public static Boolean fromBigAndEqualToSmall(Integer arr1,Integer arr2)
    {
        return arr1 <= arr2;
    }
}
