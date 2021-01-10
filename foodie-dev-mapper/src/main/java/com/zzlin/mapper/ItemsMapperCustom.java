package com.zzlin.mapper;

import com.zzlin.pojo.vo.ItemCommentVO;
import com.zzlin.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zlin
 * @date 20210105
 */
public interface ItemsMapperCustom {

    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> paramsMap);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);

    List<SearchItemsVO> searchItemsByCatId(@Param("paramsMap") Map<String, Object> paramsMap);
}
