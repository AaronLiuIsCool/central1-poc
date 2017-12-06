package com.central1.course1;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Massive {

    public Observable<Content> error(Content content) {
        if(content.getTest()==3) {
            return Observable.error(new Exception("Invalid number"));
        }
        return Observable.just(content);
    }

    public Observable<Content> m1(List<Content> list) {

        return Observable.fromIterable(list)
                .flatMap(content ->
                        error(content).onErrorResumeNext(Observable.empty())
                );
    }


    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());

        List<Content> list = new ArrayList<>();
        list.add(new Content(1));
        list.add(new Content(2));
        list.add(new Content(3));
        list.add(new Content(4));
        list.add(new Content(5));

        Massive massive = new Massive();
        massive.m1(list)
                .map(content -> {
                    System.out.println(content.getTest());
                    return content;
                })
//                .toList()
                .subscribe();

    }



}
