// IBookManager.aidl
package com.xuzhouhhy;

import com.xuzhouhhy.Book;
import com.xuzhouhhy.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

List<Book> getBookList();

void addBook (in Book book);

void registerListerner(IOnNewBookArrivedListener listener);

void unregisterListerner(IOnNewBookArrivedListener listener);

}
