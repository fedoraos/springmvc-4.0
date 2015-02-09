package com.fedora.cache.service;

/**
 * Created by arno on 15-2-9.
 */
public interface CachedService {
      int DEFAULT_EXP_TIME = 7200;

    public void remove(final String key);

    //set value
    public void set(final String key, final Object value);
    public void set(final String key, final int exp, final Object value);

    //get value
    public Object get();
    public Object get(final String key);

    public void addServer(final String server, final int port);

    /**
     * Remove many memcached server
     *
     * @param host
     *            String like [host1]:[port1] [host2]:[port2] ...
     */
    public void removeServer(String hostList);



}
