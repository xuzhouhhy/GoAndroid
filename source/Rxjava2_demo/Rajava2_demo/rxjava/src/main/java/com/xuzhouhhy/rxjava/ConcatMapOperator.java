package com.xuzhouhhy.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * created by hanhongyun on 2018/9/7 18:08
 */
public class ConcatMapOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).concatMap((Function<Integer, ObservableSource<String>>) integer -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("this is value" + i);
            }
            int delayTime = (int) (1 + Math.random() * 10);
            return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
        }).subscribe(s -> System.out.println(System.currentTimeMillis() + " " + s));
    }
}
