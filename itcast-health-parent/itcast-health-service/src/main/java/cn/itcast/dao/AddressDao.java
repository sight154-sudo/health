package cn.itcast.dao;

import cn.itcast.pojo.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author fzl
 * @Date 2021/8/20 19:07
 */
//@Mapper
public interface AddressDao {
    /**
     * 新增分院
     *
     * @param address
     */
//    @Insert("insert into t_address values(null,#{code},#{name},#{address},#{detail},#{coordinate},#{enable})")
    Integer add(Address address);

    /**
     * 根据id删除分院
     *
     * @param id
     */
    @Delete("delete from t_address where id=#{id}")
    Integer deleteById(Integer id);

    /**
     * 根据id查询分院
     *
     * @param id
     * @return
     */
    @Select("select * from t_address where id = #{id}")
    Address findById(int id);

    /**
     * 更新分院数据
     *
     * @param address
     */
    @Update("update t_address set code=#{code},name=#{name},address=#{address},detail=#{detail},coordinate=#{coordinate},enable=#{enable} where id=#{id}")
    Integer edit(Address address);

    /**
     * 查询所有分院信息
     *
     * @return
     */
//    @Select("select * from t_address")
//    List<Address> findAll();

//    @Select("select * from t_address")
    List<Address> findAll();

    /**
     * 更改是否启用
     * @param id
     * @param enable
     * @return
     */
    @Update("update t_address set enable=#{enable} where id=#{id}")
    Integer editByEnable(Integer id, Integer enable);
}
