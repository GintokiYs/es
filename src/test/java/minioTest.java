import com.minio.es.common.status.ResponseObject;
import com.minio.es.common.status.ResponseStatus;
import com.minio.es.service.impl.MinioEsServiceImpl;
import com.netflix.servo.util.VisibleForTesting;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ：yeyh
 * @date ：Created in 2020/9/1 11:07
 * @description：
 * @modified By：
 */

public class minioTest {
    MinioClient minioClient = new MinioClient("http://192.168.11.19:9000", "minio", "minio123");
    RestHighLevelClient esClient = new RestHighLevelClient(RestClient.builder(
            //new HttpHost(host,port,"http")
            //前端传值
            new HttpHost("192.168.186.131", 9200, "http")));

    @Test
    public void okioTest() throws Exception {
        String bucket = "test";
        String objectPath = "leimu.jpg";
        InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectPath)
                        .build());
        byte[] by = new byte[8192];
        int len;
        while ((len = stream.read(by)) != -1) {
        }
        System.out.println(by);
    }

    @Test
    public void esQueryTest() throws Exception {
        String bucket = "test";
        String objectPath = "d:\\leimu.jpg";
        String content = "易经";
        String[] split = objectPath.split("\\\\");
        SearchHits searchHits = search("file_index_test", "attachment.content", content);
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchHits.getHits()) {
            list.add(hit.getSourceAsMap());
        }
        String article = list.toString();
        System.out.println(article);
    }
    @Test
    public void esQueryTest2() throws Exception {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("空间");
        MinioEsServiceImpl minioEsService = new MinioEsServiceImpl();
        SearchHits searchHits = minioEsService.searchHits("minio_doc_index", "attachment.content", "算法", labels, 10, 1);
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchHits.getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();
            map.remove("attachment");
            list.add(map);
        }
        String article = list.toString();
        System.out.println(article);
    }

    public SearchHits search(String index, String key, String value) {
        QueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery(key, value);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(100);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse;
        List<Map<String, Object>> list = new ArrayList<>();
        SearchHits searchHits = null;
        try {
            searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            searchHits = searchResponse.getHits();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchHits;
    }


    @Test
    public void listDirObject() throws Exception {
        String prefix = "felt";
        int length = prefix.split("/").length;

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket("test").recursive(true).prefix(prefix).build());
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


    }
    @Test
    public void stringTest(){

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(createTime);
    }
}
