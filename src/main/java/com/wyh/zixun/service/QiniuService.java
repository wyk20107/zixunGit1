package com.wyh.zixun.service;





import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wyh.zixun.util.ZixunUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.util.UUID;

@Service
public class QiniuService {

    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    String accessKey = "7cKxKIpDjg4qL2cisbVvyNevxWJbEO3LQSfZP5ON";
    String secretKey = "s5j7fj-crOud_q7ZUYq37MLMxdZ-L4yXDhiF3hWV";
    String bucket = "wyh-namespace";

    Auth auth = Auth.create(accessKey, secretKey);

    UploadManager uploadManager = new UploadManager();

    public String getUptoken(){
        return auth.uploadToken(bucket);
    }

    public String saveImage(MultipartFile file) throws IOException{
        try{
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0){
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos+1).toLowerCase();
            if (!ZixunUtil.isFileAllowed(fileExt)){
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;

            Response res = uploadManager.put(file.getBytes(),fileName,getUptoken());

            System.out.println(res.toString());
            if (res.isOK() && res.isJson()){
                String key = JSONObject.parseObject(res.bodyString()).get("key").toString();
                return ZixunUtil.QINIU_DOMAIN_PREFIX + key;
            } else{
                logger.error("七牛上传异常"+res.bodyString());
                return null;
            }

        }catch (QiniuException e){
            logger.error("七牛上传图片失败"+e.getMessage());
            return null;
        }
    }
}
