package com.xuzhouhhy

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.xuzhouhhy.aidl.R

class MainActivity : AppCompatActivity() {

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                MESSAGE_NEW_BOOK_ARRIVED -> Log.i(Constants.TAG, "new book ${msg.obj}")
            }
        }
    }

    var bookManager: IBookManager? = null

    val arrivedListener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(book: Book?) {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget()
        }
    }

    private val mConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = IBookManager.Stub.asInterface(service)
            val bookList = bookManager?.bookList
            Log.i(Constants.TAG, "query book list:" + bookList.toString())
            val newBook = Book("Android art")
            Log.i(Constants.TAG, "add new book:" + newBook.toString())
            bookManager?.addBook(newBook)
            val newBookList = bookManager?.bookList
            Log.i(Constants.TAG, "query new book list:" + newBookList.toString())
            bookManager?.registerListerner(arrivedListener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, BookManagerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }



    override fun onStop() {
        super.onStop()
        bookManager?.unregisterListerner(arrivedListener)
    }

    override fun onResume() {
        super.onResume()
        bookManager?.registerListerner(arrivedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val MESSAGE_NEW_BOOK_ARRIVED = 100
    }

}

class Constants {
    companion object {
        const val TAG = "aidl_demo"
    }
}
