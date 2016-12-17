package com.lm.bos.dao.user.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.lm.bos.dao.base.impl.BaseDaoImpl;
import com.lm.bos.dao.user.IUserDao;
import com.lm.bos.domain.User;

@Repository
//@Scope("prototype")		//¶àÀý
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

}
