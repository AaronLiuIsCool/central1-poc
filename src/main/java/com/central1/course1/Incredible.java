package com.central1.course1;


import io.reactivex.Observable;

public class Incredible {




    public Observable<Content> loadFromCache(String id) {
        // read from cache
        return Observable.empty();
    }

    public Observable<Content> loadFromDb(String id) {
        return Observable.just(new Content(2))
                .map(content -> {
                    System.out.println(content+" is coming from DB");
                    return content;
                })
                .onErrorResumeNext(Observable.empty());
    }

    public Observable<Content> cache(Content content) {
        System.out.println("cached now");
        // cache the data
        return Observable.just(content);
    }


    public Observable<Content> getObject(String id, Observable<Content> contentLoad) {

        return loadFromCache(id).switchIfEmpty(loadFromDb(id).flatMap(this::cache));
    }

    public static void main(String[] args) {

        Incredible incredible = new Incredible();

        //Observable.defer(() -> incredible.getObject("1")).subscribe();


    }

}
