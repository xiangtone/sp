package org.eredlab.g4.ccl.datastructure;

/**
 * 非空数据传输对象接口<br>
 * @see com.eredlab.eredbos.eredccp.datastructure.Dto
 */
public interface PKey extends Dto{
	/**
	 * 对Key数据传输对象进行键值非空性校验
	 * @throws Exception 
	 */
	public void validateNullAble();
}
