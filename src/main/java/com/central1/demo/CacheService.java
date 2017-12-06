package com.central1.demo;


import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheService<T> {

    @Autowired
    private ErrorService errorService;

    public Observable<T> getFromCache(String key) {
        return Observable.just(key)
                .flatMap(errorService::logCacheFailure)
                .flatMap(keyFailed -> Observable.empty());
    }

}
