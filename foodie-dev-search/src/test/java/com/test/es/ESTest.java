package com.test.es;

import com.zzlin.es.SearchApp;
import com.zzlin.es.pojo.Stu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zlin
 * @date 20210712
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApp.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Test
    public void createIndexStu(){
        // 插入document数据，若索引不存在则进行创建，若存在则只进行新增
        Stu stu = new Stu(1002L, "hello", 19);
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

}
