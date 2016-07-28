package smsunitl.util;
/*
 * 该类用于产生概率
 */
public class Rand {
		 /**  
		    * 按照一定概率进行随机<br>  
		    * <br>  
		    * @param pSngBegin 随机数范围的开始数字  
		    * @param pSngEnd 随机数范围结束数字  
		    * @param pSngPB 要随机的数字的开始数字  
		    * @param pSngPE 要随机的数字的结束数字  
		    * @param pBytP 要随机的数字随机概率  
		    * @return 按照一定概率随机的数字  
		    */  
		public double GetRndNumP(double pSngBegin,    
		                         double pSngEnd,    
		                         double pSngPB,    
		                         double pSngPE,    
		                         double pBytP) {   
		       
		    double sngPLen;   
		       
		    double sngTLen; //total length   
		       
		    double sngIncreased; //需要缩放的长度   
		       
		    double sngResult;   
		       
		    sngPLen = pSngPE - pSngPB;   
		       
		    sngTLen = pSngEnd - pSngBegin;   
		       
		    if ((sngPLen / sngTLen) * 100 == pBytP ) {   
		           
		        return GetRandomNum(pSngBegin, pSngEnd);   
		           
		    } else {   
		           
		        // ((sngPLen + sngIncreased) / (sngTLen + sngIncreased)) * 100 = bytP   
		        sngIncreased = ((pBytP / 100) * sngTLen - sngPLen) / (1 - (pBytP / 100));   
		           
		        // 缩放回原来区间   
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
		  
		public double GetRandomNum(double pSngBegin, double pSngEnd) {   
		       
		    return (pSngEnd - pSngBegin) * Math.random() + pSngBegin;   
		} 
	//}

}
