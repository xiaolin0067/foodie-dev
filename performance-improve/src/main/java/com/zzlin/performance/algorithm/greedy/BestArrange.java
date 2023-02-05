package com.zzlin.performance.algorithm.greedy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 贪心
 * 安排最多的的会议
 *
 * @author zlin
 * @date 20230205
 */
public class BestArrange {

    /**
     * 最多排多少个会议
     *
     * @param programs 会议开始结束时间列表
     * @param timePoint 当天的开始时间
     * @return 最多排多少个会议的数量
     */
    public static int bestArrange(Program[] programs, int timePoint) {
        int result = 0;
        if (programs == null) {
            return result;
        }
        Arrays.sort(programs, new ProgramEndComparator());
        // 依次取结束时间最早的会议
        for (Program program : programs) {
            // 会议开始时间是大于等于当前时间点的才能排上
            if (timePoint <= program.getStart()) {
                result++;
                timePoint = program.getEnd();
            }
        }
        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Program {
        private int start;
        private int end;
    }

    static class ProgramEndComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.getEnd() - o2.getEnd();
        }
    }

}
