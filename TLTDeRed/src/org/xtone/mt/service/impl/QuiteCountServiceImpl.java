package org.xtone.mt.service.impl;

import java.util.List;
import java.util.Map;

import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.xtone.mt.service.inface.IQuiteCountService;

/**
 *  @Company:Xtone集团
 *  @Description:统计合作方每个月收入情况 并比较 	
 *	@author: Dove
 *  @version 
 *  @Date: 2012-3-2下午02:45:02
 */
public class QuiteCountServiceImpl extends BaseServiceImpl implements IQuiteCountService   {
	/**
	 * 统计合作方每个月收入情况 并比较 	
	 */
	public void quiteCount(Dto pDto) {
		List provCount=g4Dao.queryForList("queryProvCount", pDto);
		List mtCount=g4Dao.queryForList("queryMtCount",pDto);
		for(Object object1:provCount){
			for(Object object2:mtCount){
				Map map1=(Map)object1;
				Map map2=(Map)object2;
				if(map1.get("company").equals(map2.get("company"))){
					if(!map1.get("countNum").equals(map2.get("countNum"))){
						System.out.println("存在不相等的合作方"+map1.get("company"));
					}
				}
			}
		}
	}
}
