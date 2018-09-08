package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/8 16:05
 */
public class MergeOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.merge(Observable.just(1, 2), Observable.just(3, 4))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(System.currentTimeMillis() + " : " + integer);
                    }
                });
    }

}
