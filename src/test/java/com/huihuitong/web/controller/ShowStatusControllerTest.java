package com.huihuitong.web.controller;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Created by yangz on 2017/9/28 11:24:16.
 */
@RunWith(Theories.class)
public class ShowStatusControllerTest {
    @DataPoints
    public static String[] names = {"Tony", "Jim"};
    @DataPoints
    public static int[] ageValue1 = {10, 20};

    @Theory
    public void testMethod(String name, int age) {
        System.out.println(String.format("%s's age is %s", name, age));
    }
}
