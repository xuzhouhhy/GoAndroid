package com.xuzhouhhy.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/8 15:39
 */
public class DebounceOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(100);
                e.onNext(2);
                Thread.sleep(600);
                e.onNext(3);
                Thread.sleep(200);
                e.onNext(4);
                Thread.sleep(700);
                e.onNext(5);
                Thread.sleep(300);
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("accept : " + integer);
                    }
                });
    }

}
