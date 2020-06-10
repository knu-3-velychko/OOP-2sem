package com.knu.task9.queue;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

public class CasQueue<T> {
    private static final sun.misc.Unsafe UNSAFE;
    private static final long tailOffset;
    private static final long headOffset;

    static {
        try {
            Constructor<Unsafe> con = Unsafe.class.getDeclaredConstructor();
            con.setAccessible(true);
            UNSAFE = con.newInstance(null);
            Class<CasQueue> k = CasQueue.class;
            tailOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("tail"));
            headOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("head"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public CasQueue() {
        head = tail = new Node<T>(null);
    }

    private static void checkNotNull(Object item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    public boolean add(T item) {
        checkNotNull(item);
        Node<T> node = new Node<T>(item);

        for (Node<T> t = tail, p = t; ; ) {
            Node<T> q = p.next;
            if (q == null) {
                if (p.next(null, node)) {
                    if (p != t)
                        getTail(t, node);
                    return true;
                }
            } else if (q == p)
                p = (t != (t = tail)) ? t : head;
            else
                p = (t != (t = tail)) ? t : q;
        }
    }

    public void print() {
        Node<T> node = head;
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }

    public T get() {
        restartFromHead:
        while (true) {
            for (Node<T> h = head, p = h, q; ; ) {
                T item = p.value;
                if (item != null && p.getItem(item, null)) {
                    if (p != h)
                        updateHead(h, ((q = p.next) != null ? q : p));
                    return item;
                } else if ((q = p.next) == null) {
                    updateHead(h, p);
                    return null;
                } else if (q == p)
                    continue restartFromHead;
                else
                    p = q;
            }
        }
    }

    private void updateHead(Node<T> h, Node<T> p) {
        if (h != p && getHead(h, p)) {
            h.standardNext(h);
        }
    }

    private boolean getHead(Node<T> h, Node<T> p) {
        return UNSAFE.compareAndSwapObject(this, headOffset, h, p);
    }

    private boolean getTail(Node<T> t, Node<T> node) {
        return UNSAFE.compareAndSwapObject(this, tailOffset, t, node);
    }

}