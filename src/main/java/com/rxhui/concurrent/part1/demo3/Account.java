package com.rxhui.concurrent.part1.demo3;

/**
 * 用不同的锁对受保护资源进行精细化管理，能够提升性能。这种锁还有个名字，叫细粒度锁。
 */
class AccountA {
	// 锁：保护账户余额
	private final Object balLock = new Object();
	// 账户余额
	private Integer balance;
	// 锁：保护账户密码
	private final Object pwLock = new Object();
	// 账户密码
	private String password;

	// 取款
	void withdraw(Integer amt) {
		synchronized (balLock) {
			if (this.balance > amt) {
				this.balance -= amt;
			}
		}
	}

	// 查看余额
	Integer getBalance() {
		synchronized (balLock) {
			return balance;
		}
	}

	// 更改密码
	void updatePassword(String pw) {
		synchronized (pwLock) {
			this.password = pw;
		}
	}

	// 查看密码
	String getPassword() {
		synchronized (pwLock) {
			return password;
		}
	}
}

/**
 * 用一把锁synchronized(this) 锁住两个资源，所有操作串行化
 */
class AccountB {
	// 账户余额
	private Integer balance;
	// 账户密码
	private String password;

	// 取款
	synchronized void withdraw(Integer amt) {
		if (this.balance > amt) {
			this.balance -= amt;
		}
	}

	// 查看余额
	synchronized Integer getBalance() {
		return balance;
	}

	// 更改密码
	synchronized void updatePassword(String pw) {
		this.password = pw;
	}

	// 查看密码
	synchronized String getPassword() {
		return password;
	}
}


