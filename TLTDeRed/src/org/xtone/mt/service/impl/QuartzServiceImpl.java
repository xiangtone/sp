package org.xtone.mt.service.impl;

import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.xtone.mt.service.inface.IQuartz;

public class QuartzServiceImpl extends BaseServiceImpl implements IQuartz  {
	/**
	 * 修改开关状态
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto updateDeptItem(Dto pDto) {
			g4Dao.update("updateQuartzs", pDto);
		return null;
	}
}
