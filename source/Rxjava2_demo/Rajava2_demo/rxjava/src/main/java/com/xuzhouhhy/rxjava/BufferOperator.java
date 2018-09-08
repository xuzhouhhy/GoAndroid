package com.xuzhouhhy.rxjava;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * created by hanhongyun on 2018/9/7 18:30
 */
public class BufferOperator {

    public static void main(String... args) {
        Disposable subscribe = Observable.just(1, 2, 3, 4, 5, 6)
                .buffer(4, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        System.out.println("--------");
                        System.out.println(System.currentTimeMillis());
                        int size = integers.size();
                        System.out.println("buffer size : " + size);
                        System.out.println("buffer value : ");
                        for (int i = 0; i < size; i++) {
                            System.out.println(integers.get(i));
                        }
                        System.out.println("--------");
                    }
                });
    }

}
