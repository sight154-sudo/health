package cn.itcast.dao;

import cn.itcast.pojo.Member;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDao {

    /**
     * 查询所有会员
     * @return
     */
   Page<Member> SelectAll( String queryString);

    /**
     * 增加会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Member findById(Integer id);

    /**
     * 修改会员信息
     * @param member
     */
    Integer updateMember(Member member);


    /**
     * 修改会员状态
     * @param id
     * @param status
     */
    void updateStatus(@Param("id") Integer id,@Param("status") Integer status);

 /**
  * 根据手机号码查询会员是否存在
  * @param phone
  * @return
  */
 Member findByPhoneNumber(String phone);
}
