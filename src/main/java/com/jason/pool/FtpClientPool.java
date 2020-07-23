package com.jason.pool;

import com.jason.config.FtpClientConfigure;
import com.jason.factory.FtpClientFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.net.ftp.FTPClient;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jason
 * @Date: 2020/7/23 13:20
 * @Description: ftp连接池
 */
public class FtpClientPool implements ObjectPool<FTPClient> {

    private final static int DEFAULT_POOL_SIZE = 15;
    private final BlockingQueue<FTPClient> pool;
    private final FtpClientFactory factory;

    public FtpClientPool(FtpClientFactory factory) throws Exception {
        this(DEFAULT_POOL_SIZE,factory);
    }

    public FtpClientPool(int poolSize, FtpClientFactory factory) throws Exception {
        this.factory = factory;
        this.pool = new ArrayBlockingQueue<>(poolSize);
        initPool(poolSize);
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:48
    * @params [configure]
    * 自动构建工厂
    * @return
    */
    public FtpClientPool(FtpClientConfigure configure) throws Exception {
        this(new FtpClientFactory(configure));
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:48
    * @params [poolSize, configure]
    * 自动构建工厂
    * @return
    */
    public FtpClientPool(int poolSize,FtpClientConfigure configure) throws Exception {
        this(poolSize,new FtpClientFactory(configure));
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:48
    * @params [poolSize]
    * 初始化所有连接
    * @return void
    */
    private void initPool(int poolSize) throws Exception {
        for (int i = 0; i < poolSize; i++) {
            addObject();
        }
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:48
    * @params []
    * 新增一个连接
    * @return void
    */
    @Override
    public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
        pool.offer(factory.create(),3, TimeUnit.SECONDS);
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:48
    * @params []
    *
    * @return org.apache.commons.net.ftp.FTPClient
    */
    @Override
    public FTPClient borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
        FTPClient client = pool.take();
        if(client == null){
            client = factory.create();
            addObject();
        }else if(!factory.validateObject(client)){
            this.invalidateObject(client);
            client = factory.create();
            addObject();
        }
        return client;
    }

    @Override
    public void clear() throws Exception, UnsupportedOperationException {
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:49
    * @params []
    * 关闭方法
    * @return void
    */
    @Override
    public void close() {
        while (pool.iterator().hasNext()){
            try {
                FTPClient ftpClient = pool.take();
                factory.destroyObject(ftpClient);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getNumActive() {
        return 0;
    }

    @Override
    public int getNumIdle() {
        return 0;
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:49
    * @params [ftpClient]
    * 移除失效连接
    * @return void
    */
    @Override
    public void invalidateObject(FTPClient ftpClient) throws Exception {
        pool.remove(ftpClient);
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:49
    * @params [ftpClient]
    * 归还
    * @return void
    */
    @Override
    public void returnObject(FTPClient ftpClient) throws Exception {
        if(ftpClient != null && !pool.offer(ftpClient,3,TimeUnit.SECONDS)){
            factory.destroyObject(ftpClient);
        }
    }
}
