package com.central1.demo;

import com.central1.demo.domain.Account;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private CacheService<Account> cacheService;

    public Observable<Account> getAccounts(String accountOwner) {

        return cacheService.getFromCache(accountOwner)
                .switchIfEmpty(getRawAccounts(accountOwner));
    }

    public Observable<Account> getRawAccounts(String accountOwner) {
        return Observable.range(0, 10)
                .map(id -> new Account(id, "Account Type " + id + " - " + accountOwner, 100));
    }


}
