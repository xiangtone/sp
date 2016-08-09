package org.xtone.mt.service.inface;

import org.eredlab.g4.ccl.datastructure.Dto;

public interface IQuiteCountService {

	/**
	 * 统计合作方每个月收入情况 并比较 	
	 */
	void quiteCount(Dto pDto);

}