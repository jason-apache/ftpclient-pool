package com.jason.config;

import org.apache.commons.net.ftp.FTPClient;

/**
 * @author: Jason
 * @Date: 2020/7/23 13:49
 * @Description:ftp连接配置类
 */
public class FtpClientConfigure {

    private String host;
    /**
     * 默认21端口
     */
    private int port = 21;
    private String username;
    private String password;
    private boolean passiveMode;
    /**
     * 默认字符集
     */
    private String encoding = "UTF-8";
    /**
     * 默认十秒超时
     */
    private int connectTimeout = 10 * 1000;
    private int threadNum;
    /**
     * 默认二进制流
     */
    private int transferFileType = FTPClient.BINARY_FILE_TYPE;
    private boolean renameUploaded;
    private int retryTimes;

    public String getHost() {
        return host;
    }

    public FtpClientConfigure setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public FtpClientConfigure setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public FtpClientConfigure setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FtpClientConfigure setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean getPassiveMode() {
        return passiveMode;
    }

    public FtpClientConfigure setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public FtpClientConfigure setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public FtpClientConfigure setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public FtpClientConfigure setThreadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public int getTransferFileType() {
        return transferFileType;
    }

    public FtpClientConfigure setTransferFileType(int transferFileType) {
        this.transferFileType = transferFileType;
        return this;
    }

    public boolean isRenameUploaded() {
        return renameUploaded;
    }

    public FtpClientConfigure setRenameUploaded(boolean renameUploaded) {
        this.renameUploaded = renameUploaded;
        return this;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public FtpClientConfigure setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    @Override
    public String toString() {
        return "FtpClientConfigure{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passiveMode='" + passiveMode + '\'' +
                ", encoding='" + encoding + '\'' +
                ", clientTimeout=" + connectTimeout +
                ", threadNum=" + threadNum +
                ", transferFileType=" + transferFileType +
                ", renameUploaded=" + renameUploaded +
                ", retryTimes=" + retryTimes +
                '}';
    }
}
