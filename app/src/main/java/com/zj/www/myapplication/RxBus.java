package com.zj.www.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by zj on 2017/8/11.
 * 定义一个rxbus来取代eventbus来信息传递
 */
public class RxBus {

    // 先定义一个安全集合储存观察者和被观察者
    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    // 使用一个単例来声明rxbus
    private static class Holder {
        private static RxBus instance = new RxBus();
    }

    public static RxBus getInstance() {
        return Holder.instance;
    }

    // 注册事件
    public <T> Observable<T> register(Class<T> clz) {
        return register(clz.getName());
    }

    public <T> Observable<T> register(Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }

        Subject<T> subject = PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }

    //接触注册事件
    public <T> void unregister(Class<T> clz, Observable observable) {
        unregister(clz.getName(), observable);
    }

    public void unregister(Object tag, Observable observable) {
        List<Subject> subjects = subjectMapper.get(tag);
        if (null != subjects) {
            subjects.remove(observable);
            if (subjects.isEmpty()) {
                subjectMapper.remove(tag);
            }
        }
    }

    // 发送事件
    public void post(Object content) {
        post(content.getClass().getName(), content);  //根据当时所处的类名来发送内容
    }

    public void post(Object tag, Object content) {
        List<Subject> subjects = subjectMapper.get(tag);
        if (!subjects.isEmpty()) {
            for (Subject subject : subjects) {
                subject.onNext(content); //发送内容
            }
        }
    }
}
