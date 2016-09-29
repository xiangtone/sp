package org.eredlab.g4.arm.service.impl;

import org.eredlab.g4.arm.service.ParamService;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

/**
 * 全局参数数据访问实现
 * 
 */
public class ParamServiceImpl extends BaseServiceImpl implements ParamService{

	/**
	 * 保存参数信息表
	 */
	public Dto saveParamItem(Dto pDto){
		pDto.put("paramid", IDHelper.getParamID());
		g4Dao.insert("saveParamItem", pDto);
		return null;
	}

	/**
	 * 删除参数信息
	 * 
	 * @param pDto
	 */
	public Dto deleteParamItem(Dto pDto){
		Dto dto = new BaseDto();
		String[] arrChecked = pDto.getAsString("strChecked").split(",");
		for(int i = 0; i < arrChecked.length; i++){
			dto.put("paramid", arrChecked[i]);
			g4Dao.delete("deletParamItem", dto);
		}
		return null;
	}

	/**
	 * 修改参数信息
	 * 
	 * @param pDto
	 */
	public Dto updateParamItem(Dto pDto){
		g4Dao.update("updateParamItem", pDto);
		return null;
	}

}
