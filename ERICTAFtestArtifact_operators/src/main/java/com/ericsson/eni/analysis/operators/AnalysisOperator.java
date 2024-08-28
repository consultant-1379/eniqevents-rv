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

import com.ericsson.enm.Tool;

public interface AnalysisOperator {

    void initBrowser();

    void login(String username, String password);

    void closeBrowser();

    boolean isZeroRowsDisplayed();

    Tool getTool();

    boolean isDataLoaded();
    
    void startGuidedAnalysis();
    
    void toggleFilter(boolean toggle);
    
    void resetFilter();
    
	boolean isDataMarked();

	void navigateToPage(String navigationPageName);
	
	void checkCallDropCallSetupFailure(String checkBoxName);

	void moveSlider(int hours);

	/**
	 * 
	 */
	void forwardNavigation();

	/**
	 * 
	 */
	void backwardNavigation();

	/**
	 * 
	 */
	void selectENodeB_EutranCell();

	/**
	 * 
	 */
	void markingNetworkFailureLast24Hours();
	
	
}
