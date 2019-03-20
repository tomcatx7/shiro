package com.example.cms.test;

import com.example.cms.test.bean.Apple;
import com.example.cms.test.bean.Fruit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Container {
    static class Container1{
        List<? extends Fruit> list = new ArrayList<>();

        void f2(){
            list = Arrays.asList(new Apple(),new Fruit());
            Fruit fruit1 = list.get(0);
            fruit1.eat();
            Fruit fruit2 = list.get(1);
            fruit2.eat();
        }

    }
    static class Container2{
        List<Apple> apples = new ArrayList<>();
        List<Fruit> fruits = new ArrayList<>();

        void f1(List<? super Apple> list){
            list.add(new Apple());
            list.get(0);
        }
        void f2(){
        }

    }
    static class Container3<T extends Fruit>{
        List<T> list = new ArrayList<>();
        void add (T t){
            list.add(t);
        }
        T get(){
            return list.get(0);
        }
    }

//    public static void main(String[] args) {
//        System.out.println("classpath");
//        System.out.println(System.getProperty("java.library.path"));
//    }

}
