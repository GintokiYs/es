package com.minio.es.service.impl;

import com.minio.es.common.status.ResponseStatus;
import com.minio.es.mapper.MinioEsMapper;
import com.minio.es.service.MinioEsService;
import com.minio.es.common.status.ResponseObject;
import com.minio.es.vo.SearchVo;
import com.minio.es.vo.UploadFileVo;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import okio.BufferedSource;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ：yeyh
 * @date ：Created in 2020/8/31 10:30
 * @description：
 * @modified By：
 */
@Service("minioEsService")
public class MinioEsServiceImpl implements MinioEsService {
    Logger logger = LoggerFactory.getLogger(MinioEsServiceImpl.class);

    @Autowired
    private MinioEsMapper minioEsMapper;

    MinioClient minioClient = MinioClient.builder()
            .endpoint("http://192.168.11.19:9000")
            .credentials("minio", "minio123")
            .build();

    RestHighLevelClient esClient = new RestHighLevelClient(RestClient.builder(
            //new HttpHost(host,port,"http")
            //前端传值
            new HttpHost("192.168.186.131", 9200, "http")));

    @Override
    public Map listBuckets() {
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            ResponseObject ok = ResponseObject.ok(ResponseStatus.COMMON_OK);
            ArrayList<Map<String, String>> maps = new ArrayList<>();
            for (Bucket bucket : buckets) {
                HashMap<String, String> map = new HashMap<>();
                map.put("bucketName", bucket.name());
                map.put("createTime", bucket.creationDate().toString());
                maps.add(map);
            }
            Collections.sort(maps, new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    if (o1.get("bucketName").compareTo(o2.get("bucketName")) > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            ok.put("data", maps);
            return ok;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    @Override
    public Map makeBucket(String bucket) {
        try {
            Boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (bucketExists) {
                return ResponseObject.error(7, "目录已存在");
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                return ResponseObject.ok(ResponseStatus.COMMON_OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    @Override
    public Map removeBucket(String[] buckets, Boolean forceDelete) {
        ArrayList<String> errorArrayList = new ArrayList<>();
        for (String bucket : buckets) {
            try {
                if (forceDelete) {
                    Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).recursive(true).build());
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (Result<Item> result : results) {
                        arrayList.add(result.get().objectName());
                    }
                    Map map = removeObjects(bucket, arrayList.toArray(new String[arrayList.size()]));
                    if (map.get("code").equals(1)) {
                        return map;
                    }
                }
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
            } catch (Exception e) {
                logger.error(e.getMessage());
                errorArrayList.add(bucket + "目录删除失败");
            }
        }
        int size = errorArrayList.size();
        if (size > 0) {
            return ResponseObject.error(7, StringUtils.join(errorArrayList, ","));
        } else {
            return ResponseObject.ok(ResponseStatus.COMMON_OK);
        }
    }

    @Override
    public Map listObjects(String bucket, String prefix) {
        try {
            int length = prefix.split("/").length;
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucket).recursive(true).prefix(prefix).build());
            ResponseObject ok = ResponseObject.ok(ResponseStatus.COMMON_OK);
            ArrayList<HashMap<String, String>> maps = new ArrayList<>();
            for (Result<Item> result : results) {
                HashMap<String, String> map = new HashMap<>();
                Item item = result.get();
                String name = item.objectName().split("/")[length];
                map.put("name", name);
                if (name.split("\\.").length > 1) {
                    map.put("lastModifiedTime", item.lastModified().plusHours(8).toString());
                }
                maps.add(map);
            }
            Collections.sort(maps, new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    if (o1.get("name").compareTo(o2.get("name")) > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            ok.put("data", maps);
            return ok;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseObject.error(7, e.toString());
        }
    }

    public Map createIndex(UploadFileVo uploadFileVo) {
        try {
            String object = uploadFileVo.getObject();
            String bucket = uploadFileVo.getBucket();
            String filePath = uploadFileVo.getFilePath();
            List<String> labels = uploadFileVo.getLabel();
            String[] fileNameSplits = object.split("\\.");
            String suffix = fileNameSplits[fileNameSplits.length - 1].toLowerCase();
            String[] objectSplits = object.split("/");
            String fileNameWithSuffix = objectSplits[objectSplits.length - 1];
            String fileName = fileNameWithSuffix.substring(0, fileNameWithSuffix.lastIndexOf(".")).toLowerCase();


            //todo 根据filePath的后缀判断该存入文档index、图片index、音频index（不走文档内容读取插件）
            String indexName = "minio_other_index";
            String docs = "docx,pdf,doc,txt,xml,rtf,dot,dotm";
            String photo = "bmp,jpg,jpeg,png,gif";
            String video = "avi,mp4,rmvb,flv,wmv,asf";

            String id = UUID.randomUUID().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", id);
            hashMap.put("label", labels);
            hashMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            hashMap.put("url", "/" + bucket + "/" + object);
            hashMap.put("filename", fileName);

            if (docs.contains(suffix)) {
                indexName = "minio_doc_index";
                FileInputStream inputFromFile = new FileInputStream(filePath);
                byte[] src = new byte[inputFromFile.available()];
                inputFromFile.read(src);
                String encodedBytes = Base64.getEncoder().encodeToString(src);
                String base64 = encodedBytes;

                hashMap.put("data", base64);
                IndexRequest indexRequest = new IndexRequest(indexName).id(id)
                        //配置管道组件simple_attachment
                        .setPipeline("doc_attachment")
                        .source(hashMap);
                indexRequest.opType(DocWriteRequest.OpType.CREATE);
                //使用elasticsearch.client存储数据
                esClient.index(indexRequest, RequestOptions.DEFAULT);
                return ResponseObject.ok(ResponseStatus.COMMON_OK);
            } else if (photo.contains(suffix)) {
                indexName = "minio_photo_index";
            } else if (video.contains(suffix)) {
                indexName = "minio_video_index";
            }
            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest(indexName).id(id).source(hashMap));
            BulkResponse bulkResponse = esClient.bulk(request, RequestOptions.DEFAULT);
            if (!bulkResponse.hasFailures()) {
                return ResponseObject.ok(ResponseStatus.COMMON_OK);
            } else {
                return ResponseObject.error(ResponseStatus.COMMON_FAIL);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    @Override
    public Map uploadFile(UploadFileVo uploadFileVo) {
        try {
            String bucket = uploadFileVo.getBucket();
            boolean isExist =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
            minioClient.putObject(bucket, uploadFileVo.getObject(), uploadFileVo.getFilePath(), null);
            Map map = createIndex(uploadFileVo);
            if ("1".equals(map.get("code"))) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(uploadFileVo.getObject()).build());
                return ResponseObject.error(7, "索引构建失败，删除已上传文件");
            }
            ResponseObject ok = ResponseObject.ok(ResponseStatus.COMMON_OK);
            return ok;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    @Override
    public Map getFile(String bucket, String objectPath) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectPath)
                        .build())) {

            byte[] by = new byte[81920];
            int len;
            while ((len = stream.read(by)) != -1) {

            }
//            BufferedSource source = (BufferedSource) stream;
//            byte[] bytes = source.readByteArray();
            ResponseObject ok = ResponseObject.ok(ResponseStatus.COMMON_OK);
            ok.put("data", by);
            return ok;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseObject.error(7, e.toString());
        }
    }

    @Override
    public Map downloadFile(String bucket, String objectPath, String dest) {
        try {
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectPath)
                            .filename(dest)
                            .build());
            return ResponseObject.ok(ResponseStatus.COMMON_OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    @Override
    public Map getObjectUrl(String bucketName, String objectFileName) {
        String url = null;
        try {
            url = minioClient.getObjectUrl(bucketName, objectFileName);
            ResponseObject ok = ResponseObject.ok(ResponseStatus.COMMON_OK);
            ok.put("data", url);
            return ok;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    @Override
    public Map removeObjects(String bucket, String[] objects) {
        ArrayList<String> errorArrayList = new ArrayList<>();
        for (String object : objects) {
            Map map = removeObject(bucket, object);
            if ("1".equals(map.get("code"))) {
                errorArrayList.add("/" + bucket + "/" + object + " 删除失败");
            }
        }
        int size = errorArrayList.size();
        if (size > 0) {
            ResponseObject error = ResponseObject.error(ResponseStatus.COMMON_FAIL);
            error.put("data", errorArrayList);
            return error;
        } else {
            return ResponseObject.ok(ResponseStatus.COMMON_OK);
        }
    }

    @Override
    public Map removeObject(String bucket, String object) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(object).build());
            String url = "/" + bucket + "/" + object;
            String[] strings = object.split("\\.");
            String suffix = strings[strings.length - 1];
            String indexName = "minio_other_index";
            String docs = "docx,pdf,doc,txt,xml,rtf,dot,dotm";
            String photo = "bmp,jpg,jpeg,png,gif";
            String video = "avi,mp4,rmvb,flv,wmv,asf";
            if (docs.contains(suffix)) {
                indexName = "minio_doc_index";
            } else if (photo.contains(suffix)) {
                indexName = "minio_photo_index";
            } else if (video.contains(suffix)) {
                indexName = "minio_video_index";
            }
            SearchHits hits = searchHits(indexName, "url", url, null, 20, 1);
            for (SearchHit hit : hits.getHits()) {
                DeleteRequest deleteRequest = new DeleteRequest(indexName, hit.getId());
                esClient.delete(deleteRequest, RequestOptions.DEFAULT);
            }
            return ResponseObject.ok(ResponseStatus.COMMON_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    @Override
    public Map search(SearchVo searchVo) {
        String type = searchVo.getType();
        String keyword = searchVo.getKeyword();
        List<String> labels = searchVo.getLabels();
        int pageSize = searchVo.getPageSize();
        int page = searchVo.getPage();
        String index;
        switch (type) {
            case "doc":
                index = "minio_doc_index";
                break;
            case "photo":
                index = "minio_photo_index";
                break;
            case "video":
                index = "minio_video_index";
                break;
            default:
                index = "minio_other_index";
        }
        try {
            SearchHits searchHits;
            List<Map<String, Object>> list = new ArrayList<>();
            String key;
            if ("doc".equals(type)) {
                key = "attachment.content";
                searchHits = searchHits(index, key, keyword, labels, pageSize, page);
                for (SearchHit hit : searchHits.getHits()) {
                    Text[] fragments = hit.getHighlightFields().get(key).getFragments();
                    String content = "";
                    for (Text fragment : fragments) {
                        content += fragment + "...\n";
                    }
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    sourceAsMap.put("hlcontent", content);
                    sourceAsMap.remove("attachment");
                    list.add(sourceAsMap);
                }
            } else {
                key = "filename";
                searchHits = searchHits(index, key, keyword, labels, pageSize, page);
                for (SearchHit hit : searchHits.getHits()) {
                    list.add(hit.getSourceAsMap());
                }
            }
            String article = list.toString();
            logger.info(article);
            ResponseObject ok = ResponseObject.ok(ResponseStatus.COMMON_OK);
            ok.put("data", article);
            return ok;
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseObject.error(ResponseStatus.COMMON_FAIL);
        }
    }

    public SearchHits searchHits(String index, String key, String value, List<String> labels, int pageSize, int page) throws IOException {
//        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(key, value);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //以空格分隔关键字，多条件查询
        if (!value.isEmpty()) {
            String[] splits = value.split(" ");
            for (String keyword : splits) {
                boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(key, keyword));
//                boolQueryBuilder.must(QueryBuilders.matchQuery(key, keyword));
            }
        }
        if (labels != null && !labels.isEmpty()) {
            for (String label : labels) {
                boolQueryBuilder.must(QueryBuilders.termQuery("label", label));
            }
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(pageSize * (page - 1) > 0 ? pageSize * (page - 1) : 0);
        sourceBuilder.size(pageSize * page);
        sourceBuilder.timeout(new TimeValue(30, TimeUnit.SECONDS));
        //设置高亮显示,字体设置为红色,包含关键字后40个字符
        HighlightBuilder highlightBuilder = new HighlightBuilder().field(key)
                .fragmentSize(260)
                .requireFieldMatch(false)
                .boundaryMaxScan(100);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse;
        List<Map<String, Object>> list = new ArrayList<>();
        searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        return searchResponse.getHits();
    }
}
