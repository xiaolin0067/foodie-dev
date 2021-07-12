package com.zzlin.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zlin
 * @date 20210712
 * 注意：@Document的shards与replicas属性设置无效
 */
@Document(indexName = "stu", type = "_doc", shards = 3, replicas = 0)
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

    @Field(store = true)
    private Float money;

    @Field(store = true, type = FieldType.Keyword)
    private String sign;

    @Field(store = true)
    private String description;

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

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
