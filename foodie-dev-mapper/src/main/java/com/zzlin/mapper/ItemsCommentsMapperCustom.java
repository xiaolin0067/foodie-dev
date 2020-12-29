package com.zzlin.mapper;

import com.zzlin.pojo.CommentsLevelCount;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zlin
 * @date 20201229
 */
public interface ItemsCommentsMapperCustom {

    @Select("select comment_level as commentsLevel,COUNT(comment_level) as commentsCount from items_comments where item_id = #{itemId} GROUP BY comment_level;")
    List<CommentsLevelCount> queryCommentsLevelCount(String itemId);
}
