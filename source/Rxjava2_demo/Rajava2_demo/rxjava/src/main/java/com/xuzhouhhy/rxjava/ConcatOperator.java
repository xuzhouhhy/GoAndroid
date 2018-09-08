package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * created by hanhongyun on 2018/9/7 16:37
 */
public class ConcatOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(integer -> System.out.println("accept " + integer));
    }

}
