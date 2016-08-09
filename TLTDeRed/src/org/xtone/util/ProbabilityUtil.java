package org.xtone.util;


public class ProbabilityUtil {
	
	private static final double pSngBegin = 1;
	private static final double pSngEnd = 100;
	private static final double pSngPB = 2;
	private static final double pSngPE =10;

	/**  
	    * ����һ�����ʽ������<br>  
	    * <br>  
	    * @param pSngBegin �����Χ�Ŀ�ʼ����  
	    * @param pSngEnd �����Χ��������  
	    * @param pSngPB Ҫ�������ֵĿ�ʼ����  
	    * @param pSngPE Ҫ�������ֵĽ�������  
	    * @param pBytP Ҫ��������������  
	    * @return ����һ��������������  
	    */  
	public static double GetRndNumP(double pSngBegin,double pSngEnd,    
	                         double pSngPB,double pSngPE,double pBytP) {   
	    double sngPLen;   
	    double sngTLen; //total length   
	    double sngIncreased; //��Ҫ���ŵĳ���   
	    double sngResult;   
	    sngPLen = pSngPE - pSngPB;   
	    sngTLen = pSngEnd - pSngBegin;   
	    if ((sngPLen / sngTLen) * 100 == pBytP ) {   
	        return GetRandomNum(pSngBegin, pSngEnd);   
	    } else {   
	        // ((sngPLen + sngIncreased) / (sngTLen + sngIncreased)) * 100 = bytP   
	        sngIncreased = ((pBytP / 100) * sngTLen - sngPLen) / (1 - (pBytP / 100));   
	        // ���Ż�ԭ�����   
	        sngResult = GetRandomNum(pSngBegin, pSngEnd + sngIncreased);   
	        if (pSngBegin <= sngResult && sngResult <= pSngPB) {   
	            return sngResult;   
	        } else if (pSngPB <= sngResult && sngResult <= (pSngPE + sngIncreased)) {   
	            return pSngPB + (sngResult - pSngPB) * sngPLen / (sngPLen + sngIncreased);   
	        } else if ((pSngPE + sngIncreased) <= sngResult && sngResult <= (pSngEnd + sngIncreased )) {   
	            return sngResult - sngIncreased;
	        }
	    }
	    return 0f;   
	}   
	  
	public static double GetRandomNum(double pSngBegin, double pSngEnd) {   
	    return (pSngEnd - pSngBegin) * Math.random() + pSngBegin;   
	} 

	public static boolean detain(double pBytP){
		boolean flag = false;
		double re = ProbabilityUtil.GetRndNumP(pSngBegin, pSngEnd, pSngPB, pSngPE, pBytP);
		if(re>pSngPB && re<pSngPE){
			flag = true;
		}
		return flag;
	}
}

