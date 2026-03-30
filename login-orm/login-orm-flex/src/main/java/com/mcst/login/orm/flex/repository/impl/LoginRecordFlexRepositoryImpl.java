package com.mcst.login.orm.flex.repository.impl;

import com.mcst.easyfk.service.flex.impl.BaseFlexRepositoryImpl;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.orm.flex.persistence.mapper.LoginRecordMapper;
import com.mcst.login.orm.flex.persistence.model.LoginRecord;
import com.mcst.login.orm.repository.ILoginRecordRepository;

/**
 * <p>
 * 登录记录 Repository实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public class LoginRecordFlexRepositoryImpl extends BaseFlexRepositoryImpl<LoginRecordMapper, LoginRecordDto, LoginRecord, String> implements ILoginRecordRepository {


}