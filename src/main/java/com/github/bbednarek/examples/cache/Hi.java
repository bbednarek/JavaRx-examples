package com.github.bbednarek.examples.cache;

import rx.Observable;

public interface Hi {

    Observable<String> hi(String name);

}
