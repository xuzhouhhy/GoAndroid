package com.xuzhouhhy.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * created by hanhongyun on 2018/9/7 17:09
 */
public class FlatMapOperator {

//    private SimpleDateFormat mFormat=new SimpleDateFormat("yy-MM-dd  hh:mm:ss:")

    public static void main(String... args) {
        Disposable subscribe = Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).flatMap(integer -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String s = "thit is value " + i;
//                System.out.println(s);
                list.add(s);
            }
            int delayTime = (int) (1 + Math.random() * 10);
            return Observable.fromIterable(list)
                    .delay(delayTime, TimeUnit.MILLISECONDS);
        })
//                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.computation())
                .subscribe(s -> System.out.println(System.currentTimeMillis() + " flatmap : accept : " + s));
    }

}
