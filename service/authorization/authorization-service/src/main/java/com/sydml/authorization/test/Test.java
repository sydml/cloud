package com.sydml.authorization.test;

import com.sydml.authorization.util.BeanConvertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Liuym
 * @date 2019/3/29 0029
 */
public class Test {
    public static void main(String[] args) {
        List<People> peopleList = new ArrayList<>();
        People people = new People();
        people.setName("wagn");
        people.setType(Type.VALID);
        peopleList.add(people);

        People people1 = new People();
        people1.setName("wagn");
        people1.setType(Type.INVALID);
        peopleList.add(people1);

        List<Map<String, Object>> collect = peopleList.stream().map(BeanConvertUtils::toMap).collect(Collectors.toList());
        peopleList.stream().map(it -> new HashMap()).collect(Collectors.toList());
        System.out.println();
    }
}
