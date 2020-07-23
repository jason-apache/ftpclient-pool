package com.jason.factory;

import com.jason.config.FtpClientConfigure;
import com.jason.exception.FTPClientException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

/**
 * @author: Jason
 * @Date: 2020/7/23 13:28
 * @Description: client对象工厂
 */
public class FtpClientFactory{

    private FtpClientConfigure configure;

    public FtpClientFactory(FtpClientConfigure configure) {
        this.configure = configure;
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:50
    * @params []
    * 根据配置类生成client对象
    * @return org.apache.commons.net.ftp.FTPClient
    */
    public FTPClient create(){
        FTPClient client = new FTPClient();
        client.setConnectTimeout(configure.getConnectTimeout());
        try {
            client.connect(configure.getHost(),configure.getPort());
            int reply = client.getReplyCode();
            //验证是否成功连接
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                return null;
            }
            boolean result = client.login(configure.getUsername(), configure.getPassword());
            if(!result){
                throw new FTPClientException("ftp登录失败！username："+configure.getUsername() + " ； password："+configure.getPassword());
            }
            client.setFileType(configure.getTransferFileType());
            client.setBufferSize(1024);
            client.setControlEncoding(configure.getEncoding());
            if(configure.getPassiveMode()){
                client.enterLocalPassiveMode();
            }
        }catch (IOException | FTPClientException e){
            e.printStackTrace();
        }
        return client;
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:50
    * @params [ftpClient]
    * 销毁client
    * @return void
    */
    public void destroyObject(FTPClient ftpClient) {
        if(null != ftpClient){
            try {
                if(ftpClient.isConnected()){
                    //登出
                    ftpClient.logout();
                }
            }catch (IOException io){
                io.printStackTrace();
            } finally {
                try {
                    //断开连接
                    ftpClient.disconnect();
                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/7/23 14:51
    * @params [ftpClient]
    * 验证client有效性
    * @return boolean
    */
    public boolean validateObject(FTPClient ftpClient) {
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            throw new FTPClientException("Failed to validate client: " + e, e);
        }
    }
}
