package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/7 19:33
 */
public class TakeOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.just(1, 2, 3, 4, 5, 6)
                .take(5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("result : " + integer);
                    }
                });
    }
}
