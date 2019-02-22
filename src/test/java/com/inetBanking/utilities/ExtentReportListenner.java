package com.inetBanking.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListenner extends TestListenerAdapter {

	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extentRep;
	public ExtentTest logger;
	
	public void onStart(ITestContext testContext) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String reportName = "TestReport" + timeStamp + ".html";
		
		// Specify location for Extent Reports using Relative path:
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/" + reportName);
		
		// Specify location for Extent Reports config xml using Relative path:
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
		
		extentRep = new ExtentReports();
		
		extentRep.attachReporter(htmlReporter);
		
		extentRep.setSystemInfo("Host name", "localhost");
		extentRep.setSystemInfo("Environment", "QA");
		extentRep.setSystemInfo("user", "PV");
		
		htmlReporter.config().setDocumentTitle("InetBanking Test Project");
		htmlReporter.config().setReportName("Functional Test Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);	
	}
	
	public void onTestSuccess(ITestResult tr) {
		
		logger=extentRep.createTest(tr.getName()); // creates new entry in the report
		
		// Sends information of passed methods in green
		logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
	}
	
	public void onTestFailure(ITestResult tr) {
		
		logger=extentRep.createTest(tr.getName()); // creates new entry in the report
		
		// Sends information of passed methods in green
		logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
		
		// When TC fails, get the log to the report
		logger.log(Status.FAIL, tr.getThrowable());
		
		String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + tr.getName() + ".png";
		System.out.println(screenshotPath);
		
		File file = new File(screenshotPath);
		
		if (file.exists()) {
			try {
				logger.fail("Screenshot is below: " + logger.addScreenCaptureFromPath(screenshotPath));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void onTestSkipped(ITestResult tr) {
		
		logger=extentRep.createTest(tr.getName()); // creates new entry in the report
		
		// Sends information of passed methods in green
		logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
	}
	
	public void onFinish(ITestContext testContext) {
		
		extentRep.flush();
	}
}
