package com.system.vmodel;

import java.util.ArrayList;
import java.util.List;

public class FinancialSpCpDataShowModel
{
	public int spId;
	public String spShortName;
	public int spRowSpan = 1;
	
	public List<SpTroneModel> list = new ArrayList<FinancialSpCpDataShowModel.SpTroneModel>();

	public class SpTroneModel
	{
		public int spTroneId;
		public String spTroneName;
		public double spJieSuanLv;
		public int spTroneRowSpan = 1;
		
		public List<CpModelData> list = new ArrayList<FinancialSpCpDataShowModel.SpTroneModel.CpModelData>();
		
		public class CpModelData
		{
			public int dataRows;
			public double amount;
			public int showDataRows;
			public double showAmount;
			public int cpId;
			public String cpShortName;
			public double cpJieSuanLv;
		}
	}
}
