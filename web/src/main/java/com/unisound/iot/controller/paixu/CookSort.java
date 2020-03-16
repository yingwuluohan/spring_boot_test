package com.unisound.iot.controller.paixu;

import java.util.*;

/**
 * @Created  fn on 2020/3/11.
 * 美团app外卖页面上要显示推荐餐馆列表。每家餐馆都有一个推荐分值，名字和菜系。每页显示5家餐馆，但是为了不让某种菜系霸屏，
 * 要求每页最多显示3家同一菜系的餐馆。
你需要写一个函数，接受一组已根据推荐分值从高到低排序的餐馆，返回一组显示页面，每一页是一组符合要求的推荐餐馆。
要求用java写，写完函数后，写几组测试，并把测试结果记录下来。
示例：[{"IA", "川菜", 98.0}, {"IB", "川菜", 97.0}, {"IC", "川菜", 96.0}, {"ID", "川菜", 95.0}, {"IE", "川菜", 94.0},
{"IF", "川菜", 93.0}, {"IG", "川菜", 92.0}, {"CA", "上海菜", 91.0}, {"CB", "上海菜", 90.0}, {"CC", "上海菜", 89.0},
{"AA", "西餐", 88.0}, {"CD", "西餐", 87.0}, {"AB", "西餐", 86.0}]
 */
public class CookSort {





    public List< List< Cook >> getCookSort(String cookStr ){
        List< List< Cook >> result = new LinkedList<>();
        if( cookStr == null || cookStr.split( "," ).length < 1 ){
            return new ArrayList<>();
        }
        List< Cook > temList = getCookInfo( cookStr );
        resortCook( temList );



        return result;
    }

    //第二次分页重排
    private static List< List< Cook >> resortCook(List< Cook > list ){
        List< List< Cook >> result = new ArrayList<>();
        List< Cook > temp = new ArrayList<>( );
        int yu = list.size()/3;
        int num = list.size() %3 ;
        if( 0 < num){
            yu++;
        }
        int count=0;
        for( int i =0 ;i< list.size();i++ ){

            if( 0 == i % 3 ){
                count++;
                List< Cook > temp2 = new ArrayList<>();
                temp2 = temp;
                if( temp2.size() > 0 ){
                    result.add( temp2 );
                }
                temp = new ArrayList<>( );
            }
            temp.add( list.get( i ) );
            if( count == yu ){
                result.add( temp );
            }
        }
        return result;

    }

    //第一轮按照分数排序
    private static List< Cook > getCookInfo(String cookStr){
        String temp = cookStr.substring( 1 , cookStr.length() - 1 );
         String temp2 = temp.replace( "\"" , "" );
        String[] tempArray = temp2.split( "}," );
        if( tempArray == null || tempArray.length < 1 ){
            return new ArrayList<>();
        }
        List< Cook > tempList = new ArrayList<>( tempArray.length );
        for( int i = 0 ;i < tempArray.length;i++ ){
             if( i != tempArray.length -1 ){
                 tempArray[ i ] = tempArray[ i ].substring( 1 , tempArray[ i ].length() );
             } else if( i == tempArray.length -1 ){
                 tempArray[ i ] = tempArray[ i ].substring( 0 , tempArray[ i ].length()-1 );
             }

            Cook cook = new Cook();
            String[] cookArray = tempArray[i ].split( ",");
            if( null == cookArray || cookArray.length != 3 ){
                continue;
            }
            cook.setName( cookArray[ 0 ] );
            cook.setCook( cookArray[ 1 ]);
            cook.setNum( new Double( cookArray[ 2 ] ) );
            tempList.add( cook );
        }
        //按照分数排序
        Collections.sort(tempList, new Comparator<Cook>(){
            public int compare(Cook c1, Cook c2) {
                //按照Person的年龄进行升序排列
                if(c1.getNum() > c2.getNum()){
                    return 1;
                }
                if(c1.getNum() < c2.getNum()){
                    return 0;
                }
                return -1;
            }
        }.reversed());

        return tempList;

    }





    static class Cook{

        //名称
        private String name;
        //菜系
        private String cook;
        //排序系数
        private double num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCook() {
            return cook;
        }

        public void setCook(String cook) {
            this.cook = cook;
        }

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }
    }

    public static void main(String[] args) {
        String s = "[{\"IA\", \"川菜\", 98.0}, {\"IB\", \"川菜\", 97.0}, {\"IC\", \"川菜\", 96.0}, {\"ID\", \"川菜\", 95.0}, {\"IE\", \"川菜\", 94.0}, {\"IF\", \"川菜\", 93.0}, {\"IG\", \"川菜\", 92.0}, {\"CA\", \"上海菜\", 91.0}, {\"CB\", \"上海菜\", 90.0}, {\"CC\", \"上海菜\", 89.0}, {\"AA\", \"西餐\", 88.0}, {\"CD\", \"西餐\", 87.0}, {\"AB\", \"西餐\", 86.0}]";
        String[] ss = s.split( "}," );
        List< Cook > list = getCookInfo( s);
        resortCook( list );
        System.out.println( ss.length );




    }


}
