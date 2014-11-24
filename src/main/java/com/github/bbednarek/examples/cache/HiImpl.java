package com.github.bbednarek.examples.cache;

import rx.Observable;

public class HiImpl implements Hi {

    @Override
    public Observable<String> hi(final String name) {
        return Observable.just("Hi " + name);
    }

}
