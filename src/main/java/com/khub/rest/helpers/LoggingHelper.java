package com.khub.rest.helpers;

import org.slf4j.Logger;

public class LoggingHelper {

    public static void entering (Logger logger, String className, String methodName) {
        logger.debug("Entering {} {}", new Object[] {className, methodName});
    }

    public static void exiting (Logger logger, String className, String methodName) {
        logger.debug("Exiting {} {}", new Object[] {className, methodName});
    }

    public static void logSentStatistic (Logger logger, String service, String query, String maxResults) {
        logger.info("Sent request to {}; query: {}, max results: {}", service, query, maxResults);
    }

    public static void logTotalStatistic (Logger logger, String service, Long timeDiff, Integer responseCode) {
        logger.info("Finished Processing {}; total elapsed: {}, response code: {}", service, timeDiff + " ms", responseCode);
    }
}
