package com.unisound.iot.controller.paixu;

import java.util.Arrays;

public class kuaisu {
    public static void main(String[] args) {
        int[] array = getArray(); //生成一个有10个元素的随机数组
        quickSort(array,0,array.length-1);// 调用快速排序的方法
        System.out.println(Arrays.toString(array)); //显示快排结果

    }

    private static void quickSort(int[] array, int leftBound, int rightBound) {
        if (leftBound >rightBound) { // 确定好排序范围，否则会报数组越界异常
            return;
        }
        int i = leftBound; // 定义数组最左侧的浮标i
        int j = rightBound;  // 定义数组最右侧的浮标j
        int pivot = array[leftBound]; // 声明一个pivot作为基准数  ，将数组最左边的元素赋给这个基准数
        while (i != j) { // 如果两个浮标i和j不相等的情况下
            while (i<j && array[j] >= pivot) {  //始终注意不要引起数组索引越界，如果array[j] 大于等于基准数，array[j]上的值保持不动，但浮标j向左移一位
                j--; //浮标j左移
            }
            while (i<j && array[i] <= pivot) {//始终注意不要引起数组索引越界，如果array[i] 小于等于基准数，array[i]上的值保持不动，但浮标i向左移一位
                i++; //同理浮标i右移
            }
            swap(array, i, j); //此时交换i和j索引上的数据
        }
        //程序可以来到这里说明i和j此时相等
        //将此时浮标i或j上的数值和基准数进行交换，至此找到了中间的基准索引
        swap(array, leftBound, i);
        //以基准索引作为边界，两侧分别进行递归操作即可
        //比基准小的重新进行排序
        quickSort(array, leftBound, i-1);
        // 比基准大的重新进行排序
        quickSort(array, i+1, rightBound);
    }
    // 这是用于交换的方法
    public static void swap(int[] array, int x, int y) {
        int temp = array[x]; //数组中的第x上的元素赋值给temp
        array[x] = array[y];
        array[y] =temp;

    }
    // 获取一个范围在1-100内的有10个元素的数组
    public static int[] getArray() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*100+1);
        }
        System.out.println("系统自动生成的数组是："+ Arrays.toString(arr));
        return arr;
    }

    /**
     *  12, 1 , 20 , 44, 67 ,11 ,9 ,100 ,23
     * @param array
     * @param begin
     * @param end
     * @return
     */
    public void sort(int[] array , int begin ,int end ){

        int i = begin ;
        int j = end;
        int prrior = array[ j ];
        while( i != j ){

            while ( i <j&& array[ j ] >  prrior ){
                j--;
            }
            while ( i < j && array[ i ] < prrior ){
                i++;
            }
            swap( array , i ,j );

        }
        // i = j
        swap( array , begin ,i );
        sort( array , begin , i -1  );
        sort( array , i + 1 , end );
    }





















}
