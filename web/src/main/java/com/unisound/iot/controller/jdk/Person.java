package com.unisound.iot.controller.jdk;

public class Person {

    private String name;
    private int age;
    private String address;

    public static int num;

    public Person(String name , String address , int age ){
        this.name = name;
        this.address = address;
        this.age = age;

        num++;
    }


    public static void main( String[] args ){
        int a = 10;
        Person p1 = new Person( "" , "" , 1 );
        Person p2 = new Person( "" , "" , 2 );
        Person p3 = new Person( "" , "" , 3 );
        change( p1 );
        System.out.println( "result:" + p1.getAge()  );
    }


    public static void change( Person person ){
//        person = new Person( "" , "" ,23 );

        person.setAge( 11 );

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
/**
 * 堆：对象
 栈：局部变量及基本数据类型的变量
 代码区：类和方法
 数据区：常量池和静态变量
 */
