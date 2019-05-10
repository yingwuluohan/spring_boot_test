package com.unisound.iot.copy_asr.thread;


import com.unisound.iot.copy_asr.api.Context;
import com.unisound.iot.copy_asr.api.Resource;
import com.unisound.iot.copy_asr.service.AsrResource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceCenter {
    private static ConcurrentHashMap<String, Resource> store = new ConcurrentHashMap();
    private static Set<String> creating = Collections.synchronizedSet(new HashSet());

    private static int SUB = 0;

    public ResourceCenter() {
    }


    public static Resource create(Context context) throws Exception {
        String id = context.getId();
        System.out.println( "ResourceCenter************" );
        synchronized(id) {
            Resource var5 = new AsrResource( id );
            try {
                creating.add(id);
                Resource res =
                store.put(context.getId(), var5 );
                var5 = res;
            } finally {
                creating.remove(id);
                id.notifyAll();
            }

            return var5;
        }
    }

    public static Resource getResource(String id) {
        Resource res = (Resource)store.get(id);
        if (res == null) {
            synchronized(id) {
                res = (Resource)store.get(id);
                if (res == null) {
                    try {
                        id.wait(5000L);
                    } catch (InterruptedException var4) {
                        var4.printStackTrace();
                    }
                }

                res = (Resource)store.get(id);
            }
        }

        return res;
    }

    public static void removeResource(String id) throws Exception {

        try {
            synchronized(id) {
                id.notifyAll();
            }

            if (creating.contains(id)) {
                id.wait();
            }
        } finally {
            Resource res = (Resource)store.remove(id);
            if (res != null) {
            }

        }

    }
}
