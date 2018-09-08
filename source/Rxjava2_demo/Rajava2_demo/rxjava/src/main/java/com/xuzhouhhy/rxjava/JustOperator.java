package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/7 15:54
 */
public class JustOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.just(1, 2, 3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("onNext : " + integer);
                    }
                });
    }

}
