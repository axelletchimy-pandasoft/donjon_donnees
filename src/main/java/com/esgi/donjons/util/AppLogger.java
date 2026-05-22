package com.esgi.donjons.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class AppLogger {

    private static final String LOG_LEVEL_ENV = System.getenv("LOG_LEVEL");

    private final Logger logger;

    private AppLogger(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz.getName());
        Level level = parseLevel(LOG_LEVEL_ENV);
        if (level != null) {
            this.logger.setLevel(level);
        }
    }

    public static AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String message, Object... args) {
        logger.info(String.format(message, args));
    }

    public void warn(String message) {
        logger.warning(message);
    }

    public void warn(String message, Object... args) {
        logger.warning(String.format(message, args));
    }

    public void error(String message) {
        logger.severe(message);
    }

    public void error(String message, Object... args) {
        logger.severe(String.format(message, args));
    }

    public void error(String message, Throwable thrown) {
        logger.log(Level.SEVERE, message, thrown);
    }

    public void debug(String message) {
        logger.fine(message);
    }

    public void debug(String message, Object... args) {
        logger.fine(String.format(message, args));
    }

    private static Level parseLevel(String level) {
        if (level == null) return null;
        return switch (level.toUpperCase()) {
            case "DEBUG" -> Level.FINE;
            case "INFO" -> Level.INFO;
            case "WARN" -> Level.WARNING;
            case "ERROR" -> Level.SEVERE;
            default -> Level.INFO;
        };
    }
}
