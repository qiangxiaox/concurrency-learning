package com.rxhui.concurrent.part1.demo2;

/**
 * 互斥锁
 * 1、当修饰静态方法的时候，锁定的是当前类的 Class 对象，，在例子中就是 Class X；
 * 2、当修饰非静态方法的时候，锁定的是当前实例对象 this。
 */
class X {
	// 修饰非静态方法
	synchronized void foo() {
		// 临界区
	}
	// 修饰非静态方法
  /*synchronized(this) void fooSame(){
    //临界区
  }*/


	// 修饰静态方法
	synchronized static void bar() {
		// 临界区
	}
	// 修饰静态方法
  /*synchronized(X.class) static void barsame(){
    // 临界区
  }*/


	// 修饰代码块
	Object obj = new Object();

	void baz() {
		synchronized (obj) {
			// 临界区
		}
	}
}  
