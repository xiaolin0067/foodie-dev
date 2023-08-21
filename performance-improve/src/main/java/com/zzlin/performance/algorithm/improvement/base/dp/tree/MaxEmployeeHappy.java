package com.zzlin.performance.algorithm.improvement.base.dp.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 派对的最大快乐值
 * 员工信息的定义如下:
 * class Employee {
 * public int happy;//这名员工可以带来的快乐值
 * List<Employee> subordinates;// 这名员工有哪些直接下级
 * }
 * 公司的每个员工都符合Emplovee类的描述。整个公司的人员结构可以看作是一棵标准的、没有环的多叉树。树的头节点是公司唯一的老板。
 * 除老板之外的每个员工都有唯一的直接上级。叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。
 * 这个公司现在要办party，你可以决定哪些员工来，哪些员工不来。但是要遵循如下规则：
 * 1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 * 2.派对的整体快乐值是所有到场员工快乐值的累加
 * 3.你的目标是让派对的整体快乐值尽量大
 * 给定一棵多叉树的头节点boss，请返回派对的最大快乐值
 * <p>
 * X来
 * X的快乐值 + 子a不来的快乐值 + 子b不来的快乐值 + ...
 * X不来
 * 子a来与不来的快乐值取最大值 + 子b来与不来的快乐值取最大值 + ...
 *
 * @author zlin
 * @date 20230430
 */
public class MaxEmployeeHappy {

    public static int getEmployeeTreeMaxHappy(Employee employee) {
        EmployeeInfo info = process(employee);
        return Math.max(info.getComeHappy(), info.getNotComeHappy());
    }

    private static EmployeeInfo process(Employee employee) {
        if (employee == null) {
            return new EmployeeInfo(0, 0);
        }
        if (employee.getSubordinates() == null || employee.getSubordinates().isEmpty()) {
            return new EmployeeInfo(employee.getHappy(), 0);
        }
        // x来
        int comeHappy = employee.getHappy();
        // x不来
        int notComeHappy = 0;
        for (Employee subordinate : employee.getSubordinates()) {
            EmployeeInfo employeeInfo = process(subordinate);
            comeHappy += employeeInfo.getNotComeHappy();
            notComeHappy += Math.max(employeeInfo.getComeHappy(), employeeInfo.getNotComeHappy());
        }
        return new EmployeeInfo(comeHappy, notComeHappy);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class EmployeeInfo {
        private int comeHappy;
        private int notComeHappy;
    }

    @Data
    static class Employee {
        public int happy;//这名员工可以带来的快乐值
        List<Employee> subordinates;// 这名员工有哪些直接下级
    }
}
