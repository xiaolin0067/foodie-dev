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

    /**
     * 一般来说不建议通过Java ElasticsearchTemplate client客户端来对索引进行管理（创建索引，更新映射，删除索引）
     * 相当于数据库一样，程序只进行对数据的处理操作，不进行表的创建与修改删除
     * 同时客户端对ES的索引进行管理也存在问题：
     * 1、@Document的shards与replicas属性设置无效，无法设置分片与副本
     * 2、@Field的FieldType类型不灵活
     */
    @Test
    public void createIndexStu(){
        // 插入document数据，若索引不存在则进行创建，若存在则只进行新增
        Stu stu = new Stu(1002L, "hello", 19);
        // 扩展字段的mapping的更新
        stu.setMoney(66.6f);
        stu.setSign("sign-1002");
        stu.setDescription("a man can see the blood directly who was a real hero");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    @Test
    public void deleteIndexStu(){
        esTemplate.deleteIndex(Stu.class);
    }

}
