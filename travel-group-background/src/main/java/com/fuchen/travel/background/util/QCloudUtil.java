package com.fuchen.travel.background.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

/**
 * @author 伏辰
 * @date 2023/1/9
 * 腾讯云对象存储工具类
 */
@Component
public class QCloudUtil {

    /**
     * 上传文件到腾讯云对象存储
     * @param bucketName 对象存储桶名称
     * @param key 键名
     * @param file 上传的文件
     * @param cosRegion 上传的地区
     * @param secretId 密钥id
     * @param secretKey 密钥key
     */
    public void uploadFile(String bucketName, String key, MultipartFile file,
                           String cosRegion, String secretId, String secretKey) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(cosRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        //  生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        //将MultipartFile转为File
        File localFile = transferToFile(file);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
    }

    /**
     * MultipartFile转File
     * @param multipartFile multipartFile文件
     * @return 返回 File 文件
     */
    private File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
