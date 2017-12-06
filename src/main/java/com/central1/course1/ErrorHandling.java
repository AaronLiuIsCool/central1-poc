package com.central1.course1;


import com.central1.course1.domain.Account;
import com.central1.course1.domain.ErrorProne;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandling extends ErrorProne {


    public Observable<Account> getAccountsForScenario1(String username) {
        List<String> accountIds = new ArrayList<>();
        accountIds.add("Checking 1");
        accountIds.add("Checking 2");
        accountIds.add("Checking 3");
        accountIds.add("Saving 4");
        accountIds.add("Saving 5");
        accountIds.add("Saving 6");

        return Observable.fromIterable(accountIds)
                .flatMap(accountId -> getAccount(accountId))
                .map(account -> {
                    System.out.println("Loading account " + account.getAccountId());
                    return account;
                });

    }


    public Observable<Account> getAccountsForScenario2(String username) {
        List<String> accountIds = new ArrayList<>();
        accountIds.add("1- Checking 1");
        accountIds.add("2- Checking 2");
        accountIds.add("3- Checking 3");
        accountIds.add("4- Saving 4");
        accountIds.add("5- Saving 5");
        accountIds.add("6- Saving 6");

        return Observable.fromIterable(accountIds)
                .flatMap(accountId -> getAccount(accountId))
                .map(account -> {
                    System.out.println("Loading account " + account.getAccountId());
                    return account;
                });

    }


    public Observable<Account> getAccount(String accountId) {

        return Observable
                .defer(() -> {
                    if (accountId.contains("1")) {
                        return Observable.error(new RuntimeException("Account doesn't exist"));
                    } else if (accountId.contains("4")) {
                        return Observable.error(new RuntimeException("Unable to retrieve account. Network communication problem occurred."));
                    } else if (accountId.contains("5")) {
                        return Observable.error(new Exception("State is unknown"));
                    }

                    return Observable.just(new Account(accountId, "Account " + accountId));
                })
                .onErrorResumeNext(handleErrorGracefully())
                //.onErrorResumeNext(whenExceptionIsThenLogAndIgnore(RuntimeException.class))
                //.onErrorResumeNext(whenExceptionIsThenLogAndIgnore(IllegalStateException.class))
                ;


    }


    public <T> Function<Throwable, Observable<T>> handleErrorGracefully() {
        return t -> Observable.empty();
    }

    public static void main(String[] args) {

        ErrorHandling errorHandling = new ErrorHandling();
        //errorHandling.getAccountsForScenario1("Alex").subscribe();
        errorHandling.getAccountsForScenario2("Alex").subscribe();

    }
}
