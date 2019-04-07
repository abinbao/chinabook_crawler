package com.ye.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ye.constant.Constant;
import com.ye.model.ChinaBookModel;

/**
 * 中国图书网 图书信息入库
 */
@Mapper
public interface ChinaBookDao {

    /**
     * @param
     * @return
     */
    @Insert("insert into china_book_info (\n" + "            `id`,\n" + "            `image_url`,\n"
            + "            `book_name`,\n" + "            `detail_url`,\n" + "            `author_info`,\n"
            + "            `publish_time`,\n" + "            `publisher`,\n" + "            `sell_price`,\n"
            + "            `discount`,\n" + "            `price_tit`,\n" + "            `recoLagu`,\n"
            + "            `category1`,\n" + "            `category2`,\n" + "            `insert_time`,\n"
            + "            `update_time`\n" + " ,`origin_url`       )\n" + "        values(\n" + "            #{id},\n"
            + "            #{imageUrl},\n" + "            #{bookName},\n" + "            #{detailUrl},\n"
            + "            #{authorInfo},\n" + "            #{publishTime},\n" + "            #{publisher},\n"
            + "            #{sellPrice},\n" + "            #{discount},\n" + "            #{priceTit},\n"
            + "            #{recolagu},\n" + "            #{category1},\n" + "            #{category2},\n"
            + "            #{insertTime},\n" + "            #{updateTime}\n" + ",   #{originUrl}     )")
    int insert(ChinaBookModel model);

    // 翻页
    @Select({ "<script>", "select count(*) from china_book_info", "</script>" })
    int count(ChinaBookModel model);

    /**
     * 根据 id 查询图书信息
     * 
     * @param id
     * @return
     */
    @Select("select * from china_book_info where id = #{id}")
    ChinaBookModel queryById(Integer id);

    /**
     * 翻页查询
     * 
     * @param model
     * @return
     */
    @Select({ "<script>", "select * from china_book_info ",
            "order by " + Constant.OrderByCreatetimeDesc + ",insert_time desc", "limit #{start},#{end}", "</script>" })
    List<ChinaBookModel> list(ChinaBookModel model);

    /**
     * 按类别1 统计书的数量 前 4
     * 
     * @return
     */
    @Select("select count(*) as cnt,category1 from china_book_info group by category1 order by  cnt desc limit 4")
    List<Map<String, Object>> getCountByCategory1();

    /**
     * 按类别1 统计书的数量 前 10
     */
    @Select("select count(*) as cnt,category2 from china_book_info group by category2 order by  cnt desc limit 10;")
    List<Map<String, Object>> getCountByCategory2();

    /**
     * 按售价 前 10
     */
    @Select("select * from china_book_info discount order by sell_price desc limit 10;")
    List<ChinaBookModel> getRankBySellPrice();

    /**
     * 按折扣 前 10
     */
    @Select("select * from china_book_info discount order by discount asc limit 10;")
    List<ChinaBookModel> getRankByDiscount();

    /**
     * 删除用户
     * 
     * @param patient
     * @return
     */
    @Delete("delete from china_book_info where id = #{id}")
    int deleteById(ChinaBookModel model);
}
