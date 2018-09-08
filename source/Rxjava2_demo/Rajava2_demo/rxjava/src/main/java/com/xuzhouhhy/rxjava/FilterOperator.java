package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * created by hanhongyun on 2018/9/7 18:27
 */
public class FilterOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.just(1, 2, 3, -1, -2, -3)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 0;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(System.currentTimeMillis() + " : " + integer);
                    }
                });
    }

}
