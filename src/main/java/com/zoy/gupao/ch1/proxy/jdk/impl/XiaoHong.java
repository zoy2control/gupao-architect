package com.zoy.gupao.ch1.proxy.jdk.impl;

import com.zoy.gupao.proxy.jdk.Person;
import lombok.Data;

/**
 * 被代理者
 * Created by zoypong on 2019/5/2.
 */
@Data
public class XiaoHong implements Person {

    private String name = "小红";
    private String sex = "女";

    @Override
    public void findLove() {
        System.out.println("我是：" + this.name + ",性别：" + this.sex + "。我找对象的要求是：");
        System.out.println("高富帅");
        System.out.println("有房有车");
        System.out.println("身高180cm以上，体重70kg以上");
    }
}
