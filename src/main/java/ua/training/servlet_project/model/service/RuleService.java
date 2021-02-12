package ua.training.servlet_project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.DaoFactory;
import ua.training.servlet_project.model.dao.RuleDao;
import ua.training.servlet_project.model.entity.Rule;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RuleService {
    private static final Logger LOGGER = LogManager.getLogger(RuleService.class);
    private final DaoFactory daoFactory;

    public RuleService() {
        daoFactory = DaoFactory.getInstance();
    }

    public Map<String, List<Rule>> getAllRules() {
        Map<String, List<Rule>> rules;

        try (RuleDao ruleDao = daoFactory.createRuleDao()) {
            rules = ruleDao.findAll().stream()
                    .collect(Collectors.groupingBy(Rule::getPattern));
        }

        LOGGER.info(rules);

        return rules;
    }
}
