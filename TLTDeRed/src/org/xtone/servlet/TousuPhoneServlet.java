package org.xtone.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.xtone.mt.service.impl.TousuPhoneServiceImpl;
import org.xtone.util.FileTool;
import org.xtone.util.URLConnectionUtil;

public class TousuPhoneServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5838265332619542883L;
	static Logger log = Logger.getLogger(TousuPhoneServlet.class); 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("GBK");
		PrintWriter pw = resp.getWriter();
		String fileUrl = req.getParameter("file");// 文件地址
		String black = req.getParameter("black");// 是否存入黑名单 1为是 0为否
		String id = req.getParameter("id"); // 唯一批次号
		String pid = req.getParameter("pid");// 业务线ID号
		String datestart = req.getParameter("datestart");// 开始时间，包含该天
		if(null==datestart||"".equals(datestart)){
			datestart="2010-1-1";
		}
		String dateend = req.getParameter("dateend");// 结束时间，包含该天
		if(null==dateend||"".equals(dateend)){
			dateend="2080-1-1";
		}
		datestart=datestart+" 00:00:00";
		dateend=dateend+" 23:59:59";
		
		String type = req.getParameter("type");//类型，1为全部 2为计次 3为包月
		if(null==type||"".equals(type)){
			type="1";
		}
		
		try {
			if (null == fileUrl || "".equals(fileUrl)||null == black || "".equals(black)
					||null == id || "".equals(id)||null == pid || "".equals(pid) ) {
				pw.write("-1");
				return;
			}else if(200!=URLConnectionUtil.getPostCode(fileUrl)){
				pw.write("-1");
				return;
			}else{
				pw.write("1");
			}
			pw.flush();
			pw.close();
			
			Dto dto = new BaseDto();
			dto.put("idoa", id);
			dto.put("black", black);
			dto.put("pid", pid);
			dto.put("datestart", datestart);
			dto.put("dateend", dateend);
			dto.put("type", type);
			
			String realpath = getServletContext().getRealPath("/")+"tousu/"+id+".txt";
			realpath = realpath.replace("\\", "/");
			dto.put("path", realpath);
			String contextpath ="http://"+req.getLocalAddr()+":"+req.getServerPort()+ getServletContext().getContextPath()+"/tousu/"+id+".txt";
			dto.put("contextpath", contextpath);

			List<String> list = FileTool.readTxtByUrl(fileUrl);
			TousuPhoneServiceImpl tousuPhoneServiceImpl=new TousuPhoneServiceImpl();
			tousuPhoneServiceImpl.insertPhonesBatch(dto, list);
		} catch (Exception e) {
			log.error("",e);
			e.printStackTrace();
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}
