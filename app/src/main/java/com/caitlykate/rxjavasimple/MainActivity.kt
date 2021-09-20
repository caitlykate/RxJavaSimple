package com.caitlykate.rxjavasimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable


class MainActivity : AppCompatActivity() {
    private var TAG = "MainActivity"
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        button.setOnClickListener{
            startRStream()                  //RxJava
            startRStreamKotlin()            //RxJava + RxKotlin Extensions
            startRStreamZip()               //объединение потоков
        }
    }

    private fun startRStream(){

        val myObservable = getObservable()
        val myObserver = getObserver()
        myObservable.subscribe(myObserver)
    }

    private fun getObservable(): Observable<String> {
        //один из способов создания
        return Observable.just("1","2","3","4","5")
    }

    private fun getObserver(): Observer<String>{
        return object : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                //TODO("Not yet implemented")
            }

            override fun onNext(s: String) {
                Log.d(TAG, "onNext: $s")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }

        }

    }


    private fun startRStreamKotlin(){
        val list = listOf("1", "2", "3", "4", "5")

        list.toObservable()     //
            .subscribeBy(       //Kotlin Extensions для RxJava
            onNext = {println(it)},
            onError = {it.printStackTrace()},
            onComplete = {println("onComplete!")}
        )
    }

    private fun startRStreamZip() {

        val numbers = Observable.range(1, 6)

        val strings = Observable.just("One", "Two", "Three",

            "Four", "Five", "Six" )

        val zipped = Observable.zip(strings, numbers) { s, n -> "$s $n" }
        zipped.subscribe(::println)
    }
}