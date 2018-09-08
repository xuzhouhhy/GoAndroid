package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * created by hanhongyun on 2018/9/7 16:18
 */
public class MapOperator {

    public static void main(String... arg) {
        Disposable subscribe = Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        })
                .map(integer -> "this is the value " + integer)
                .subscribe(s -> System.out.println("accept:" + s));
    }
}
