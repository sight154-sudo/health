package cn.itcast.service;

import cn.itcast.pojo.Address;

import java.util.List;

/**
 * @Author fzl
 * @Date 2021/8/20 18:57
 */
public interface AddressService {
    /**
     * 新增分院
     *
     * @param address
     */
    Integer add(Address address);

    /**
     * 分页查询分院信息
     *
     * @param queryPageBean
     * @return
    PageResult QueryByPage(QueryPageBean queryPageBean);*/

    /**
     * 根据id删除分院
     *
     * @param id
     */
    Integer deleteById(Integer id);

    /**
     * 根据id查询分院
     *
     * @param id
     * @return
     */
    Address findById(int id);

    /**
     * 更新分院数据
     *
     * @param address
     */
    Integer edit(Address address);

    /**
     * 查询所有分院信息
     *
     * @return
     */
    List<Address> findAll();

    /**
     * 更改是否启用
     * @param id
     * @param enable
     * @return
     */
    Boolean editByEnable(Integer id, Integer enable);
}
