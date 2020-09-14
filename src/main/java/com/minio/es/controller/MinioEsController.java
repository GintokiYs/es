package com.minio.es.controller;


import com.minio.es.service.MinioEsService;
import com.minio.es.vo.SearchVo;
import com.minio.es.vo.UploadFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author ：yeyh
 * @date ：Created in 2020/8/27 10:56
 * @description：
 * @modified By：
 */
@RestController()
@RequestMapping("/minio")
@Api(value = "minio controller", tags = {"minio的Api文档"})
public class MinioEsController {

    @Autowired
    private MinioEsService minioEsService;

    @GetMapping("/bucket/listBuckets")
    @ApiOperation(value = "查看minio桶目录", notes = "查看minio桶目录")
    public Map listBuckets() {
        return minioEsService.listBuckets();
    }

    @PutMapping("/bucket/makeBucket/{bucket}")
    @ApiOperation(value = "创建minio桶目录", notes = "创建minio桶目录")
    public Map makeBucket(@PathVariable("bucket") @ApiParam(name = "bucket", value = "要创建的minio桶目录名", required = true) String bucket) {
        return minioEsService.makeBucket(bucket);
    }

    @PutMapping("/bucket/removeBucket")
    @ApiOperation(value = "删除minio桶目录", notes = "查看minio桶目录")
    public Map removeBucket(@RequestBody @ApiParam(name = "buckets", value = "要删除的minio桶目录数组", required = true) String[] buckets,
                            @RequestParam @ApiParam(name = "forceDelete", value = "若目录下还有文件是否强制删除", required = true) Boolean forceDelete) {
        return minioEsService.removeBucket(buckets, forceDelete);
    }

    @GetMapping("/bucket/listObjects")
    @ApiOperation(value = "查询指定目录下的对象文件", notes = "查询指定目录下的对象文件")
    public Map listObjects(@ApiParam(name = "bucket", value = "minio桶目录", required = true) @RequestParam String bucket,
                           @ApiParam(name = "prefix", value = "minio对象目录", required = true) @RequestParam String prefix) {
        return minioEsService.listObjects(bucket, prefix);
    }

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件并构建Es文档", notes = "上传文件并构建Es文档")
    public Map uploadFile(@ApiParam(name = "文件上传Vo", value = "文件上传Vo", required = true) @RequestBody UploadFileVo uploadFileVo) {
        return minioEsService.uploadFile(uploadFileVo);
    }

    @GetMapping("/getFile/{bucket}/{objectPath}")
    @ApiOperation(value = "获取minio对象的二进制数组", notes = "获取minio对象的二进制数组")
    public Map getFile(@PathVariable("bucket") @ApiParam(name = "bucket", value = "minio桶目录", required = true) String bucket,
                       @PathVariable("objectPath") @ApiParam(name = "objectPath", value = "minio对象文件名", required = true) String objectPath) {
        return minioEsService.getFile(bucket, objectPath);
    }

    @GetMapping("/downloadFile")
    @ApiOperation(value = "下载minio对象文件至本地路径", notes = "下载minio对象文件至本地路径")
    public Map downloadFile(@RequestParam @ApiParam(name = "bucket", value = "minio桶目录", required = true) String bucket,
                            @RequestParam @ApiParam(name = "objectPath", value = "minio对象文件名", required = true) String objectPath,
                            @RequestParam @ApiParam(name = "dest", value = "本地路径名", required = true) String dest) {
        return minioEsService.downloadFile(bucket, objectPath, dest);
    }

    @GetMapping("/getObjectUrl")
    @ApiOperation(value = "获取对象url路径", notes = "获取对象url路径")
    public Map getObjectUrl(@RequestParam @ApiParam(name = "bucket", value = "minio桶目录", required = true) String bucket,
                            @RequestParam @ApiParam(name = "objectName", value = "minio对象文件名", required = true) String objectName) {
        return minioEsService.getObjectUrl(bucket, objectName);
    }

    @PostMapping("/search")
    @ApiOperation(value = "文件检索", notes = "文件检索")
    //@PathVariable("type") @ApiParam(name = "type", value = "搜索类别（doc,photo,video,other）", required = true) String type,
//    @PathVariable("keyword") @ApiParam(name = "keyword", value = "搜索关键字", required = true) String keyword,
//    @PathVariable("pageSize") @ApiParam(name = "pageSize", value = "每页显示数量", required = true) String pageSize,
//    @PathVariable("page") @ApiParam(name = "page", value = "页数", required = true) String page) {
    public Map search(@ApiParam(name = "搜索条件Vo", value = "搜索条件Vo", required = true) @RequestBody SearchVo searchVo) {
        return minioEsService.search(searchVo);
    }

    @PostMapping("/removeObjects")
    @ApiOperation(value = "删除minio多个对象文件并删除Es对应文档", notes = "删除minio多个对象文件并删除Es对应文档")
    public Map removeObjects(@ApiParam(name = "bucket", value = "minio桶目录", required = true) String bucket,
                             @RequestBody @ApiParam(name = "objects", value = "minio对象文件数组", required = true) String[] objects) {
        return minioEsService.removeObjects(bucket, objects);
    }

    @DeleteMapping("/removeObject")
    @ApiOperation(value = "删除minio对象文件并删除Es对应文档", notes = "删除minio对象文件并删除Es对应文档")
    public Map removeObjects(@RequestParam @ApiParam(name = "bucket", value = "minio桶目录", required = true) String bucket,
                             @RequestParam @ApiParam(name = "object", value = "minio对象文件名", required = true) String object) {
        return minioEsService.removeObject(bucket, object);
    }


}
