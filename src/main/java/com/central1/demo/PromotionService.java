package com.central1.demo;

import com.central1.demo.domain.Account;
import com.central1.demo.domain.Promotion;
import io.reactivex.Observable;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {


    public Observable<Account>  attachPromotions(Account account) {

        System.out.println("Hello");

        return loadPromotionForAccount(account)
                .map(promotion -> account.attachPromotion(promotion));


    }

    public Observable<Promotion> loadPromotionForAccount(Account account) {
        return Observable.just(new Promotion(account.getType(), "Amazing promotion"));
    }

}
