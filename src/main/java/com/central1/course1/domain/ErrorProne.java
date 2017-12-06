package com.central1.course1.domain;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public abstract class ErrorProne {

    public <T> Observable<T> logError(ChainError chainError) {
        System.out.println("Error: " + chainError.getMessage());
        return Observable.empty();
    }

    public <T> Function<Throwable, Observable<T>> whenExceptionIsThenLogAndIgnore(Class what) {
        return t -> what.isInstance(t) ? fireErrorLog(t) : breakTheChain(t);
    }

    public  <T> Observable<T> breakTheChain(Throwable t) {
        System.out.println("\t\tBreaking the chain");
        return Observable.error(t);
    }

    public  <T> Observable<T> fireErrorLog(Throwable t) {
        System.out.println("\t\tMaking sure chain is resilient");
        return logError(new ChainError( t.getMessage() ));
    }

}
