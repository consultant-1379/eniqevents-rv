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


import com.ericsson.cifwk.taf.ui.sdk.*;
import com.ericsson.cifwk.taf.ui.core.*;


public class TimeBasedCFAModel extends GenericViewModel {
	
	
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector=".//*[@id='af2f2f31-afe7-4d17-910d-cc3d17909b91']/div[1]/div")
	CheckBox callDropCheckBox;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector=".//*[@id='af2f2f31-afe7-4d17-910d-cc3d17909b91']/div[2]/div")
	CheckBox callSetupFailureCheckBx;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector=".//*[@id='42f39b91-1b9f-46af-aa70-f065b1bd3bfd']/div/div[2]/div[3]")
	Link startOfSlider;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector=".//input[@value='Network CFA >>']")
	Button networkCFAButton;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="(//img[@src='Images/EmptyImage.gif'])[2]")
	Link last24Hours;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="(//img[@src='Images/EmptyImage.gif'])[1]")
	Link previous24Hours;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH,selector=".//div[@title='ONRM_ROOT_MO_R:CTL01002_1']")
	Link eNodeBListItem;
	
	
	public Link geteNodeBListItem() {
		return eNodeBListItem;
	}

	public Link getEutranCellListItem() {
		return eutranCellListItem;
	}

	@UiComponentMapping(selectorType=SelectorType.XPATH,selector=".//div[@title='CTL01003_1_7C_1']")
	Link eutranCellListItem;
	
	
	public void selectEnodeB() {
		waitUntilComponentIsDisplayed(eNodeBListItem, 20000);
		eNodeBListItem.click();
	}
	
	public void selectEutranCell() {
		waitUntilComponentIsDisplayed(eutranCellListItem, 20000);
		eutranCellListItem.click();
	}
	
	//time in hours
	public void moveSlider(int Time) {
		startOfSlider.mouseDown(0,0);
		//length of slider/ number of hours
		int movingPosition=(151/48)*Time;
		startOfSlider.mouseUp(movingPosition,0);
	}
	
	public void selectCheckBox(UiComponent checkbox) {
		System.out.println(checkbox);
		checkbox.click();
		//System.out.println(checkbox.isSelected());	
	}
	
	public void navigateToNetworkCFA() {
		networkCFAButton.click();
	}

	
	public Link getStartOfSlider() {
		return startOfSlider;
	}


	public CheckBox getCallDropCheckBox() {
		return callDropCheckBox;
	}


	public CheckBox getCallSetupFailureCheckBox() {
		return callSetupFailureCheckBx;
	}
	
	
	public Button getNetwokCFAButton() {
		return networkCFAButton;
	}
	
	public Link getLast24Hours() {
		return last24Hours;
	}
	
	public Link getPrevious24Hours() {
		return previous24Hours;
	}

}
