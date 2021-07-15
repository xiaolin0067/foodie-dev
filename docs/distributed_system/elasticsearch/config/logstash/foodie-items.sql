SELECT
 i.id AS itemId,
 i.item_name AS itemName,
 i.sell_counts AS sellCounts,
 ii.url AS imgUrl,
 s.price_discount AS price,
 i.updated_time as updated_time
 FROM
 items i
 LEFT JOIN items_img ii ON i.id = ii.item_id
 LEFT JOIN
 (
 SELECT s.item_id, min( s.price_discount ) AS price_discount FROM items_spec s
 GROUP BY s.item_id
 ) s
 ON ii.item_id = s.item_id
 WHERE ii.is_main = 1 
 and i.updated_time >= :sql_last_value
