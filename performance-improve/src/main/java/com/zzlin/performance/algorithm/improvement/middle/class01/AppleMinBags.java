package com.zzlin.performance.algorithm.improvement.middle.class01;

/**
 * 小虎去附近的商店买苹果，奸诈的商贩使用了捆绑交易，只提供6个每袋和8个每袋的包装包装不可拆分。
 * 可是小虎现在只想购买恰好n个苹果，小虎想购买尽量少的袋数方便携带。如果不能购买恰好n个苹果，小虎将不会购买。
 * 输入一个整数n，表示小虎想购买的个苹果，返回最小使用多少袋子。如果无论如何都不能正好装下，返回-1。
 *
 * @author lin
 * @date 2023/8/20
 */
public class AppleMinBags {

    public static int minBags(int appleNums) {
        if (appleNums < 0) {
            return -1;
        }
        int bag6 = -1;
        int bag8 = appleNums / 8;
        int rest = appleNums % 8;
        // 大于24个的后续都是不用在尝试使用6来代替的，因为8是可以的
        while (rest >=0 && rest < 24) {
            int restBag6 = minBagBase6(rest);
            if (restBag6 != -1) {
                bag6 = restBag6;
                break;
            }
            rest = appleNums - 8 * (--bag8);
        }
        return bag6 == -1 ? -1 : bag6 + bag8;
    }

    private static int minBagBase6(int rest) {
        return rest % 6 == 0 ? (rest / 6) : -1;
    }

    /**
     * 根据第一种解决方案找规律
     *
     * @param appleNums
     * @return
     */
    public static int minBagAwesome(int appleNums) {
        if ((appleNums & 1) != 0) {
            return -1;
        }
        if (appleNums < 18) {
            return appleNums == 0 ? 0 :
                    (appleNums == 6 || appleNums == 8) ? 1 :
                            (appleNums == 12 || appleNums == 14 || appleNums == 16) ? 2 : -1;
        }
        return (appleNums - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(i + " : " + minBags(i) + " _ " + minBagAwesome(i));
        }
    }

}
