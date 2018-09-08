package com.xuzhouhhy.rxjava;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HelloWorld {

    public static void main(String[] args) {
        Disposable subscribe = Flowable.just("hello world").subscribe(System.out::println);
        hello("hello", "et", "I", "am", "xuzhouhhy");
    }

    private static void hello(String... names) {
        Disposable subscribe = Observable.fromArray(names)
                .subscribe(System.out::println);
    }

}
