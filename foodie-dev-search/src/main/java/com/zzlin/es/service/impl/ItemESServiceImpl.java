package com.zzlin.es.service.impl;

import com.zzlin.es.pojo.Item;
import com.zzlin.es.service.ItemESService;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.PagedGridResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zlin
 * @date 20210716
 */
@Service
public class ItemESServiceImpl implements ItemESService {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        String preTag = "<font color='red'>";
        String postTag = "</font>";
        Pageable pageable = PageRequest.of(page, pageSize);
        String searchField = "itemName";
        SortBuilder sortBuilder;
        if (sort.equals("c")) {
            sortBuilder = new FieldSortBuilder("sellCounts").order(SortOrder.DESC);
        } else if (sort.equals("p")) {
            sortBuilder = new FieldSortBuilder("price").order(SortOrder.ASC);
        } else {
            sortBuilder = new FieldSortBuilder("itemName.keyword").order(SortOrder.ASC);
        }
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(searchField, keywords))
                // 高亮搜索条件
                .withHighlightFields(
                        new HighlightBuilder.Field(searchField)
                        // 高亮标签一般由前端进行自定义扩充，后端可直接返回默认的标签<em/>，前端对该标签进行自定义样式即可
                        // <style> em {color: #d2364c; font-weight: bold;} </style>
//                                .preTags(preTag)
//                                .postTags(postTag)
                )
                .withSort(sortBuilder)
                .withPageable(pageable)
                .build();
        // 修改映射结果集SearchResultMapper
        AggregatedPage<Item> itemPage = esTemplate.queryForPage(query, Item.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Item> itemList = new ArrayList<>();
                for (SearchHit hit : searchResponse.getHits()) {
                    String highItemName =  hit.getHighlightFields().get(searchField).getFragments()[0].toString();
                    Item item = JsonUtils.jsonToPojo(hit.getSourceAsString(), Item.class);
                    item.setItemName(highItemName);
                    itemList.add(item);
                }
                return new AggregatedPageImpl<>((List<T>)itemList, pageable, searchResponse.getHits().totalHits);
            }
        });
        return new PagedGridResult(
                // controller处减去了1，此处加回
                page + 1,
                itemPage.getTotalPages(),
                itemPage.getTotalElements(),
                itemPage.getContent());
    }
}
