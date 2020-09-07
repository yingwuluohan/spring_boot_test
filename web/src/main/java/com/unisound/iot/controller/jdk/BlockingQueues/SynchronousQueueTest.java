package com.unisound.iot.controller.jdk.BlockingQueues;

import java.util.concurrent.SynchronousQueue;

/**
 * @Created by yingwuluohan on 2020/3/19.
 * SynchronousQueue是一个没有数据缓冲的BlockingQueue，
 * 生产者线程对其的插入操作put必须等待消费者的移除操作take，反过来也一样
 * SynchronousQueue内部并没有数据缓存空间，你不能调用peek()方法来看队列中是否有数据元素，
 * 因为数据元素只有当你试着取走的时候才可能存在，
 * 不取走而只想偷窥一下是不行的，当然遍历这个队列的操作也是不允许的。
 * 队列头元素是第一个排队要插入数据的线程，而不是要交换的数据。
 * 数据是在配对的生产者和消费者线程之间直接传递的，并不会将数据缓冲数据到队列中。
 * 可以这样来理解：生产者和消费者互相等待对方，握手，然后一起离开
 */
public class SynchronousQueueTest {

    private static SynchronousQueue synchronousQueue = new SynchronousQueue( false);


    public static void main(String[] args) {
        for( int i =0;i < 1 ;i++ ){
            try {
                synchronousQueue.put( i + "queue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("长度:"+ synchronousQueue.size() );
    }




}
