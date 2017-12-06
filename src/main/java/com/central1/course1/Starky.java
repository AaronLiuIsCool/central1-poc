package com.central1.course1;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class Starky {


    public Observable<List<Content>> map(Observable<List<String>> convertee) {
        return convertee.flatMap(strings -> Observable.fromIterable(strings))
                .map(this::map)
                .toList()
                .toObservable();

    }

    public Content map(String convertee) {
        return new Content(Integer.parseInt(convertee));
    }

    public static void main(String[] args) {

        Starky starky = new Starky();

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        Observable<List<String>> listObservable = Observable.just(list);

        Observable<List<Content>> result = starky.map(listObservable);

        result.flatMap(contents -> Observable.fromIterable(contents))
                .map(content -> {
                    System.out.println(content.getTest());
                    return content.getTest();
                })
                .toList()
                .subscribe();


    }
}
