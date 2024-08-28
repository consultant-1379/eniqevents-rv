/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.eni.analysis.operators;

import java.awt.MouseInfo;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.Operator;
import com.ericsson.cifwk.taf.annotations.VUserScoped;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.Ports;
import com.ericsson.cifwk.taf.ui.Browser;
import com.ericsson.cifwk.taf.ui.BrowserOS;
import com.ericsson.cifwk.taf.ui.BrowserTab;
import com.ericsson.cifwk.taf.ui.BrowserType;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.cifwk.taf.ui.core.UiComponent;
import com.ericsson.eni.analysis.views.*;
import com.ericsson.enm.Tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@VUserScoped
@Operator(context = Context.UI)
public class AnalysisUIOperator implements AnalysisOperator {
    private Browser browser;
    private String url;
    private static final Logger LOG = LoggerFactory.getLogger(AnalysisUIOperator.class);

    public AnalysisUIOperator() {
    }

    @Override
    public void initBrowser() {
    	//System.setProperty("phantomjs.binary.path", "C:\\phantomjs-2.0.0-windows\\phantomjs-2.0.0-windows\\bin\\phantomjs.exe");
    	//System.setProperty("phantomjs.cli.args","--ignore-ssl-errors=yes");
        this.browser = UI.newBrowser(BrowserType.PHANTOMJS, BrowserOS.ANY);
        LOG.info("Browser is provided - {}", browser);
        Tool.set(browser);
    }

    @Override
    public void login(String username, String password) {
        this.url = getUrl();
        BrowserTab browserTab = browser.open(url);
        browserTab.maximize();
        LoginViewModel loginView = browserTab.getView(LoginViewModel.class);

        loginView.login(username, password);
    }

    /**
     * @return returns true if the analysis displays the span element with content
     * '0 of 0 rows' displayed. Returns false otherwise
     */
    @Override
    public boolean isZeroRowsDisplayed() {
        BrowserTab browserTab = browser.getCurrentWindow();
        LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
        UiComponent noRowsLoadedSpan = loggedInModelView.getNoRowsSpan();
        //not guarding against spurious wake ups
        browserTab.waitUntilComponentIsDisplayed(noRowsLoadedSpan, 30000);
        return loggedInModelView.isZeroRowsLoaded();
    }

    @Override
    public boolean isDataLoaded() {
        BrowserTab browserTab = browser.getCurrentWindow();
        LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
        UiComponent readySpan = loggedInModelView.getReadySpan();
        browserTab.waitUntilComponentIsDisplayed(readySpan, -1); //waits forever!!
        UiComponent noRowsLoadedSpan = loggedInModelView.getNoRowsSpan();
        System.out.println("data loaded");
        return !noRowsLoadedSpan.exists();
    }
    
    @Override
    public boolean isDataMarked() {
        BrowserTab browserTab = browser.getCurrentWindow();
        LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
        UiComponent readySpan = loggedInModelView.getReadySpan();
        browserTab.waitUntilComponentIsDisplayed(readySpan, -1); //waits forever!!
        return loggedInModelView.isMarked();
        
    }


    /**
     * @return the String http:// URL to the analysis that is to be opened
     * in the format http://ipaddress:portnumber/uri/to/analysis
     */
    private String getUrl() {
        Host analysisHost = DataHandler.getHostByName("host");
        String ipAddress = analysisHost.getIp();
        Integer httpPort = analysisHost.getPort(Ports.HTTPS);
        String uri = (String) DataHandler.getAttribute("analysis.web.uri");
        return String.format("https://%s:%s%s", ipAddress, httpPort, uri);
    }

    @Override
    public void closeBrowser() {
    	BrowserTab browserTab = browser.getCurrentWindow();
        LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
        UiComponent logoutMenu = loggedInModelView.getLogoutMenu();
        browserTab.waitUntilComponentIsDisplayed(logoutMenu,30000);
        loggedInModelView.logoutClick();
    	
        if (!browser.isClosed()) {
            browser.close();
        }
    }

    @Override
    public Tool getTool() {
        return Tool.set(browser);
    }


	public void startGuidedAnalysis() {
		// TODO Auto-generated method stub
		BrowserTab browserTab = browser.getCurrentWindow();
        LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
        UiComponent startButton = loggedInModelView.getStartButton();
        browserTab.waitUntilComponentIsDisplayed(startButton,30000);
        loggedInModelView.startGuidedAnalysis();
        isDataLoaded();
	}

	/* (non-Javadoc)
	 * @see com.ericsson.eni.analysis.operators.AnalysisOperator#toggleFilter(boolean)
	 */
	@Override
	public void toggleFilter(boolean toggle) {
		// TODO Auto-generated method stub
		BrowserTab browserTab = browser.getCurrentWindow();
		LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
		UiComponent filterEnableLink = loggedInModelView.getFilterEnableLink();
		browserTab.waitUntilComponentIsDisplayed(filterEnableLink, 30000);
		loggedInModelView.filterToggle(toggle);
	}

	public void resetFilter() {
		BrowserTab browserTab = browser.getCurrentWindow();
		LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
		UiComponent resetFilterButton = loggedInModelView.getResetFilterButton();
		browserTab.waitUntilComponentIsDisplayed(resetFilterButton, 30000);
		loggedInModelView.resetFilter();
		isDataLoaded();
		
	}
	
	@Override
	public void navigateToPage(String navigationPageName){
		BrowserTab browserTab = browser.getCurrentWindow();
		LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
		loggedInModelView.navigateToPage(navigationPageName);	
	}
	
	@Override
	public void forwardNavigation(){
		BrowserTab browserTab = browser.getCurrentWindow();
		LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
		loggedInModelView.moveForward();			
	}
	
	@Override
	public void backwardNavigation(){
		BrowserTab browserTab = browser.getCurrentWindow();
		LoggedInModelView loggedInModelView = browserTab.getView(LoggedInModelView.class);
		loggedInModelView.moveBackWard();	
	}
	
	public void checkCallDropCallSetupFailure(String checkBoxName) {
		BrowserTab browserTab = browser.getCurrentWindow();
		TimeBasedCFAModel timeBasedCFAModel = browserTab.getView(TimeBasedCFAModel.class);
		UiComponent checkBox= null;
		if(checkBoxName.equals("Call Drops"))
			checkBox = timeBasedCFAModel.getCallSetupFailureCheckBox();
		else
			checkBox = timeBasedCFAModel.getCallDropCheckBox();
		browserTab.waitUntilComponentIsDisplayed(checkBox, 30000);
		timeBasedCFAModel.selectCheckBox(checkBox);
		
	}
	
	@Override
	public void moveSlider(int hours) {
		BrowserTab browserTab = browser.getCurrentWindow();
		TimeBasedCFAModel timeBasedCFAModel = browserTab.getView(TimeBasedCFAModel.class);
		UiComponent startOfSlider = timeBasedCFAModel.getStartOfSlider();
		browserTab.waitUntilComponentIsDisplayed(startOfSlider, 30000);
		timeBasedCFAModel.moveSlider(24);
	}
	
	@Override
	public void selectENodeB_EutranCell() {
		BrowserTab browserTab = browser.getCurrentWindow();
		TimeBasedCFAModel timeBasedCFAModel = browserTab.getView(TimeBasedCFAModel.class);
		timeBasedCFAModel.selectEnodeB();
		timeBasedCFAModel.selectEutranCell();
		
	}
	
	@Override
	public void markingNetworkFailureLast24Hours() {
		// TODO Auto-generated method stub
		BrowserTab browserTab = browser.getCurrentWindow();
		TimeBasedCFAModel timeBasedCFAModel = browserTab.getView(TimeBasedCFAModel.class);
		UiComponent linkLast24Hours = timeBasedCFAModel.getLast24Hours();
		browserTab.waitUntilComponentIsDisplayed(linkLast24Hours, 30000);
	
		int height= linkLast24Hours.getSize().getHeight();
		int width= linkLast24Hours.getSize().getWidth();
		System.out.println(height+" "+width);
		
		System.out.println(MouseInfo.getPointerInfo().getLocation());
		//linkLast24Hours.mouseDown(0,0);
		linkLast24Hours.mouseDown(310,170);
		System.out.println(MouseInfo.getPointerInfo().getLocation());
		try {
        	System.out.println("Hello4");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            
        }
		System.out.println(MouseInfo.getPointerInfo().getLocation());
		//linkLast24Hours.mouseMove(50,-50);
		linkLast24Hours.mouseUp(719,310);
		System.out.println(MouseInfo.getPointerInfo().getLocation());
		
			try {
			        	System.out.println("Hello4");
			            Thread.sleep(10000);
			        } catch (InterruptedException e) {
			            
			        }
		
			
		//linkLast24Hours.mouseUp(900,150);
		isDataLoaded();
		
		
	}
	
	
}
