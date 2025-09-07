package com.bookapi.report;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.bookapi.logs.ConsoleColors;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class AllureFactory implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println(ConsoleColors.BLUE_UNDERLINED + "===== TEST EXECUTION STARTED =====" + ConsoleColors.RESET);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testDesc = result.getMethod().getDescription();

        System.out.println(ConsoleColors.BLUE_UNDERLINED + testName + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + testDesc + ConsoleColors.RESET);

        // Attach test start info to Allure
        Allure.step("Starting test: " + testName);
        if (testDesc != null) {
            Allure.description(testDesc);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("=============== TEST CASE PASSED ================");
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(Status.PASSED));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("=============== TEST CASE FAILED ================");
        String errorMessage = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error";
        
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(Status.FAILED));
        Allure.addAttachment("Failure Reason", 
                new ByteArrayInputStream(errorMessage.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("=============== TEST CASE SKIPPED ================");
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(Status.SKIPPED));
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println(ConsoleColors.BLUE_UNDERLINED + "===== TEST EXECUTION FINISHED =====" + ConsoleColors.RESET);
    }
}
