package com.central1.demo;

import io.reactivex.Observable;
import org.springframework.stereotype.Service;

@Service
public class PermissionService<T> {

    public Observable<T> maskBasedOnPermission(T checkDomainForPermission) {
        // domain object permission checker implementation
        return Observable.just(checkDomainForPermission);
    }

    public Observable<String> hasPermissionToCollect(String username) {
        if(username.contains("scott")) {
            return Observable.error(new RuntimeException("Permission denied"));
        }
        return  Observable.just(username);
    }


}
