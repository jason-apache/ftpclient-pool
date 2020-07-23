import com.jason.config.FtpClientConfigure;
import com.jason.pool.FtpClientPool;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/23 14:04
 * @Description:
 */
public class TestPool {

    private static FtpClientPool pool;

    @Before
    public void beforeStart(){
        FtpClientConfigure config = new FtpClientConfigure();
        config.setHost("192.168.8.113").setUsername("dy").setPassword("123456");
        try {
            pool = new FtpClientPool(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPool() throws Exception {
        FTPClient client = pool.borrowObject();
        client.changeWorkingDirectory("/");
        FTPFile[] ftpFiles = client.listFiles();
        List<FTPFile> list = new ArrayList<>(100);
        for (FTPFile ftpFile : ftpFiles) {
            list.add(ftpFile);
            if(ftpFile.isDirectory()){
                list.addAll(recursionFind(new ArrayList<>(),"/"+ftpFile.getName(),client));
            }
        }
        System.out.println(list);
    }

    public List<FTPFile> recursionFind(List<FTPFile> ftpFileList,String path,FTPClient client) throws IOException {
        boolean changed = client.changeWorkingDirectory(path);
        if(changed){
            FTPFile[] ftpFiles = client.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                ftpFileList.add(ftpFile);
                if(ftpFile.isDirectory()){
                    ftpFileList.addAll(recursionFind(new ArrayList<>(),path+"/"+ftpFile.getName(),client));
                }
            }
        }
        return ftpFileList;
    }
}
