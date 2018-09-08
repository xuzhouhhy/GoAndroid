package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/8 16:12
 */
public class ReduceOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        System.out.println(System.currentTimeMillis() + " middle : " + integer + "  " + integer2);
                        return integer * integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(System.currentTimeMillis() + " result : " + integer);
                    }
                });
    }

}
