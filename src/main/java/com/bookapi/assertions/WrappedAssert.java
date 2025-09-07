package com.bookapi.assertions;

import org.testng.Assert;

import com.bookapi.logs.WrappedReportLogger;
import com.bookapi.reportBuilder.ApiClient;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class WrappedAssert {

    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            WrappedReportLogger.info("[VERIFICATION - PASS] " + message);
            Allure.step("[VERIFICATION - PASS] " + message, Status.PASSED);
        } catch (AssertionError e) {
            WrappedReportLogger.error("[VERIFICATION - FAIL] " + message);
            Allure.step("[VERIFICATION - FAIL] " + message, Status.FAILED);
            Assert.fail("[VERIFICATION - FAIL] " + message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        try {
            Assert.assertFalse(condition, message);
            WrappedReportLogger.info("[VERIFICATION - PASS] " + message);
            Allure.step("[VERIFICATION - PASS] " + message, Status.PASSED);
        } catch (AssertionError e) {
            WrappedReportLogger.error("[VERIFICATION - FAIL] " + message);
            Allure.step("[VERIFICATION - FAIL] " + message, Status.FAILED);
            Assert.fail("[VERIFICATION - FAIL] " + message);
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            if (expected == null || expected.equals("")) {
                expected = "BLANK";
            }

            if (actual == null || actual.equals("")) {
                actual = "BLANK";
            }

            Assert.assertEquals(actual, expected, message);
            WrappedReportLogger.info("[VERIFICATION - PASS] " + message + ". EXPECTED [" + expected + "] " + " ACTUAL [" + actual + "]");
            Allure.step("[VERIFICATION - PASS] " + message + ". EXPECTED [" + expected + "] " + " ACTUAL [" + actual + "]", Status.PASSED);
        } catch (AssertionError e) {
            WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + ". EXPECTED [" + expected + "] " + " ACTUAL [" + actual + "]");
            Allure.step("[VERIFICATION - FAIL] " + message + ". EXPECTED [" + expected + "] " + " ACTUAL [" + actual + "]", Status.FAILED);
            Assert.fail("[VERIFICATION - FAIL] " + message + ". EXPECTED [" + expected + "] " + " ACTUAL [" + actual + "]");
        }
    }

    public static void assertNotNull(Object value, String message) {
        try {
            Assert.assertNotNull(value, message);
            WrappedReportLogger.info("[VERIFICATION - PASS] " + message + " | Value: " + value);
            Allure.step("[VERIFICATION - PASS] " + message + " | Value: " + value, Status.PASSED);
        } catch (AssertionError e) {
            WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + " | Value is NULL");
            Allure.step("[VERIFICATION - FAIL] " + message + " | Value is NULL", Status.FAILED);
            Assert.fail("[VERIFICATION - FAIL] " + message + " | Value is NULL");
        }
    }

    public static void assertJsonSchema(Response response, String schemaPath, String message) {
        try {
            if (schemaPath == null || schemaPath.trim().isEmpty()) {
                schemaPath = "BLANK";
            }

            boolean matchesSchema;

            // Check if schema file exists in classpath
            if (ApiClient.class.getClassLoader().getResource(schemaPath) == null) {
                WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + ". SCHEMA FILE NOT FOUND [" + schemaPath + "]");
                Allure.step("[VERIFICATION - FAIL] " + message + ". SCHEMA FILE NOT FOUND [" + schemaPath + "]", Status.FAILED);
                Assert.fail("[VERIFICATION - FAIL] " + message + ". SCHEMA FILE NOT FOUND [" + schemaPath + "]");
            }

            try {
                response.then().assertThat()
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
                matchesSchema = true;
            } catch (AssertionError e) {
                matchesSchema = false;
            }

            Assert.assertTrue(matchesSchema, message);
            WrappedReportLogger.info("[VERIFICATION - PASS] " + message + ". SCHEMA [" + schemaPath + "]");
            Allure.step("[VERIFICATION - PASS] " + message + ". SCHEMA [" + schemaPath + "]", Status.PASSED);

        } catch (AssertionError e) {
            WrappedReportLogger.error("[VERIFICATION - FAIL] " + message + ". SCHEMA [" + schemaPath + "]");
            Allure.step("[VERIFICATION - FAIL] " + message + ". SCHEMA [" + schemaPath + "]", Status.FAILED);
            Assert.fail("[VERIFICATION - FAIL] " + message + ". SCHEMA [" + schemaPath + "]");
        }
    }
}
