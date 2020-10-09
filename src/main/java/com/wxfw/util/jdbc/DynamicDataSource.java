package com.wxfw.util.jdbc;

import com.wxfw.util.StringHelper;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态数据源,根据key切换不同数据源 , 只有所有请求都是读请求时候才会走cluster , 有写请求就立刻切换到master , 并且持续到事务结束
 * <p>
 * DynamicDataSource dynamicDataSource = new DynamicDataSource();
 * Map<Object, Object> dataSources = new HashMap<Object , Object>();
 * dataSources.put("master", masterDataSource);
 * dataSources.put("cluster1", clusterDataSource1);
 * dynamicDataSource.setTargetDataSources(dataSources);
 *
 * @author gaohw
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String BEAN_NAME = "dynamicDataSource";

    public static final String MASTER_PREFIX = "master";
    public static final String CLUSTER_PREFIX = "cluster";

    private AtomicInteger masterCount = new AtomicInteger(0);
    private AtomicInteger clusterCount = new AtomicInteger(0);

    private String defaultKey;

    private ConcurrentHashMap<String, Object> masterPool = new ConcurrentHashMap<String, Object>();
    private ConcurrentHashMap<String, Object> clusterPool = new ConcurrentHashMap<String, Object>();

    public void putMaster(DataSource dataSource) {
        this.putMaster(dataSource, false);
    }

    public void putMaster(DataSource dataSource, boolean isDefault) {
        Integer index = masterCount.addAndGet(1);
        String key = MASTER_PREFIX + index;
        if (StringHelper.isEmpty(defaultKey) || isDefault) {
            this.defaultKey = key;
        }
        this.masterPool.put(key, dataSource);
    }

    public void putCluster(DataSource dataSource) {
        Integer index = clusterCount.addAndGet(1);
        this.clusterPool.put(CLUSTER_PREFIX + index, dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String key = DynamicDataSourceHolder.getDataSource();
        if (key == null || key.trim().equals("")) {
            return this.defaultKey;
        }
        // 如果是从库的情况,其他情况都走主库
        if (CLUSTER_PREFIX.equals(key)) {
            // 简单负载均衡....超级简单.....
            if (clusterCount.intValue() == 1) {
                return key + clusterCount;
            }
            // 从DataSource池中随机取一个
            int randomIndex = new Random().nextInt(clusterCount.intValue()) + 1;
            return key + randomIndex;
        } else {
            if (masterCount.intValue() == 1) {
                return key + masterCount;
            }
            int randomIndex = new Random().nextInt(masterCount.intValue()) + 1;
            return key + randomIndex;
        }
    }

    public void setTargetDataSources() {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.putAll(masterPool);
        if (!clusterPool.isEmpty()) {
            targetDataSources.putAll(clusterPool);
        }
        super.setTargetDataSources(targetDataSources);
    }

}
