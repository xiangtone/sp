package org.xtone.mt.DBHandle;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisServiceProviderForQuartz
{

	private static SqlMapClient sqlMapper;
	private static Logger logger;
	public IbatisServiceProviderForQuartz()
	{
	}
	public static SqlMapClient getSqlMapper()
	{
		return sqlMapper;
	}
	static 
	{
		logger = Logger.getLogger(IbatisServiceProviderForQuartz.class);
		try
		{
			Reader reader = Resources.getResourceAsReader("config/Quartz.xml");
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		}
		catch (IOException e)
		{
			logger.error((new StringBuilder("config/Quartz.xml")).append(e).toString());
			throw new RuntimeException((new StringBuilder("Something bad happened while building the SqlMapClient instance.")).append(e).toString(), e);
		}
	}
	

}
