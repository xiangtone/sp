package org.xtone.mt.service.inface;

import org.eredlab.g4.ccl.datastructure.Dto;

public interface IMtIncomeService {

	/**
	 * 先查询,跟gameid去查询具体的业务多少钱，业务处理保存到表中
	 * @param pDto
	 * @return
	 */

	public abstract Dto saveIncomeDetail(Dto pDto);
	
	/**
	 * 统计包月收入情况
	 */
	public Dto saveIncomeDetailByMonth(Dto pDto);
	/**
	 * 更新gateType 字段
	 */
	public void updateGateType();

}