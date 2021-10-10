package cn.itcast.service.impl;

import cn.itcast.dao.AddressDao;
import cn.itcast.pojo.Address;
import cn.itcast.service.AddressService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

/**
 * @Author fzl
 * @Date 2021/8/20 19:04
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;
    /**
     * 添加分院
     * @param address
     */
    @Override
    public Integer add(Address address) {
        return addressDao.add(address);
    }

/*    *//**
     * 分页查询分院
     * @param queryPageBean
     * @return
     *//*
    @Override
    public PageResult QueryByPage(QueryPageBean queryPageBean) {
        //开启分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //根据条件分页查询检查项信息
        Page<Address> page = addressDao.findAll();
        //构建分页数据返回
        return new PageResult(page.getTotal(),page.getResult());
    }*/

    /**
     * 根据id删除分院
     * @param id
     */
    @Override
    public Integer deleteById(Integer id) {
        return addressDao.deleteById(id);
    }

    /**
     * 根据id查询分院
     * @param id
     * @return
     */
    @Override
    public Address findById(int id) {
        return addressDao.findById(id);
    }

    /**
     * 更新分院信息
     * @param address
     */
    @Override
    public Integer edit(Address address) {
        return addressDao.edit(address);
    }

    /**
     * 查询全部分院
     * @return
     */
    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    /**
     * 更改是否启用
     * @param id
     * @param enable
     * @return
     */
    @Override
    public Boolean editByEnable(Integer id, Integer enable) {
        return addressDao.editByEnable(id,enable)>0;
    }
}
