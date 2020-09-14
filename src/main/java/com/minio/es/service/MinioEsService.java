package com.minio.es.service;

import com.minio.es.vo.SearchVo;
import com.minio.es.vo.UploadFileVo;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * @author ：yeyh
 * @date ：Created in 2020/8/27 10:58
 * @description：
 * @modified By：
 */
@Service
public interface MinioEsService {

    Map uploadFile(UploadFileVo uploadFileVo);

    Map getFile(String bucket, String objectPath);

    Map downloadFile(String bucket, String objectPath, String dest);

    Map getObjectUrl(String bucketName, String objectName);

    Map removeObjects(String bucket, String[] objects);

    Map listBuckets();

    Map makeBucket(String bucket);

    Map removeBucket(String[] buckets, Boolean forceDelete);

    Map listObjects(String bucket,String prefix);

    Map removeObject(String bucket, String object);

    Map search(SearchVo searchVo);
}
