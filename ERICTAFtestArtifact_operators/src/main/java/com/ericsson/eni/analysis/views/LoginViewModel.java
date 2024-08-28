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

import com.ericsson.cifwk.taf.ui.core.SelectorType;
import com.ericsson.cifwk.taf.ui.core.UiComponentMapping;
import com.ericsson.cifwk.taf.ui.sdk.*;

public class LoginViewModel extends GenericViewModel {

	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//input[@title='Username']")
	TextBox usernameTextBox;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//input[@title='Password']")
	TextBox passwordTextBox;
	
	@UiComponentMapping(selectorType=SelectorType.XPATH, selector="//input[@class='LoginButton']")
	Button loginButton;
	
	
	public void login(String username, String password) {
		usernameTextBox.setText(username);
		passwordTextBox.setText(password);
		loginButton.click();
	}
}
