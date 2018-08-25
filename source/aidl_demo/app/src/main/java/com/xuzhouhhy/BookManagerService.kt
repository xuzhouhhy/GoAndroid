package com.xuzhouhhy

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

class BookManagerService : Service() {

    //    private val mBookList = CopyOnWriteArrayList<Book>()
    private val mBookList = mutableListOf<Book>()

    //    private val mListenerList = CopyOnWriteArrayList<IOnNewBookArrivedListener>()
    private val mRemoteCallbackList = RemoteCallbackList<IOnNewBookArrivedListener>()

    private val mIsDestroyed = AtomicBoolean(false)

    private val binder = object : IBookManager.Stub() {

        override fun addBook(book: Book?) {
            mBookList.add(book ?: return)
        }

        override fun getBookList(): MutableList<Book> {
            return mBookList
        }

        override fun registerListerner(listener: IOnNewBookArrivedListener) {
            mRemoteCallbackList.register(listener)
            Log.i(Constants.TAG, "register ,listener size: ${mRemoteCallbackList.registeredCallbackCount}")
//            if (mListenerList.contains(listener)) {
//                Log.i(Constants.TAG, "register fail : already registered")
//            } else {
//                mListenerList.add(listener)
//                Log.i(Constants.TAG, "register success")
//            }
        }

        override fun unregisterListerner(listener: IOnNewBookArrivedListener) {
            mRemoteCallbackList.unregister(listener)
            Log.i(Constants.TAG, "unqregister ,listener size: ${mRemoteCallbackList.registeredCallbackCount}")
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener)
//                Log.i(Constants.TAG, "already removed")
//            } else {
//                Log.i(Constants.TAG, "listener not register")
//            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        mBookList.add(Book("ios"))
        mBookList.add(Book("Android"))
        Thread(Runnable {
            while (!mIsDestroyed.get()) {
                Thread.sleep(5000)
                val size = mBookList.size
                val newBook = Book("第${size + 1}本书")
                mBookList.add(newBook)
                onNewBookArrived(newBook)
            }
        }).start()
    }

    private fun onNewBookArrived(newBook: Book) {
        mRemoteCallbackList.beginBroadcast()
        val count = mRemoteCallbackList.registeredCallbackCount
        for (i in 0 until count) {
            val broadcastItem = mRemoteCallbackList.getBroadcastItem(i)
            broadcastItem.onNewBookArrived(newBook)
        }
        mRemoteCallbackList.finishBroadcast()
//        mListenerList.forEach {
//            it.onNewBookArrived(newBook)
//        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsDestroyed.set(true)
    }
}