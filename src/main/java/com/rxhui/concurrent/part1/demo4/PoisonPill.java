package com.rxhui.concurrent.part1.demo4;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * createed By xiaoqiang
 * 2019/7/8 14:10
 */
public class PoisonPill {

	public static void main(String[] args) {
		File file = new File("D:\\test1/");
		IndexingService c = new IndexingService(file);
		c.start();
		try {
			TimeUnit.MICROSECONDS.sleep(100);// 停止ＸＸ时间，显示出消费速度慢于生产速度　
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.stop();
	}
}
