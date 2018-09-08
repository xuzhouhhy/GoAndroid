package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * created by hanhongyun on 2018/9/7 18:23
 */
public class DistinctOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.just(1, 1, 3, 2, 2, 3, 4, 4, 5)
                .distinct()
                .subscribe(integer -> System.out.println(System.currentTimeMillis() + " : " + integer));
    }

}
