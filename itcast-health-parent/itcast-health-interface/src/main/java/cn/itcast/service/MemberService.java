package cn.itcast.service;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.Member;

public interface MemberService {


     /**
      * 分页查询会员
      * @param queryPageBean
      * @return
      */
     PageResult findMemberByPage(QueryPageBean queryPageBean);

    /**
     * 增加会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据id查询会员
     * @param id
     * @return
     */
    Member findById(Integer id);

    /**
     * 编辑 member
     * @param member
     * @return
     */
    void updateMember(Member member);

    /**
     * 修改会员状态
     * @param id
     * @param status
     */
    void updateStatus(Integer id, Integer status);

    Member findByPhoneNumber(String phone);
}
