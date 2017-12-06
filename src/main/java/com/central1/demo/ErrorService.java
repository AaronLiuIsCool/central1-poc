package com.central1.demo;

import io.reactivex.Observable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ErrorService {

    private static List<String> errors = new ArrayList<>();

    public Observable<String> logCacheFailure(String error) {
        errors.add("Unable to load cache for " + error);
        return Observable.just(error);
    }


}
