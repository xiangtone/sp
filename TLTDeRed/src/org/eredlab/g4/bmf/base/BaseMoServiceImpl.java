package org.eredlab.g4.bmf.base;

import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;

/**
 * 业务模型实现基类<br>
 * 
 */
public class BaseMoServiceImpl implements BaseService {

	/**
	 * Bpo基类中给子类暴露的一个DAO接口<br>
	 */
	protected IDao moDao;

	protected static PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);
	public void setMoDao(IDao moDao) {
		this.moDao = moDao;
	}
}
