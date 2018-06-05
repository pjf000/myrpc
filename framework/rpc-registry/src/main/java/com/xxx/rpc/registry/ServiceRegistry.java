package com.xxx.rpc.registry;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 服务注册
 *
 * @author huangyong
 * @since 1.0.0
 */
public class ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String registryAddress;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }
    /**
     * 新版注册方式
     * @param intf
     * @param serverAddress
     */
    public void register(String intf,String serverAddress) {
        if (intf != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                createNode(zk, intf,serverAddress);
            }
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            LOGGER.error("", e);
        }catch (InterruptedException ie){
            LOGGER.error("", ie);
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException  e) {
            LOGGER.error("", e);
        }catch (InterruptedException ie){
            LOGGER.error("", ie);
        }
    }

    private void createNode(ZooKeeper zk, String intf,String serverAddress) {
        try {
            //创建永久的根节点
            if(zk.exists(Constant.ZK_REGISTRY_PATH,true)==null){
                zk.create(Constant.ZK_REGISTRY_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //创建永久的接口包名节点
            if(zk.exists(Constant.ZK_REGISTRY_PATH+"/"+ intf,true)==null){
                zk.create(Constant.ZK_REGISTRY_PATH+"/"+ intf, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //创建暴露intf服务的ip+port 临时节点
            String path = zk.create(Constant.ZK_REGISTRY_PATH+"/"+ intf+"/"+serverAddress, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            LOGGER.debug("create zookeeper node ({})", path);
        } catch (KeeperException  e) {
            LOGGER.error("", e);
        }catch (InterruptedException ie){
            LOGGER.error("", ie);
        }
    }
}