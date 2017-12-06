package com.central1.demo;
import com.central1.demo.domain.Account;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    public AccountService accountService;

    @Autowired
    public PromotionService promotionService;

    @Autowired
    public PermissionService<Account> permissionService;

    @RequestMapping("/members/{username}/accounts")
    public List<Account> getAccounts(@PathVariable String username) {
        // blocking i/o

        return Observable.just(username)
                .flatMap(permissionService::hasPermissionToCollect)
                .flatMap(accountService::getAccounts)
                .flatMap(promotionService::attachPromotions)
                .flatMap(permissionService::maskBasedOnPermission)
                .toList().blockingGet();


    }
}
