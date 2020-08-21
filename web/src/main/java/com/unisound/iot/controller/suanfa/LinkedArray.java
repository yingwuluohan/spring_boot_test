package com.unisound.iot.controller.suanfa;

/**
 * @Created by yingwuluohan on 2020/3/23.
 */
public class LinkedArray<T extends Number > {

    //链表的头节点
    private Entry<T> head;

    //节点实体类
    static final class Entry<T>{
        //记录当前节点的下一个节点
        Entry<T> next;
        T t;
        public Entry(T t) {
            // TODO Auto-generated constructor stub
            this.t = t;
        }
    }

    public void add(T t) {
        //根据参数创建一个节点
        Entry<T> entry = new Entry<T>(t);
        //当链表为空时，记录head节点
        if(head == null) {
            head = entry;
        }else {
            //节点移动，并且赋值，保持head节点为新增加的entry
            entry.next = head;
            head = entry;
        }
    }

    public void reverse() {
        ///记录current的节点是head大的下一个节点。
        Entry<T> current = head.next;

        //切断head.next指向current，（当前的head变为链表的尾，所以next为空）
        head.next = null;
        while(current != null) {
            //记录currentNext的节点是currentNext大的下一个节点。
            Entry<T> currentNext = current.next;
            //current.next反方向指向以前的节点
            current.next = head;
            //移动head和current指针，到后面head重新成为头节点
            head = current;
            current = currentNext;
        }
    }
    public  void addByOrder(T t) {
        //根据参数创建一个节点
        Entry<T> entry = new Entry<T>(t);
        //当链表为空时，记录head节点
        if(head == null) {
            head = entry;
        }else {
            //从head开始，记录上个节点，
            //current是当前节点，previous为current的上一个节点
            Entry<T> current = head;
            Entry<T> previous = head;

            //遍历整条链表，直到当前current节点为空(链表已经到尾部)
            //或者插入的树数值比当前current大
            while(current != null && t.doubleValue() >current.t.doubleValue()) {
                //节点移动
                previous = current;
                current = current.next;
            }

            //当前链表只有head一个节点，
            //并且传入大的数值比head小(因为current == previous，current节点并没有移动)
            if(current == previous) {
                entry.next = head;
                head = entry;
            }else {
                //找到节点的位置，在previous和current的中间插入
                previous.next = entry;
                entry.next = current;
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stubw
        LinkedArray linkedArray = new LinkedArray();
        int i = 1;
        linkedArray.add( i );
        System.out.println("-------------分割线-----------");
        linkedArray.addByOrder( i );
    }

    private static void add1() {
        LinkedArray<Number> list = new LinkedArray<>();
        list.add(3);
        list.add(4);
        list.add(8);
        list.add(6);
        list.add(9);
        list.add(2);
        list.add(1);
        list.add(5);
        list.add(0);
        list.add(7);

        list.reverse();
    }






















}
