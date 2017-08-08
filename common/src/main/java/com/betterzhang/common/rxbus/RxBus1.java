package com.betterzhang.common.rxbus;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Android Studio.
 * Author : zhangzhongqiang
 * Email  : betterzhang.dev@gmail.com
 * Time   : 2017/08/08 下午 3:45
 * Desc   : description
 */

public class RxBus1 {

    private static volatile RxBus1 defaultInstance;

    private final Subject<Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus1() {
        bus = PublishSubject.create().toSerialized();
    }

    // 单例RxBus
    public static RxBus1 getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus1.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus1();
                }
            }
        }
        return defaultInstance;
    }

    // 发送一个新的事件，所有订阅此事件的订阅者都会收到
    public void post(Object action) {
        bus.onNext(action);
    }

    // 用 code 指定订阅此事件的对应 code 的订阅者
    public void postWithCode(int code, Object action) {
        bus.onNext(new Action(code, action));
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者,
    public <T> Observable<T> toObservableWithCode(final int code, Class<T> eventType) {
        return bus.ofType(Action.class)
                .filter(new Predicate<Action>() {
                    @Override
                    public boolean test(Action action) throws Exception {
                        return action.code == code;
                    }
                })
                .map(new Function<Action, Object>() {
                    @Override
                    public Object apply(Action action) throws Exception {
                        return action.data;
                    }
                })
                .cast(eventType);
    }

}
