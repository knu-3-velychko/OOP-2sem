package com.knu.task9.queue;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

class Node<T> {
    private static final sun.misc.Unsafe UNSAFE;
    private static final long itemOffset;
    private static final long nextOffset;

    static {
        try {
            Constructor<Unsafe> con = sun.misc.Unsafe.class.getDeclaredConstructor();
            con.setAccessible(true);
            UNSAFE = con.newInstance(null);
            Class<Node> k = Node.class;
            itemOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("value"));
            nextOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("next"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    volatile T value;
    volatile Node<T> next;

    Node(T value) {
        UNSAFE.putObject(this, itemOffset, value);
    }

    boolean getItem(T oldItem, T it) {
        return UNSAFE.compareAndSwapObject(this, itemOffset, oldItem, it);
    }

    boolean next(Node<T> oldNext, Node<T> n) {
        return UNSAFE.compareAndSwapObject(this, nextOffset, oldNext, n);
    }

    public void standardNext(Node<T> h) {
        UNSAFE.putOrderedObject(this, nextOffset, h);
    }
}