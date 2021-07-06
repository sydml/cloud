package com.sydml.etcd.client;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyuming
 * @date 2021-03-26 17:50:30
 */
@Slf4j
public class EtcdConfig {
    private static Client client = Client.builder().endpoints("http://172.17.131.121:2479").build();

    public static void main(String[] args){
        try {
            setValue("testKey", "etcdValueTest");
            String value = getValue("testKey");
            log.info("getValue {} from key {}", "testKey", value);
            delete("testKey");
        } catch (Exception e) {
            log.error("operate etcd error", e);
        }finally {
            client.close();
        }


    }

    private static void delete(String key) throws InterruptedException, ExecutionException {
        client.getKVClient().delete(ByteSequence.from(key.getBytes(StandardCharsets.UTF_8))).get().getDeleted();
    }

    private static void setValue(String key, String value) {
        client.getKVClient().put(ByteSequence.from(key.getBytes(StandardCharsets.UTF_8)), ByteSequence.from(value.getBytes(StandardCharsets.UTF_8)));
    }

    private static String getValue(String key) throws InterruptedException, ExecutionException {
        return new String(client.getKVClient().get(ByteSequence.from(key.getBytes(StandardCharsets.UTF_8))).get().getKvs().get(0).getValue().getBytes(), StandardCharsets.UTF_8);
    }

}
