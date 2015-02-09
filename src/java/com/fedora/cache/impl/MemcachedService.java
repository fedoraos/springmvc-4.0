package com.fedora.cache.impl;

import com.fedora.cache.service.CachedService;
import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by arno on 15-2-9.
 */
@Service
public class MemcachedService implements CachedService {

    private MemcachedClient client;

    @Override
    public void set(String key, Object value) {
        this.set(key, DEFAULT_EXP_TIME, value);
    }

    /**
     * cas 原子更新
     * @param key
     * @param exp
     * @param value
     */
    @Override
    public void set(String key, int exp, Object value) {
        try {
            if (client.get(key) == null)
                return ;
            GetsResponse<Integer> result = client.gets(key);
            if (result == null)
                client.set(key, exp, value);
            else
                client.cas(key, exp, new CASOperation() {

                    @Override
                    public int getMaxTries() {
                        return 10;
                    }

                    @Override
                    public Object getNewValue(long currentCAS, Object currentValue) {
                        return currentValue;
                    }
                });

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        try {
            client.set(key, exp, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public Object get(String key) {
        try {
            return client.get(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
            this.addServer("localhost", 12000);
        }
        return null;
    }

    @Override
    public void remove(String key) {
        try {
            client.delete(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addServer(String server, int port) {
        try {
            client.addServer(server, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeServer(String hostList) {
        client.removeServer(hostList);
    }

    @Autowired
    public void setClient(MemcachedClient client) {
        this.client = client;
    }
}
