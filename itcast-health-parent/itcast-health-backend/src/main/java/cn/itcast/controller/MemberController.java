package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/member")
@Slf4j
public class MemberController {

    @Reference(version = "1.0.0")
    private MemberService memberService;

    /**
     * TODO
     * 分页查询会员
     *
     * @param queryPageBean
     * @return
     */
    @GetMapping(path = "/findPage")
    public Result findMemberByPage(QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = memberService.findMemberByPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_MEMBER_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MEMBER_FAIL);
        }

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findMemberById(@PathVariable Integer id) {
        try {
            Member member = memberService.findById(id);
            return new Result(true,MessageConstant.QUERY_MEMBER_SUCCESS,member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_MEMBER_FAIL);
        }
    }


    /**
     * 增加会员
     * @param member
     * @return
     */
    @PostMapping(path = "/add")
    public Result AddMember(@RequestBody Member member) {
        try {
            memberService.add(member);
            return new Result(true,MessageConstant.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_MEMBER_FAIL);
        }
    }

    /**
     * 编辑 member
     * @param member
     * @return
     */
    @PutMapping(path = "/update")
    public Result updateMember(@RequestBody Member member){
        try {
            memberService.updateMember(member);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MEMBER_FAIL);
        }
    }

    /**
     * 修改会员状态
     * @param id
     * @param status
     * @return
     */
    @PutMapping(path = "/{id}/{status}")
    public Result updateStatus(@PathVariable Integer id,@PathVariable Integer status){
        try {
            memberService.updateStatus(id,status);
            return new Result(true,MessageConstant.UPDATE_MEMBER_STATUS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPDATE_MEMBER_STATUS_FAIL);
        }
    }


}
