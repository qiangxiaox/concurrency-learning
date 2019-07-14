package com.rxhui.concurrent.part1.demo3;

/**
 * @Description
 * @Author xiaoqiang
 * @Date 2019/6/20 18:24
 */
public class AccountC {

	private int balance;

	// 转账
	void transfer(
			AccountC target, int amt) {
		if (this.balance > amt) {
			this.balance -= amt;
			target.balance += amt;
		}
	}

	// 转账, this这把锁并不能保证target.balance的资源
	synchronized void transferSync(
			AccountC target, int amt) {
		if (this.balance > amt) {
			this.balance -= amt;
			target.balance += amt;
		}
	}
}

/**
 * 多个对象共用一把锁，锁资源
 * 如果创建 Account 对象时，传入的 lock 不是同一个对象，如果创建 Account 对象时，
 * 传入的 lock 不是同一个对象，那可就惨了，会出现锁自家门来保护他家资产的荒唐事
 */
class AccountD {
	private Object lock;
	private int balance;
	private AccountD(){};
	// 创建 Account 时传入同一个 lock 对象
	public AccountD(Object lock) {
		this.lock = lock;
	}
	// 转账
	void transfer(AccountD target, int amt){
		// 此处检查所有对象共享的锁
		synchronized(lock) {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}

/**
 * 用 Account.class 作为共享的锁。Account.class 是所有 Account 对象共享的，
 * 而且这个对象是 Java 虚拟机在加载 Account 类的时候创建的，所以我们不用担心它的唯一性。
 */
class AccountE {
	private int balance;
	// 转账
	void transfer(AccountE target, int amt){
		synchronized(AccountE.class) {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}
