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
package com.ericsson.eni.testflows;

import com.ericsson.cifwk.taf.scenario.TestStepFlow;
import com.ericsson.eni.teststeps.DataLoadTimeTestSteps;

import javax.inject.Inject;

import static com.ericsson.cifwk.taf.scenario.TestScenarios.*;


public class DataLoadTimeFlow {

    @Inject
    private DataLoadTimeTestSteps teststeps;

    public TestStepFlow getLoginLogoutFlow() {
        return flow("LoginFlow")
                .addTestStep(annotatedMethod(teststeps, "InitBrowser"))
                .addTestStep(annotatedMethod(teststeps, "LoginFlowStart"))
                .addTestStep(annotatedMethod(teststeps, "Login"))
                .addTestStep(annotatedMethod(teststeps, "CleanUp"))
                .addTestStep(annotatedMethod(teststeps, "LoginFlowEnd"))
                .withDataSources(   
                        dataSource("users"))
                .build();
    }
    
    public TestStepFlow getStartReportFlow() {
		return flow("LoginFlow")
				.addTestStep(annotatedMethod(teststeps, "InitBrowser"))
				.addTestStep(annotatedMethod(teststeps, "LoginFlowStart"))
				.addTestStep(annotatedMethod(teststeps, "Login"))
				.addTestStep(annotatedMethod(teststeps, "StartGuidedAnalysis"))
				.addTestStep(annotatedMethod(teststeps, "MoveSlider"))
				.addTestStep(annotatedMethod(teststeps, "SelectCallDrop"))
				.addTestStep(annotatedMethod(teststeps, "SelectCallSetupFailure"))
				.addTestStep(annotatedMethod(teststeps, "SelectEnodeB"))
				.addTestStep(annotatedMethod(teststeps, "MarkingNetworkFailureLast24Hours"))
				.addTestStep(annotatedMethod(teststeps, "CleanUp"))
                .addTestStep(annotatedMethod(teststeps, "LoginFlowEnd"))
				.withDataSources(dataSource("users"))
				.build();
    }
    public TestStepFlow getMoveSliderFlow() {
		return flow("LoginFlow")
				.addTestStep(annotatedMethod(teststeps, "InitBrowser"))
				.addTestStep(annotatedMethod(teststeps, "LoginFlowStart"))
				.addTestStep(annotatedMethod(teststeps, "Login"))
				.addTestStep(annotatedMethod(teststeps, "StartGuidedAnalysis"))
				.addTestStep(annotatedMethod(teststeps, "MoveSlider"))
				.addTestStep(annotatedMethod(teststeps, "CleanUp"))
                .addTestStep(annotatedMethod(teststeps, "LoginFlowEnd"))
				.withDataSources(dataSource("users"))
				.build();
    	
    }
}
