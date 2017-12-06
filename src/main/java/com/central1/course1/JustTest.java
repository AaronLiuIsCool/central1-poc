package com.central1.course1;


import io.reactivex.Observable;

import java.util.List;

public class JustTest {


    public Observable<String> test(String v) {
        return Observable.just(v);
    }

    public Observable<String> testCollection() {

        return Observable.range(0, 10)
                .map(counter -> "item:" + counter)
                .flatMap(item -> {
                    Thread.sleep(5);
                    System.out.println(item);

                    return Observable.just(item);
                });
    }

    public static void main(String[] args) throws InterruptedException {

        JustTest justTest = new JustTest();
        Observable<List<String>> s1 = justTest.testCollection().toList().toObservable();

//        s1.subscribe();
//
//        //Thread.sleep(20);
//
//        s1.subscribe();

        Observable.zip(s1, s1, (t1, t2) -> {
            System.out.println("done");
            return "";
        }).subscribe();

    }

}
