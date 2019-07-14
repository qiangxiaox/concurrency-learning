package com.rxhui.concurrent.part1.demo4;

/**
 * @Description
 * @Author xiaoqiang
 * @Date 2019/7/2 10:01
 */
public class TestString2 {

	public static void main(String[] args) {
		String str = "lll";
		String a = "lll";
		String str2 = "lll" + "";
		System.out.println(str == str2);
	}


	/*public class TestString2 {
		public TestString2();
		Code:
		0: aload_0
		1: invokespecial #1                  // Method java/lang/Object."<init>":()V
		4: return

		public static void main(java.lang.String[]);
		Code:
		0: ldc           #2                  // String lll
		2: astore_1
		3: ldc           #2                  // String lll
		5: astore_2
		6: ldc           #2                  // String lll
		8: astore_3
		9: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
		12: aload_1
		13: aload_3
		14: if_acmpne     21
		17: iconst_1
		18: goto          22
		21: iconst_0
		22: invokevirtual #4                  // Method java/io/PrintStream.println:(Z)V
		25: return
	}*/
}
