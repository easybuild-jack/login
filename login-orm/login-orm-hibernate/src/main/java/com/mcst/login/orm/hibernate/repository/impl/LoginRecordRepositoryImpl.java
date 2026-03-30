package com.mcst.login.orm.hibernate.repository.impl;

import com.mcst.easyfk.service.hibernate.impl.BaseHibernateRepositoryImpl;
import com.mcst.login.api.dto.LoginRecordDto;
import com.mcst.login.orm.hibernate.persistence.mapper.LoginRecordMapper;
import com.mcst.login.orm.hibernate.persistence.model.LoginRecord;
import com.mcst.login.orm.repository.ILoginRecordRepository;

/**
 * <p>
 * 登录记录 Repository实现类
 * </p>
 *
 * @author eb-jack
 * @since 2026-03-30
 */
public class LoginRecordRepositoryImpl extends BaseHibernateRepositoryImpl<LoginRecordMapper, LoginRecordDto, LoginRecord, String> implements ILoginRecordRepository {


}