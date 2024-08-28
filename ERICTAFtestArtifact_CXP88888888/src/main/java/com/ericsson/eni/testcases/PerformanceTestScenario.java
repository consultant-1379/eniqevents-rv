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


package com.ericsson.eni.testcases;

import com.ericsson.cifwk.taf.TestCase;
import com.ericsson.cifwk.taf.TestContext;
import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.TestId;
import com.ericsson.cifwk.taf.datasource.TafDataSources;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.cifwk.taf.execution.TestExecutionEvent;
import com.ericsson.cifwk.taf.scenario.api.ExceptionHandler;
import com.ericsson.cifwk.taf.scenario.api.TestScenarioBuilder;
import com.ericsson.cifwk.taf.scenario.api.TestScenarioRunnerBuilder;
import com.ericsson.cifwk.taf.scenario.impl.LoggingScenarioListener;
import com.ericsson.cifwk.taf.ui.UI;
import com.ericsson.eni.common.ContextKey;
import com.ericsson.eni.predicates.User;
import com.ericsson.eni.predicates.UsersPredicate;
import com.ericsson.eni.scheduler.ScheduleParamResolver;
import com.ericsson.eni.scheduler.ScheduleParams;
import com.ericsson.eni.testflows.DataLoadTimeFlow;
import com.ericsson.enm.metrics.MetricsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;

import static com.ericsson.cifwk.taf.datasource.TafDataSources.fromCsv;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.runner;
import static com.ericsson.cifwk.taf.scenario.TestScenarios.scenario;
import static com.ericsson.eni.scheduler.ScenarioRunScheduler.schedule;

public class PerformanceTestScenario extends TorTestCaseHelper implements TestCase {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceTestScenario.class);

    @Inject
    private DataLoadTimeFlow testflow;

    @Inject
    TestContext context;

    @BeforeSuite
    public void setup() {
        UI.closeWindow(TestExecutionEvent.ON_SUITE_FINISH);
  
    }

    @Test
    @Context(context={Context.UI})
    @TestId(id = "Start-Report")
    
    public void startReport() {
    	//set Execution Id for reporting
        context.setAttribute(ContextKey.EXECUTION_ID, UUID.randomUUID().toString());

        //Build datasource with users and make it shared
        TestDataSource<User> users = TafDataSources.filter(fromCsv("data/users.csv", User.class), new UsersPredicate(1));
        TestDataSource<User> sharedUsersDataSource = TafDataSources.shared(users);
        context.addDataSource("users", sharedUsersDataSource);

        //Create scenario with test flow
        TestScenarioBuilder scenarioBuilder = scenario("Start-Report")
                .addFlow(testflow.getStartReportFlow())
                .withTestStepExceptionHandler(ExceptionHandler.PROPAGATE)
                .withVusers(1);
        
        TestScenarioRunnerBuilder runnerBuilder = runner()
        		.withListener(new LoggingScenarioListener())
                .withExceptionHandler(ExceptionHandler.PROPAGATE);

        //Get scenario schedule params
        ScheduleParams flowScheduleParams = ScheduleParamResolver.get("flow.simple-login");

        //schedule scenario to run
        schedule().scenario(scenarioBuilder)
                .withRunner(runnerBuilder)
                .withTimeSlot(60) //pause is taken if execution was faster than 60seconds
                .toRun(flowScheduleParams.getUnitCount(), flowScheduleParams.getTimeUnit());
        	
    }
   /* @Test
    @Context(context = {Context.UI})
    @TestId(id = "Simple-Login-Logout-Test")
    public void loginLogoutScenario() {
        //set Execution Id for reporting
        context.setAttribute(ContextKey.EXECUTION_ID, UUID.randomUUID().toString());

        //Build datasource with users and make it shared
        TestDataSource<User> users = TafDataSources.filter(fromCsv("data/users.csv", User.class), new UsersPredicate(1));
        TestDataSource<User> sharedUsersDataSource = TafDataSources.shared(users);
        context.addDataSource("users", sharedUsersDataSource);

        //Create scenario with test flow
        TestScenarioBuilder scenarioBuilder = scenario("Simple-Login-Logout-Test")
                .addFlow(testflow.getLoginLogoutFlow())
                .withTestStepExceptionHandler(ExceptionHandler.PROPAGATE)
                .withVusers(1);

        TestScenarioRunnerBuilder runnerBuilder = runner()
                .withListener(new LoggingScenarioListener())
                .withExceptionHandler(ExceptionHandler.PROPAGATE);

        //Get scenario schedule params
        ScheduleParams flowScheduleParams = ScheduleParamResolver.get("flow.simple-login");

        //schedule scenario to run
        schedule().scenario(scenarioBuilder)
                .withRunner(runnerBuilder)
                .withTimeSlot(60) //pause is taken if execution was faster than 60seconds
                .toRun(flowScheduleParams.getUnitCount(), flowScheduleParams.getTimeUnit());
    }
    
    
    @Test
    @Context(context={Context.UI})
    @TestId(id = "Start-Report")
    public void startReport() {
    	//set Execution Id for reporting
        context.setAttribute(ContextKey.EXECUTION_ID, UUID.randomUUID().toString());

        //Build datasource with users and make it shared
        TestDataSource<User> users = TafDataSources.filter(fromCsv("data/users.csv", User.class), new UsersPredicate(1));
        TestDataSource<User> sharedUsersDataSource = TafDataSources.shared(users);
        context.addDataSource("users", sharedUsersDataSource);

        //Create scenario with test flow
        TestScenarioBuilder scenarioBuilder = scenario("Start-Report")
                .addFlow(testflow.getStartReportFlow())
                .withTestStepExceptionHandler(ExceptionHandler.PROPAGATE)
                .withVusers(1);
        
        TestScenarioRunnerBuilder runnerBuilder = runner()
                .withListener(new LoggingScenarioListener())
                .withExceptionHandler(ExceptionHandler.PROPAGATE);

        //Get scenario schedule params
        ScheduleParams flowScheduleParams = ScheduleParamResolver.get("flow.simple-login");

        //schedule scenario to run
        schedule().scenario(scenarioBuilder)
                .withRunner(runnerBuilder)
                .withTimeSlot(60) //pause is taken if execution was faster than 60seconds
                .toRun(flowScheduleParams.getUnitCount(), flowScheduleParams.getTimeUnit());
        	
    }
    @Test
    @Context(context={Context.UI})
    @TestId(id = "Move-Slider")
    public void moveSlider() {
    	//set Execution Id for reporting
        context.setAttribute(ContextKey.EXECUTION_ID, UUID.randomUUID().toString());

        //Build datasource with users and make it shared
        TestDataSource<User> users = TafDataSources.filter(fromCsv("data/users.csv", User.class), new UsersPredicate(1));
        TestDataSource<User> sharedUsersDataSource = TafDataSources.shared(users);
        context.addDataSource("users", sharedUsersDataSource);

        //Create scenario with test flow
        TestScenarioBuilder scenarioBuilder = scenario("Move-Slider")
                .addFlow(testflow.getMoveSliderFlow())
                .withTestStepExceptionHandler(ExceptionHandler.PROPAGATE)
                .withVusers(1);
        
        TestScenarioRunnerBuilder runnerBuilder = runner()
                .withListener(new LoggingScenarioListener())
                .withExceptionHandler(ExceptionHandler.PROPAGATE);

        //Get scenario schedule params
        ScheduleParams flowScheduleParams = ScheduleParamResolver.get("flow.simple-login");

        //schedule scenario to run
        schedule().scenario(scenarioBuilder)
                .withRunner(runnerBuilder)
                .withTimeSlot(60) //pause is taken if execution was faster than 60seconds
                .toRun(flowScheduleParams.getUnitCount(), flowScheduleParams.getTimeUnit());
        	
    }*/

    @AfterMethod
    public void tearDown() throws IOException {
        MetricsBuilder.closeConnection();
    }
}
