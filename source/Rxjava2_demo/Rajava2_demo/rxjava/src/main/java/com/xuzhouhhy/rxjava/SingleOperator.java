package com.xuzhouhhy.rxjava;

import java.util.Random;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * created by hanhongyun on 2018/9/8 15:31
 */
public class SingleOperator {

    public static void main(String... args) {
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        System.out.println("onSuccess : " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError : " + e.getMessage());
                    }
                });
    }

}
