package ykn.sovava.Tools;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * description: 获取随机数
 * @className: GetRandom
 * @author: ykn
 * @date: 2022/5/20
 **/
public class GetRandom {
    private static Random rd = new Random();

    public static double getRandom(double range) {
        return rd.nextDouble() + 0.02;
    }


    public static Integer getRandom(int range) {
        return rd.nextInt(range);
    }

    public static Integer getRandom(int left, int right) {
        return rd.nextInt(right - left) + left + 1;
    }


}