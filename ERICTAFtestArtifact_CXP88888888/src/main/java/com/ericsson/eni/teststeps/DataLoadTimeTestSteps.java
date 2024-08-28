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
package com.ericsson.eni.teststeps;

import com.ericsson.cifwk.taf.TestContext;
import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.guice.OperatorRegistry;
import com.ericsson.eni.analysis.operators.AnalysisOperator;
import com.ericsson.eni.common.ContextKey;
import com.ericsson.eni.common.ReportHelper;
import com.google.common.base.Throwables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class DataLoadTimeTestSteps extends TorTestCaseHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DataLoadTimeTestSteps.class);

    @Inject
    OperatorRegistry<AnalysisOperator> registry;

    @Inject
    TestContext context;

    @TestStep(id = "InitBrowser")
    public void initBrowser() {
        AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        operator.initBrowser();
    }

    @TestStep(id = "Login")
    public void login(@Input("username") String username,
                      @Input("password") String password) {
        LOG.info("Executing login flow with username {} and password {}", username, password);
        AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        //operator.login(username, password);
        loginUser(username, password, operator);
    
    }
    
    @TestStep(id="StartGuidedAnalysis") 
    public void startGuidedAnalysis(){
    	
    	LOG.info("start guided analysis report");
    	AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        operator.startGuidedAnalysis();
        
    }
    
    @TestStep(id="MoveSlider") 
    public void moveSlider(){
    	
    	LOG.info("Move Time Slider");
    	AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        operator.toggleFilter(true);
        operator.resetFilter();
        operator.moveSlider(24);
        
    }
    
    @TestStep(id="SelectCallDrop") 
    public void selectCallDrop(){
    	
    	LOG.info("select CallDrop");
    	AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        operator.toggleFilter(true);
        operator.resetFilter();
        operator.checkCallDropCallSetupFailure("Call Drops");
        operator.isDataLoaded();
        
    }
    
    @TestStep(id="SelectCallSetupFailure") 
    public void selectCallSetupFailure(){
    	
    	LOG.info("select CallDrop or CallSetupFailure");
    	AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        operator.toggleFilter(true);
        operator.resetFilter();
        operator.checkCallDropCallSetupFailure("Call Setup Failures");
        operator.isDataLoaded();
        
    }
    
    @TestStep(id="SelectEnodeB")
    public void selectEnodeB() {
    	LOG.info("select EnodeB");
    	AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        operator.selectENodeB_EutranCell();
        operator.isDataLoaded();
    }
    
    @TestStep(id="MarkingNetworkFailureLast24Hours")
    public void markingNetworkFailureLast24Hours() {
    	LOG.info("marking NetworkFailure Last24Hours");
    	AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        operator.resetFilter();
        operator.markingNetworkFailureLast24Hours();
        System.out.println(operator.isDataMarked());
    }
        
    
    
    @TestStep(id = "CleanUp")
    public void cleanUp() {
        AnalysisOperator operator = registry.provide(AnalysisOperator.class);
        LOG.info("Operator provided is: {}", operator);
        long startOfExecution = System.currentTimeMillis();
        operator.closeBrowser();
        ReportHelper.reportSuccess("closeBrowser", startOfExecution);
    }

    private void loginUser(String username, String password, AnalysisOperator operator) {
        long startOfExecution = System.currentTimeMillis();
        try {
            operator.login(username, password);
            ReportHelper.reportSuccess("loginUser", startOfExecution);
        } catch (Exception e) {
            ReportHelper.reportFail("loginUser", startOfExecution);
            LOG.error(e.getMessage());
            Throwables.propagate(e);
        }
    }


    @TestStep(id = "LoginFlowStart")
    public void recordFlowStart() {
        context.setAttribute(ContextKey.FLOW_START, System.currentTimeMillis());
    }

    @TestStep(id = "LoginFlowEnd")
    public void recordFlowEnd() {
        long flowStartTime = context.getAttribute(ContextKey.FLOW_START);
        String timeRange=context.getAttribute(ContextKey.TIME_RANGE);
        if(timeRange!=null) {
            ReportHelper.reportSuccess("TotalTime - " + timeRange, flowStartTime);
        }
        else
        {
            ReportHelper.reportSuccess("TotalTime",flowStartTime);
        }
    }
    
    
}
