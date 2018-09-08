package com.xuzhouhhy.rxjava;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/7 19:28
 */
public class SkipOperator {

    public static void main(String... args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Disposable subscribe = Observable.just(1, 2, 3, 4, 5, 6, 7)
                .skip(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("onSkipFinish : " + integer);
                    }
                });
    }
}