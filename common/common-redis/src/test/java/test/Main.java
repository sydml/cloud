package test;

/**
 * 测试5秒再次尝试方法
 * <p>
 * Created by Yuming-Liu
 * 日期： 2019-03-09
 * 时间： 20:50
 */
public class Main {
    public static void main(String[] args) {
        int i = 1;
        while (true) {
            System.out.println("in :" + i);
            i++;
            if (i % 2 == 0) {
                try {
                    Thread.sleep(5000);
                    int a = 1 / 0;
                } catch (Exception e) {
                    System.out.println("error");
                }
                System.out.println("continue");
                continue;
            }
            System.out.println("out.if");
        }
    }
}
