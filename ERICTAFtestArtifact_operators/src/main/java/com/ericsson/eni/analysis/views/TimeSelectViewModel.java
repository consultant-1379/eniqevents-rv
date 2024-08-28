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

import com.ericsson.cifwk.taf.ui.core.*;
import com.ericsson.cifwk.taf.ui.sdk.GenericViewModel;
import com.ericsson.cifwk.taf.ui.sdk.Select;

public class TimeSelectViewModel extends GenericViewModel{
	
	@UiComponentMapping(selectorType=SelectorType.CSS, selector=".StyledDropDown")
	private Select timeSelectDropdown;
	
	public void selectTime(String time) {
		timeSelectDropdown.selectByTitle(time);
	}
	
	public Select getTimeSelectDropdown() {
		return this.timeSelectDropdown;
	}
}
