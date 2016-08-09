package org.xtone.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.xtone.mt.quartz.QuartzManager;
import org.xtone.mt.quartz.limitDays.AlterTableQuartzJob;
import org.xtone.mt.quartz.limitDays.DayReportToSubQuartzJob;
import org.xtone.mt.quartz.limitDays.LimitDaysQuartzJob1;
import org.xtone.mt.quartz.limitDays.LimitDaysQuartzJob2;
import org.xtone.mt.quartz.limitDays.LimitDaysQuartzJob3;
import org.xtone.mt.quartz.limitDays.MonthMtQuartzJob;
import org.xtone.mt.quartz.limitDays.MonthSuccCpnNumQuartzJob;
import org.xtone.mt.quartz.limitDays.ReportTableQuartzJob;
import org.xtone.mt.quartz.limitDays.ReportTableQuartzJob2;
import org.xtone.mt.quartz.limitDays.UpdateReprotByMtQuartzJob;
import org.xtone.mt.quartz.limitDays.UserNumQuartzJob;

public class JobInitServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(JobInitServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.error(">>>>>>>>>> begin initial JOB");
		String jobDescription = "";
		try {// night job
			jobDescription = "night job";
			LimitDaysQuartzJob1 job = new LimitDaysQuartzJob1();
			String job_name1 = "11";
			QuartzManager.addJob(job_name1, job, "0 01 00 * * ? *");

			LimitDaysQuartzJob2 job2 = new LimitDaysQuartzJob2();
			String job_name2 = "12";
			QuartzManager.addJob(job_name2, job2, "0 01 08 * * ? *");

			LimitDaysQuartzJob3 job3 = new LimitDaysQuartzJob3();
			String job_name3 = "73";
			QuartzManager.addJob(job_name3, job3, "0 00 07 * * ? *");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		try {// stat daily job
			jobDescription = " stat daily job ";
			UserNumQuartzJob job = new UserNumQuartzJob();
			String job_name1 = "13";
			QuartzManager.addJob(job_name1, job, "0 41 16 * * ? *");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		try {// 定时移动日表到月表中
			jobDescription = " move daily to month ";
			MonthMtQuartzJob job = new MonthMtQuartzJob();
			String job_name1 = "monthMT";
			QuartzManager.addJob(job_name1, job, "00 30 01 * * ?");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		try {// 统计月限手机号码人数
			jobDescription = " MonthSuccCpnNumQuartzJob ";
			MonthSuccCpnNumQuartzJob job = new MonthSuccCpnNumQuartzJob();
			String job_name1 = "MonthSuccCpnNum";
			QuartzManager.addJob(job_name1, job, "0 0/2 * * * ?");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		try {// 移动日报到 OA和统计包月
			jobDescription = " DayReportToSubQuartzJob ";
			DayReportToSubQuartzJob job = new DayReportToSubQuartzJob();
			String job_name1 = "DayReportToSub";
			QuartzManager.addJob(job_name1, job, "00 30 03 * * ?");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		try {// 统计日报
			jobDescription = " ReportTableQuartzJob ";
			ReportTableQuartzJob job = new ReportTableQuartzJob();
			String job_name1 = "reportTable";
			QuartzManager.addJob(job_name1, job, "0 06 02 * * ? *");

			ReportTableQuartzJob2 job2 = new ReportTableQuartzJob2();
			String job_name2 = "reportTable2";
			QuartzManager.addJob(job_name2, job2, "0 0/10 * * * ?");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		try {// 更新状态报告，扣量模式
			jobDescription = " UpdateReprotByMtQuartzJob ";
			UpdateReprotByMtQuartzJob job = new UpdateReprotByMtQuartzJob();
			String job_name1 = "UpdateReportByMt";
			QuartzManager.addJob(job_name1, job, "0/10 * * * * ?");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		try {// mt move
			jobDescription = " AlterTableQuartzJob ";
			AlterTableQuartzJob job = new AlterTableQuartzJob();
			String job_name1 = "alterTable";
			QuartzManager.addJob(job_name1, job, "00 01 00 * * ?");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("start " + jobDescription + " error", e);
		}

		// try {//凌晨2点30分移动日报扣量数据到OA服务器
		// DayReportklToOAQuartzJob job = new DayReportklToOAQuartzJob();
		// String job_name1 ="DayReportklToOA";
		// QuartzManager.addJob(job_name1,job,"00 30 02 * * ?");
		// } catch (Exception e) {
		// e.printStackTrace();
		// logger.error("启动JOB【凌晨2点30分移动日报扣量数据到OA服务器】异常", e);
		// }
		logger.error(">>>>>>>>>> job set end ");
	}

}
