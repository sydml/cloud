/*
package com.sydml.redission.config;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

*/
/**
 * @author Liuym
 * @date 2019/3/19 0019
 *//*

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfig {

    private int threads = 10;
    private String codecClass = "org.redisson.codec.JsonJacksonCodec";

    */
/**
     * 单机配置
     *//*

    @NestedConfigurationProperty
    private SingleServerConfigProp singleServer;

    // TODO 集群配置等等

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() throws Exception {
        Codec codec = (Codec) ClassUtils.forName(codecClass, ClassUtils.getDefaultClassLoader()).newInstance();
        Config config = new Config();
        config.setCodec(codec);
        config.setThreads(threads);
        config.setEventLoopGroup(new NioEventLoopGroup());
        config.setReferenceEnabled(true);
        config.setTransportMode(TransportMode.NIO);
        config.setNettyThreads(0);
        config.setLockWatchdogTimeout(30 * 1000L);
        config.setKeepPubSubOrder(true);
        config.setUseScriptCache(false);

        // 单机配置
        if (singleServer != null) {
            config.useSingleServer()
                    .setAddress(singleServer.address)
                    .setConnectionMinimumIdleSize(singleServer.connectionMinimumIdleSize)
                    .setConnectionPoolSize(singleServer.connectionPoolSize)
                    .setDatabase(singleServer.database)
                    .setDnsMonitoringInterval(singleServer.dnsMonitoringInterval)
                    .setSubscriptionConnectionMinimumIdleSize(singleServer.subscriptionConnectionMinimumIdleSize)
                    .setSubscriptionConnectionPoolSize(singleServer.subscriptionConnectionPoolSize)
                    .setSubscriptionsPerConnection(singleServer.subscriptionsPerConnection)
                    .setClientName(singleServer.clientName)
                    .setRetryAttempts(singleServer.retryAttempts)
                    .setRetryInterval(singleServer.retryInterval)
                    .setTimeout(singleServer.timeout)
                    .setConnectTimeout(singleServer.connectTimeout)
                    .setIdleConnectionTimeout(singleServer.idleConnectionTimeout)
                    .setPassword(singleServer.password);
        }

        return Redisson.create(config);
    }

    @Data
    private static class SingleServerConfigProp {
        private String address;
        private int connectionMinimumIdleSize = 10;
        private int idleConnectionTimeout = 10000;
        private int connectTimeout = 10000;
        private int timeout = 3000;
        private int retryAttempts = 3;
        private int retryInterval = 1500;
        private String password = null;
        private int subscriptionsPerConnection = 5;
        private String clientName = null;
        private int subscriptionConnectionMinimumIdleSize = 1;
        private int subscriptionConnectionPoolSize = 50;
        private int connectionPoolSize = 64;
        private int database = 0;
        private int dnsMonitoringInterval = 5000;
    }
}
*/
