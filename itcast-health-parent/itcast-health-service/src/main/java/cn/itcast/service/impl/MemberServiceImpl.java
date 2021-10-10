package cn.itcast.service.impl;

import cn.itcast.dao.MemberDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Service(version = "1.0.0")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    /**
     * 分页查询会员
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findMemberByPage(QueryPageBean queryPageBean) {
        //开启分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Member> page = memberDao.SelectAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 增加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        member.setRegTime(new Date());
        member.setPassword(DigestUtils.md5Hex("123"));
        member.setStatus(1);
        memberDao.add(member);
    }

    /**
     * 根据id查询会员
     * @param id
     * @return
     */
    @Override
    public Member findById(Integer id) {
      return memberDao.findById(id);
    }

    /**
     * 编辑 member
     * @param member
     * @return
     */
    @Override
    public void updateMember(Member member) {
        Integer result = memberDao.updateMember(member);
        System.out.println(result);

    }

    /**
     * 修改会员状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Integer id, Integer status) {
        memberDao.updateStatus(id,status);
    }

    @Override
    public Member findByPhoneNumber(String phone) {
       return memberDao.findByPhoneNumber(phone);
    }
}
