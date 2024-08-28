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
package com.ericsson.eni.analysis.objects;

import java.util.HashMap;

public class NavigationPageController {
	HashMap<String,Integer> navigationPages = new HashMap<String,Integer>();
	
	/**
	 * 
	 */
	public NavigationPageController() {
		navigationPages.put("Start",1);
		navigationPages.put("Time Based CFA",2);
		navigationPages.put("Network CFA",3);
		navigationPages.put("Controller CFA",4);
		navigationPages.put("Access Area CFA",5);
		navigationPages.put("QCI CFA",6);
		navigationPages.put("Event Type CFA",7);
		navigationPages.put("Cause Code CFA",8);
		navigationPages.put("Filtered Data",9);
		
	}

	/**
	 * @return the navigationPages
	 */
	public HashMap<String, Integer> getNavigationPages() {
		return this.navigationPages;
	}

	
	
	

}
