package com.central1.course1;

import io.reactivex.Observable;
import io.reactivex.Single;
import rx.observables.ConnectableObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PullVsPush {

    public static void main(String[] args) {

        PullVsPush pullVsPush = new PullVsPush();

        List<String> phoneNumbers = pullVsPush.getRandomPhoneNumber(100);

        long start = System.nanoTime();
        List<String> cleanedPhoneNumbers = phoneNumbers.stream().filter(phoneNumber -> pullVsPush.lastFourDigitContains(phoneNumber, "0")).collect(Collectors.toList());
        long end = System.nanoTime();

        long loopResult = end - start;
        System.out.println(loopResult+"\tloopResult");


        start = System.nanoTime();
        Single<List<String>> phoneSingle = Observable.defer(() ->
                Observable.fromIterable(phoneNumbers)
                        .filter(phoneNumber -> pullVsPush.lastFourDigitContains(phoneNumber, "0")))
        .toList();
        end = System.nanoTime();


        cleanedPhoneNumbers = phoneSingle.blockingGet();


        long observableResult = end - start;



        System.out.println(observableResult + "\tobservableResult");




    }

    private boolean lastFourDigitContains(String string, String contains) {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return string.substring(8, 12).contains(contains);
    }

    public List<String> getRandomPhoneNumber(int numberToBeGenerated) {

        List<String> phoneNumbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberToBeGenerated; i++) {
            phoneNumbers.add("604-" + (111 + random.nextInt(999)) + "-" + (1111 + random.nextInt(9999)));
        }

        return phoneNumbers;
    }

}
