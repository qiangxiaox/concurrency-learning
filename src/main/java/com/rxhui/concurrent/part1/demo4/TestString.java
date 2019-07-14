package com.rxhui.concurrent.part1.demo4;

/**
 * @Description
 * @Author xiaoqiang
 * @Date 2019/7/2 9:58
 */
public class TestString {
	public static void main(String[] args) {
		String str = "lll";
		String a = "lll";
		String str2 = a + "";
		System.out.println(str == str2);
	}



	/*public static void main(java.lang.String[]);
		Code:
			0: ldc           #2                  // String lll
			2: astore_1
			3: ldc           #2                  // String lll
			5: astore_2
			6: new           #3                  // class java/lang/StringBuilder
			9: dup
			10: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
			13: aload_2
			14: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
			17: ldc           #6                  // String
			19: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
			22: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
			25: astore_3
			26: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
			29: aload_1
			30: aload_3
			31: if_acmpne     38
			34: iconst_1
			35: goto          39
			38: iconst_0
			39: invokevirtual #9                  // Method java/io/PrintStream.println:(Z)V
			42: return
	}*/
}
