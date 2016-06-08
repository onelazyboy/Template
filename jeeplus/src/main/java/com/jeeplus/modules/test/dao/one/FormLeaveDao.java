/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.dao.one;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.test.entity.one.FormLeave;

/**
 * 员工请假DAO接口
 * @author liugf
 * @version 2016-05-06
 */
@MyBatisDao
public interface FormLeaveDao extends CrudDao<FormLeave> {

	
}