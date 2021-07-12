package com.zzlin.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author zlin
 * @date 20210712
 */
@Document(indexName = "stu", type = "_doc")
public class Stu {

    /**
     * @Id 注解可将字段值付给es的自己的文档ID，若不添加该注解ES则会自行生成一个文档的ID
     */
    @Id
    private Long id;

    @Field(store = true)
    private String name;

    @Field(store = true)
    private Integer age;

    public Stu() {
    }

    public Stu(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
