package com.rxhui.concurrent.part1.design_pattern;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * createed By xiaoqiang
 * 2019/7/14 18:34
 */
public class TestLowerCaseInput {
	public static void main(String[] args) {
		int c;
		try {
			InputStream in = new LowerCaseInputStream(
					new BufferedInputStream(
							new FileInputStream("E:\\test.txt")));
			while ((c = in.read()) >= 0){
				System.out.print((char) c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
