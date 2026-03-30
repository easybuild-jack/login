package com.mcst.login.controller;

import com.mcst.login.api.response.LoginRecordResp;
import com.mcst.login.api.request.LoginRecordReq;
import com.mcst.login.api.request.LoginRecordEditReq;
import com.mcst.login.api.ILoginRecordApi;
import com.mcst.easyfk.core.dto.request.ModifyRequest;
import com.mcst.easyfk.core.builders.MRPBuilder;
import com.mcst.easyfk.core.dto.request.SearchRequest;
import com.mcst.easyfk.core.builders.SRPBuilder;
import com.mcst.easyfk.core.dto.response.ResponseResult;
import com.mcst.easyfk.core.builders.RRBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.mcst.easyfk.core.dto.request.BatchBasicReq;
import com.mcst.easyfk.core.utils.common.SplitUtil;
import jakarta.annotation.Resource;
import java.util.List;

/**
 * 登录记录相关接口
 * @author eb-jack
 * @since 2026-03-30
 */
@Tag(name = "登录记录相关接口", description = "登录记录相关接口")
@RestController
@RequestMapping("/api/login/loginRecord")
public class LoginRecordController {
    @Resource
    private ILoginRecordApi loginRecordApi;


    @Operation(summary = "详情")
    @GetMapping("/detail")
    public ResponseResult<LoginRecordResp> detail(String id) {
        return RRBuilder.buildSuccessBody(this.loginRecordApi.queryById(id));
    }

    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/queryPage")
    public ResponseResult<List<LoginRecordResp>> queryPage(@Validated LoginRecordReq loginRecordReq) {
        SRPBuilder<LoginRecordReq> builder = SRPBuilder.<LoginRecordReq>builder().example(loginRecordReq);
        return RRBuilder.buildBodyByPageResult(this.loginRecordApi.queryByPage(builder.build()));
    }

    @Operation(summary = "新增/编辑", description = "新增/编辑")
    @PostMapping("/addOrEdit")
    public ResponseResult<?> addOrEdit(@RequestBody LoginRecordEditReq loginRecordEditReq) {
        ModifyRequest<LoginRecordReq> requestParam = MRPBuilder.buildRequest(loginRecordEditReq, LoginRecordReq.class);
        return RRBuilder.buildBodyByBaseResult(this.loginRecordApi.save(requestParam));
    }

    @Operation(summary = "批量删除", description = "批量删除")
    @PostMapping("/delete")
    public ResponseResult<?> delete(@Validated @RequestBody BatchBasicReq<String> batchBase) {
        List<String> idArray = SplitUtil.split(batchBase.getIds());
        ModifyRequest<LoginRecordReq> requestParam = MRPBuilder.<LoginRecordReq>builder().ids(idArray).build();
        return RRBuilder.buildBodyByBaseResult(this.loginRecordApi.delete(requestParam));
    }

}
