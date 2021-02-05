package ua.training.servlet_project.model.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private static final Logger LOGGER = LogManager.getLogger(ValidationResult.class);
    List<String> errors;

    public ValidationResult() {
        errors = new ArrayList<>();
    }

    public void addErrorValue(String key) {
        LOGGER.info(key);
        errors.add(key);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
