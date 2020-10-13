package com.xuegao.rocketmqconsumer.nonannotation;

import java.lang.reflect.Field;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation
 * <br/> @ClassName：改桃花
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/13 15:28
 */
public class 改桃花 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        小咸鱼 小咸鱼 = new 小咸鱼();
        Field xiaohua = 小咸鱼.getClass().getDeclaredField("xiaohua");
        System.out.println(小咸鱼);
        xiaohua.setAccessible(true);
        xiaohua.set(小咸鱼, "小花是炮友");
        System.out.println(xiaohua.get(小咸鱼));
    }
}
class 小咸鱼 {
    private final String xiaohua = "小花是好朋友";

    @Override
    public String toString() {
        return "小咸鱼{" +
                "xiaohua='" + xiaohua + '\'' +
                '}';
    }
}