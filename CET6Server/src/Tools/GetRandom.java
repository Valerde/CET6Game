package Tools;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @className: GetRandom
 * @description:
 * @author: ykn
 * @date: 2022/5/16
 **/
public class GetRandom {
    private static Random rd = new Random();

    public static double getRandom(double range){
        return rd.nextDouble()+0.02;
    }

    /**
     * @param: len
     * @description: 获得一个随机数
     * @return: java.lang.Integer
     * @author: ykn
     * @time: 2022/5/4 19:17
     */
    public static Integer getRandom(int range) {

        return rd.nextInt(range);
    }

    /**
     * @param: range
     * @param: len
     * @description: 获取一组不重合的随机数, range为范围, len为长度
     * @return: java.util.List<java.lang.Integer>
     * @author: ykn
     * @time: 2022/5/16 18:43
     */
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
