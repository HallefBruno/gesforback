package com.gesforback.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        //System.out.println("Cache: "+cacheEvent.getKey()+" , "+ cacheEvent.getOldValue() +", "+cacheEvent.getNewValue());
        System.out.println("Cache: ");
    }
}
