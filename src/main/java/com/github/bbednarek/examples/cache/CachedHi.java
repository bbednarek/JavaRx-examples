package com.github.bbednarek.examples.cache;

import rx.Observable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedHi implements Hi {

    private final Hi delegate;
    private final Map<String, String> cache;

    public CachedHi(Hi delegate) {
        this.delegate = delegate;
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public Observable<String> hi(final String name) {
        return Observable.defer(() -> {
                                    String cached = cache.get(name);
                                    if (cached != null) {
                                        return Observable.just(cached);
                                    } else {
                                        Observable<String> hi = delegate.hi(name);
                                        cache.put(name, hi.toBlocking().single());
                                        return hi;
                                    }
                                }
        );
    }
}
