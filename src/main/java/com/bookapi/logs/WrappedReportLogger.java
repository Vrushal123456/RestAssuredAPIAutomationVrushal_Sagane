package com.bookapi.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class WrappedReportLogger {

    private static final Logger logger = LogManager.getLogger(WrappedReportLogger.class);

    public static void trace(String message) {
        logger.trace(message);
        Allure.step(message, Status.SKIPPED); // trace = low-level info (mapped to skipped/info)
    }

    public static void debug(String message) {
        logger.debug(message);
        Allure.step(message, Status.SKIPPED); // debug mapped to skipped/info
    }

    public static void info(String message) {
        logger.info(ConsoleColors.GREEN + message + ConsoleColors.RESET);
        Allure.step(message, Status.PASSED);
    }

    public static void warn(String message) {
        logger.warn(ConsoleColors.YELLOW + message + ConsoleColors.RESET);
        Allure.step(message, Status.BROKEN); // warn mapped to broken
    }

    public static void error(String message) {
        logger.error(ConsoleColors.RED_BOLD + message + ConsoleColors.RESET);
        Allure.step(message, Status.FAILED);
    }

    public static void fatal(String message) {
        logger.fatal(ConsoleColors.RED_BOLD + message + ConsoleColors.RESET);
        Allure.step(message, Status.FAILED);
    }
}
