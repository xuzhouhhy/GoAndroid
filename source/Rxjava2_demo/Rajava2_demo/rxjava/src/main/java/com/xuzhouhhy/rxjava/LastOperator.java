package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/8 16:03
 */
public class LastOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.just(1, 2, 3)
                .last(6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(System.currentTimeMillis() + " : " + integer);
                    }
                });
    }

}
