package Tools;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * description: 获取随机数,通过一个重载的方法允许获得一组随机数或一个int 或 double的随机数
 * @className: GetRandom
 * @author: ykn
 * @date: 2022/5/16
 **/
public class GetRandom {
    private static Random rd = new Random();

    public static double getRandom(double range) {
        return rd.nextDouble() + 0.02;
    }

    public static Integer getRandom(int range) {
        return rd.nextInt(range);
    }


    public static List<Integer> getArrayListRandom(int range, int len) {
        Set<Integer> hs = new HashSet<>();
        while (true) {
            int tmp = rd.nextInt(range);
            hs.add(tmp);
            if (hs.size() == len)
                break;
        }
        return hs.stream().toList();
    }
}
