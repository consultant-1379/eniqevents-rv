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
package com.ericsson.eni.analysis.views;

import java.util.HashMap;

import com.ericsson.cifwk.taf.ui.core.*;
import com.ericsson.cifwk.taf.ui.sdk.*;
import com.ericsson.eni.analysis.objects.NavigationPageController;

public class LoggedInModelView extends GenericViewModel {
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//span[@title='0 of 0 rows']")
	UiComponent noRowsSpan;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//span[@title='Ready ']")
	UiComponent readySpan;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector="//div[@title='Menu']")
	Link logoutMenu;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector="//div[@title='Log out']")
	Link logoutLink;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector="html/body/div[2]/div[3]/div/button[1]")
	Button logoutOKButton;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector="//input[@value='Start >>']")
	Button startButton;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//span[@title='0 marked']")
	UiComponent zeroMarkedSpan;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//input[contains(@value, '>>')]")
	UiComponent forwardNavigationButton;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//input[contains(@value, '<<')]")
	UiComponent backwardNavigationButton;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//div[@class='activeTabName']")
	UiComponent activePageName;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector="//div[@title='Reset']")
	Button resetFilterButton;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector="//div[@title='Filters']")
	Link filterEnableLink;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector=".//span[text()='Filters']")
	Label filerSpan;
	
	public void filterToggle(boolean toggle)
	{
		if(toggle) {
			if(!filerSpan.exists()) {			
				filterEnableLink.click();
			} 
		} else {
			if(filerSpan.exists()) {
				filterEnableLink.click();
			}
		}
	}
	
	public UiComponent getFilterEnableLink() {
		return this.filterEnableLink;
	}
	public void resetFilter() {
		resetFilterButton.click();
		System.out.println("reset filter");
	}
	
	public Button getResetFilterButton() {
		return resetFilterButton;
	}
	public UiComponent getNoRowsSpan() {
		return this.noRowsSpan;
	}
	
	public UiComponent getReadySpan() {
		return this.readySpan;
	}
	
	public UiComponent getZeroMarkedSpan() {
		return this.zeroMarkedSpan;
	}
	
	public boolean isZeroRowsLoaded() {
		if(noRowsSpan.exists()) {
			return true;
		}		
		return false;
	}
	
	public boolean isMarked() {
		if(zeroMarkedSpan.exists())
			return false;
		else 
			return true;
	}

	
	public UiComponent getLogoutMenu() {
		return this.logoutMenu;
	}
	
	public UiComponent getStartButton() {
		return this.startButton;
	}
	
	public UiComponent getLogoutOKButton() {
		return this.logoutOKButton;
	}
	
	
	
	public UiComponent getLogoutLink() {
		return this.logoutLink;
	}
	
	public void startGuidedAnalysis() {
		startButton.click();
	}
	
	public void logoutClick() {
		logoutMenu.click();
		waitUntilComponentIsDisplayed(logoutLink, 20000);
		logoutLink.click();
		waitUntilComponentIsDisplayed(logoutOKButton, 20000);
		logoutOKButton.click();
		
	}
	
	

	
	public UiComponent getForwardNavigationButton() {
		return forwardNavigationButton;
	}
	
	public UiComponent getActivePageName() {
		return activePageName;
	}

	public void setForwardNavigationButton(UiComponent forwardNavigationButton) {
		this.forwardNavigationButton = forwardNavigationButton;
	}


	public UiComponent getBackwardNavigationButton() {
		return backwardNavigationButton;
	}


	public void setBackwardNavigationButton(UiComponent backwardNavigationButton) {
		this.backwardNavigationButton = backwardNavigationButton;
	}
	
	public void moveForward() {
		waitUntilComponentIsDisplayed(forwardNavigationButton,20000);
		forwardNavigationButton.click();
	}
	
	public void moveBackWard() {
		waitUntilComponentIsDisplayed(backwardNavigationButton,20000);
		backwardNavigationButton.click();
	}
	
	public void navigateToPage(String navigationPageName) {
		NavigationPageController navigationPageController = new NavigationPageController();
		HashMap<String,Integer> navigationPages = navigationPageController.getNavigationPages();
		String currentPageName = activePageName.getProperty(UiProperties.TITLE);
		System.out.println(currentPageName);
		System.out.println(navigationPageName);
		Integer initialPageValue = navigationPages.get(currentPageName);
		Integer destinationPageValue = navigationPages.get(navigationPageName);
		Integer difference = destinationPageValue-initialPageValue;
		System.out.println(initialPageValue+" "+destinationPageValue+" "+difference);
		boolean check=!activePageName.getProperty(UiProperties.TITLE).equals(navigationPageName);
		if(difference>0) {
			while (check && difference!=0) {
				moveForward();
				waitUntilComponentIsDisplayed(activePageName,20000);
				System.out.println(activePageName.getProperty(UiProperties.TITLE));
				check=!activePageName.getProperty(UiProperties.TITLE).equals(navigationPageName);
				System.out.println(check);
			}
				
			
		} else if (difference<0) {
		while(check) {
				moveBackWard();
				waitUntilComponentIsDisplayed(activePageName,20000);
				System.out.println(activePageName.getProperty(UiProperties.TITLE));
				check=!activePageName.getProperty(UiProperties.TITLE).equals(navigationPageName);
				System.out.println(check);
			}
		}
		
		
		
	}
	

}
