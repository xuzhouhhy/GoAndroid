package com.xuzhouhhy.rxjava;


import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * created by hanhongyun on 2018/9/7 16:25
 */
public class ZipOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.zip(getObservableInteger(), getObservableString(), (integer, s) -> s + integer)
                .subscribe(s -> System.out.println("result is : " + s));
    }

    private static Observable<Integer> getObservableInteger() {
        return Observable.create(e -> {
            if (!e.isDisposed()) {
                System.out.println("onNextInteger emit : 1");
                e.onNext(1);
                System.out.println("onNextInteger emit : 2");
                e.onNext(2);
                System.out.println("onNextInteger emit : 3");
                e.onNext(3);
                System.out.println("onNextInteger emit : 4");
                e.onNext(4);
            }
        });
    }

    private static Observable<String> getObservableString() {
        return Observable.create(e -> {
            if (!e.isDisposed()) {
                System.out.println("onNextString emit : A");
                e.onNext("A");
                System.out.println("onNextString emit : B");
                e.onNext("B");
                System.out.println("onNextString emit : C");
                e.onNext("C");
            }
        });
    }
}
