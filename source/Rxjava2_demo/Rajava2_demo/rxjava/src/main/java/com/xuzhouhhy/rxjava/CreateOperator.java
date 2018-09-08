package com.xuzhouhhy.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CreateOperator {

    public static void main(String... args) {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            System.out.println("Observale emit 1");
            e.onNext(1);
            System.out.println("Observale emit 2");
            e.onNext(2);
            System.out.println("Observale emit 3");
            e.onNext(3);
            System.out.println("Observale emit 4");
            e.onNext(4);
            e.onComplete();
            System.out.println("Observale emit 5");
            e.onNext(5);
        }).subscribe(new Observer<Integer>() {

            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe : " + d.isDisposed());
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                System.out.println("onNext : " + integer);
//                if (i == 2) {
//                    mDisposable.dispose();
//                    System.out.println("onNext : isDisposed : " + mDisposable.isDisposed());
//                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

    }

}