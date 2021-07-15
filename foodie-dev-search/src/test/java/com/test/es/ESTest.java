package com.test.es;

import com.zzlin.es.SearchApp;
import com.zzlin.es.pojo.Stu;
import com.zzlin.utils.JsonUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlin
 * @date 20210712
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApp.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 一般来说不建议通过Java ElasticsearchTemplate client客户端来对索引进行管理（创建索引，更新映射，删除索引）
     * 相当于数据库一样，程序只进行对数据的处理操作，不进行表的创建与修改删除
     * 同时客户端对ES的索引进行管理也存在问题：
     * 1、@Document的shards与replicas属性设置无效，无法设置分片与副本
     * 2、@Field的FieldType类型不灵活
     *
     * 创建索引，添加文档，生成mapping
     */
    @Test
    public void createIndexStu(){
        // 插入document数据，若索引不存在则进行创建，若存在则只进行新增
        Stu stu = new Stu(1006L, "hello-6", 23);
        // 扩展字段的mapping的更新
        stu.setMoney(668.66f);
        stu.setSign("sign-1006");
        stu.setDescription("the blood is yellow");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndexStu(){
        esTemplate.deleteIndex(Stu.class);
    }

    /**
     * 更新文档
     */
    @Test
    public void updateStuDoc() {
        Map<String, Object> map = new HashMap<>();
//        map.put("description","this is new description...");
        map.put("money",6666.6f);
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(map);
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1005")
                .withIndexRequest(indexRequest)
                .build();
        esTemplate.update(updateQuery);
    }

    /**
     * 查询文档
     */
    @Test
    public void getStuDoc() {
        GetQuery getQuery = new GetQuery();
        getQuery.setId("1002");
        Stu stu = esTemplate.queryForObject(getQuery, Stu.class);
        System.out.println(stu.toString());
    }

    /**
     * 删除文档
     */
    @Test
    public void deleteStuDoc() {
        esTemplate.delete(Stu.class, "1002");
    }

    /**
     * 分页搜索文档
     */
    @Test
    public void searchStuDoc() {
        Pageable pageable = PageRequest.of(0, 2);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("sign", "1002"))
                .withPageable(pageable)
                .build();
        AggregatedPage<Stu> stuPage = esTemplate.queryForPage(query, Stu.class);
        System.out.println(stuPage.getTotalPages());
        stuPage.getContent().forEach(e -> System.out.println(JsonUtils.objectToJson(e)));
    }

    /**
     * 高亮显示搜索结果关键字
     */
    @Test
    public void highlightStuDoc() {
        String preTag = "<font color='red'>";
        String postTag = "</font>";
        Pageable pageable = PageRequest.of(0, 10);
        SortBuilder moneySortBuilder = new FieldSortBuilder("money").order(SortOrder.DESC);
        SortBuilder ageSortBuilder = new FieldSortBuilder("age").order(SortOrder.ASC);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "blood"))
                // 高亮搜索条件
                .withHighlightFields(
                        new HighlightBuilder.Field("description")
                        .preTags(preTag)
                        .postTags(postTag)
                )
                .withSort(moneySortBuilder)
                .withSort(ageSortBuilder)
                .withPageable(pageable)
                .build();
        // 修改映射结果集SearchResultMapper
        AggregatedPage<Stu> stuPage = esTemplate.queryForPage(query, Stu.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Stu> stuList = new ArrayList<>();
                for (SearchHit hit : searchResponse.getHits()) {
                    Stu stu = JsonUtils.jsonToPojo(hit.getSourceAsString(), Stu.class);
                    String highDesc =  hit.getHighlightFields().get("description").getFragments()[0].toString();
                    stu.setDescription(highDesc);
                    stuList.add(stu);
                }
                if (!stuList.isEmpty()) {
                    // 通过此构造方法直接返回page会丢失总页数等信息
                    return new AggregatedPageImpl<>((List<T>)stuList);
                }
                return null;
            }
        });
        System.out.println(stuPage.getTotalPages());
        stuPage.getContent().forEach(e -> System.out.println(JsonUtils.objectToJson(e)));
    }
}
