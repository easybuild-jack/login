package com.mcst.login.orm.plus.repository.impl;

import com.mcst.easyfk.service.mybatisplus.impl.BaseMyBatisRepositoryImpl;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.orm.plus.persistence.mapper.LoginRecordMapper;
import com.mcst.login.orm.plus.persistence.model.LoginRecord;
import com.mcst.login.orm.repository.ILoginRecordRepository;

/**
 * <p>
 * 登录记录 Repository实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public class LoginRecordPlusRepositoryImpl extends BaseMyBatisRepositoryImpl<LoginRecordMapper, LoginRecordDto, LoginRecord, String> implements ILoginRecordRepository {


}