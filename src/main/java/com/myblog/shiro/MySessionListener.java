package com.myblog.shiro;

/**
 * Created by Zephery on 2017/7/18.
 */

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MySessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
        System.out.println("登录+1==" + sessionCount.get());
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("登录退出-1==" + sessionCount.get());
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("登录过期-1==" + sessionCount.get());

    }

    public int getSessionCount() {
        return sessionCount.get();
    }
}
