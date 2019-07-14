package com.rxhui.concurrent.part1.demo2;

/**
 * synchronized 使用
 */
class SafeCalc {
	long value = 0L;

	long get() {
		return value;
	}

	/**
	 * 作用等同于synchronized(this),只锁了当前方法，上面的get()方法并没有加锁
	 */
	synchronized void addOne() {
		value += 1;
	}

	/**
	 * 加锁之后才能保证value 的值对 get() 方法是可见的
	 *
	 * @return
	 */
	synchronized long get2() {
		return value;
	}

}

/**
 * 两把锁保护统一资源
 */
class SafeCalc2 {
	volatile static long value = 0L;

	/**
	 * 作用等同于synchronized(this)
	 */
	synchronized long get() {
		return value;
	}

	/**
	 * 作用等同于synchronized(SafeCalc2.class)
	 */
	synchronized static void addOne() {
		value += 1;
	}
}

/**
 * 修饰的并不是统一代码块,无法保证原子性，锁的并不是互斥资源
 */
class SafeCalc3 {
	volatile long value = 0L;

	long get() {
		synchronized (new Object()) {
			return value;
		}
	}

	void addOne() {
		synchronized (new Object()) {
			value += 1;
		}
	}
}

class Test{

	public static void main(String[] args) throws InterruptedException {
		calc();
	}

	public static void calc() throws InterruptedException {
		SafeCalc3 safeCalc = new SafeCalc3();
		for(int x = 0; x < 10 ; x++){
			new Thread(() -> {
				safeCalc.addOne();
				System.out.println(Thread.currentThread().getName() + ":"+safeCalc.get());
			}).start();
		}
	}
}
