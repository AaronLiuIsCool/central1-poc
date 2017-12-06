package com.central1.course1;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.List;

public class Fluable {

    public static void main(String[] args) {

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        BehaviorSubject<Content> contentBehaviorSubject = BehaviorSubject.create();
        contentBehaviorSubject.onNext(new Content(1));

        Observable<Integer> recordsObservable = Observable.fromIterable(integers);
        Observable<Content> contentObservable = contentBehaviorSubject.map(content -> content);

        contentBehaviorSubject.onNext(new Content(2));

        Observable.combineLatest(contentObservable, recordsObservable, (content, integer) -> {
            System.out.println(content.getTest() + " - " + integer);
            return integer;
        }).subscribe();

        contentBehaviorSubject.onNext(new Content(3));

        Observable.combineLatest(contentObservable, recordsObservable, (content, integer) -> {
            System.out.println(content.getTest() + " = " + integer);
            return integer;
        }).subscribe();


    }

}
