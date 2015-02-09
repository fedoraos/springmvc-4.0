package com.arno;

import net.rubyeye.xmemcached.*;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.StringTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by arno on 15-2-9.
 */
public class MemcachedTest {
    private static  MemcachedClient client;
    @BeforeClass
    public static  void init() throws InterruptedException, MemcachedException, TimeoutException {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses("localhost:12000"));
        try {
            client = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
          client.flushAll();
    }

    @Test
    public void testCAC() throws InterruptedException, MemcachedException, TimeoutException {
        client.set("test", 200, "test");

        GetsResponse<String> result = client.gets("test");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());


        client.addWithNoReply("test",200, "test");
         result = client.gets("test");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());

        client.add("test",200, "test1");
        result = client.gets("test");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());


        client.replace("test",200, " memcached client");
        result = client.gets("test");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());

        client.append("test", " for java");
        result = client.gets("test");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());
    }

    @Test
    public void testselfCAS() throws InterruptedException, MemcachedException, TimeoutException {

        set("helo", 200, " memcached client");
        GetsResponse<String> result  = client.gets("helo");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());

        client.replace("helo", 200, " memcached client");
         result  = client.gets("helo");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());



        client.delete("helo", 20L);
        client.add("helo", 200, " memcached client");
        result  = client.gets("helo");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());

        client.add("helo", 200, " memcached client");
        result  = client.gets("helo");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());

        set("helo", 200, " memcached client");
        result  = client.gets("helo");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());


        set("helo", 200, " memcached client");
        result  = client.gets("helo");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());

        set("helo", 200, " memcached client");
        result  = client.gets("helo");
        System.out.printf("%s\t%s\n", result.getCas(),result.getValue());

    }

    @Test
    public void testop() throws InterruptedException, MemcachedException, TimeoutException {

        client.flushAll();
        if (!client.set("hello1", 0, "world")) {
            System.err.println("set error");
        }
        if (client.add("hello1", 0, "dennis")) {
            System.err.println("Add error,key is existed");
        }
        if (!client.replace("hello1", 0, "dennis")) {
            System.err.println("replace error");
        }
        client.append("hello1", " good");
        client.prepend("hello1", "hello ");
        String name = client.get("hello1", new StringTranscoder());
        System.out.println(name);
        client.deleteWithNoReply("hello1");
    }

    public void set(String key, int expried, final Object value) {
        try {
            if (value == null)
                return;
            GetsResponse<Integer> result = client.gets(key);
            if (result == null) {
                client.set(key, expried, value);
            } else {
                client.cas(key, expried, new CASOperation<Object>() {
                    public int getMaxTries() {
                        return 10;
                    }

                    public Object getNewValue(long currentCAS,
                                              Object currentValue) {
                        return value;
                    }
                });
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
