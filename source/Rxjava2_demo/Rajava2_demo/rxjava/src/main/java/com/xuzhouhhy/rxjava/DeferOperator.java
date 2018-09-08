package com.xuzhouhhy.rxjava;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * created by hanhongyun on 2018/9/8 15:45
 */
public class DeferOperator {

    public static void main(String... args) {
        Observable<Integer> defer = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        });

        defer.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

}
