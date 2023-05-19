package com.daishuai.juc.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Daishuai
 * @version 1.0.0
 * @description CLH锁其实就是一种是基于逻辑队列非线程饥饿的一种自旋公平锁，由于是 Craig、Landin 和 Hagersten三位大佬的发明，因此命名为CLH锁。
 * @createTime 2023年05月18日 10:13:00
 */
public class CLHLock {

    private final AtomicReference<Node> tail;

    private final ThreadLocal<Node> cur;

    private final ThreadLocal<Node> pre;


    public CLHLock() {
        tail = new AtomicReference<Node>(new Node());
        cur = new ThreadLocal<Node>() {
            @Override
            protected Node initialValue() {
                return new Node();
            }
        };
        pre = new ThreadLocal<Node>();
    }

    public void lock() {
        Node currentNode = cur.get();
        currentNode.locked = true;
        Node preNo = tail.getAndSet(currentNode);
        this.pre.set(preNo);
        while (preNo.locked) {
            System.out.println("线程" + Thread.currentThread().getName() + "没有获取锁, 正在自旋等待");
        }
        System.out.println("线程" + Thread.currentThread().getName() + "成功获取锁!!!");
    }

    public void unlock() {
        Node currentNode = cur.get();
        currentNode.locked = false;
        System.out.println("线程" + Thread.currentThread().getName() + "释放了锁");
        cur.set(pre.get());
    }

    private static class Node {
        /**
         * 锁状态: 默认false, 表示线程没有获取到锁; true表示线程获取到锁或正在等待
         * 为了保证locked状态是线程间可见的, 因此用volatile关键字修饰1
         */
        volatile boolean locked = false;
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        a = b = a;
        System.out.println(a);
        System.out.println(b);
    }
}
