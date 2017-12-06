package com.central1.demo;

import com.central1.demo.domain.Account;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;


public class Demo1 {

    @Autowired
    public AccountService accountService;

    @Autowired
    public PromotionService promotionService;

    @Autowired
    public PermissionService<Account> permissionService;

    public Observable<Account> getRawAccounts(String username) {

        return Observable.just(username)
                .flatMap(permissionService::hasPermissionToCollect)
                .flatMap(accountService::getAccounts)
                .flatMap(promotionService::attachPromotions)
                .flatMap(permissionService::maskBasedOnPermission);
    }


}
