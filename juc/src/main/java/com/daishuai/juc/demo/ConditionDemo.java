package com.daishuai.juc.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Daishuai
 * @version 1.0.0
 * @description condition测试类
 * @createTime 2023年05月19日 13:44:00
 */
public class ConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程一加锁成功");
                System.out.println("线程一执行await被挂起");
                condition.await();
                // condition.signal();
                System.out.println("线程一被唤醒成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("线程一释放锁成功");
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程二加锁成功");
                System.out.println("线程二执行await被挂起");
                condition.await();
                // condition.signal();
                System.out.println("线程二唤醒线程一");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("线程二释放锁成功");
            }
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("线程三加锁成功");
                condition.signal();
                System.out.println("线程三唤醒线程一");
            } finally {
                lock.unlock();
                System.out.println("线程三释放锁成功");
            }
        }).start();
    }
}
