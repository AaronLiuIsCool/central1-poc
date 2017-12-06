package com.central1.course1;

import com.central1.course1.domain.Accounts;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class CrazyTime {

    private static Map<String, Accounts> cache = new HashMap<>();

    private Observable<Accounts> getAccountFromSloooooowSystem() {
        return Observable.create(e -> {

            System.out.println("CALLED");

            Thread.sleep(1000);

            Accounts accounts = new Accounts();
            accounts.add("Checking1", "Checking Account");
            accounts.add("Checking2", "Checking Account Shared");
            accounts.add("saving1", "Saving Account");

            e.onNext(accounts);
        });
    }

    public BehaviorSubject<Accounts> getAccount(String username) {

        AtomicBoolean dirty = new AtomicBoolean(true);

        BehaviorSubject<Accounts> accountBehaviorSubject = BehaviorSubject.create();

        getFromCache(username)
                .switchIfEmpty(getAccountFromSloooooowSystem().flatMap(accounts -> cache(username, accounts)))
                .subscribe(new Observer<Accounts>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Accounts accounts) {
                        accountBehaviorSubject.onNext(accounts);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return accountBehaviorSubject;

    }

    private Observable<Accounts> getFromCache(String username) {

        return Observable.just(username)
                .flatMap(s -> {
                    if (cache.containsKey(username)) {
                        return Observable.just(cache.get(username));
                    }
                    return Observable.empty();
                });
    }

    private Observable<Accounts> cache(String key, Accounts accounts) {
        cache.put(key, accounts);
        return Observable.just(accounts);
    }

    public static void main(String[] args) throws InterruptedException {
        CrazyTime crazyTime = new CrazyTime();

        ExecutorService pool = Executors.newFixedThreadPool(4);

        pool.submit(() -> {
            crazyTime.getAccount("test").map(accounts -> accounts).subscribe();
        });

        pool.submit(() -> {
            crazyTime.getAccount("test").map(accounts -> accounts).subscribe();
        });

        Thread.sleep(2000);

        Observable.zip(crazyTime.getAccount("test").map(accounts -> accounts),crazyTime.getAccount("test").map(accounts -> accounts), (accounts, accounts2) -> {
            return accounts;
        }).subscribe();
//
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        crazyTime.getAccount("test")
//                .map(accounts -> {
//                    System.out.println(accounts);
//                    return accounts;
//                }).subscribe();
//        //Observable.zip(crazyTime.getAccount("test"), crazyTime.getAccount("test"),(accounts, accounts2) -> accounts).subscribe();

    }


}
