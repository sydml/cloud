package com.sydml.etcd.client;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.cluster.Member;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author liuyuming
 * @date 2021-03-26 17:50:30
 */
public class EtcdConfig {
    public static Client getEtcdClient(){
        Client client = Client.builder().endpoints("http://172.18.73.179:2479").build();
        return client;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Client client = getEtcdClient();
        client.getKVClient().put(ByteSequence.from("testKey".getBytes(StandardCharsets.UTF_8)), ByteSequence.from("etcdValueTest".getBytes(StandardCharsets.UTF_8)));
        client.getKVClient().get(ByteSequence.from("testKey".getBytes(StandardCharsets.UTF_8))).get();
        client.getClusterClient().listMember().get().getMembers().get(0).getClientURIs();
        client.getKVClient().get(ByteSequence.from("testKey".getBytes(StandardCharsets.UTF_8))).get().getKvs().get(0).getValue().getBytes();
        String s = client.getKVClient().get(ByteSequence.from("testKey".getBytes(StandardCharsets.UTF_8))).get().getKvs().get(0).getValue().toString();
        System.out.println(s);


    }
}
