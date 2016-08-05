package org.xtone.mt.service.inface;

import java.sql.SQLException;

import org.eredlab.g4.ccl.datastructure.Dto;

public interface IUsersNumService {

	/**
	 * 保存用户
	 * 
	 * @param pDto
	 * @return
	 */
	public void saveUsersNumItem() throws SQLException;

}