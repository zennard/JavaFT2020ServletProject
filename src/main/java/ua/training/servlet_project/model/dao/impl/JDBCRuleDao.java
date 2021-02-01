package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.RuleDao;
import ua.training.servlet_project.model.dao.mapper.RuleMapper;
import ua.training.servlet_project.model.entity.Rule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JDBCRuleDao implements RuleDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCRuleDao.class);
    private Connection connection;

    public JDBCRuleDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Rule entity) {

    }

    @Override
    public Optional<Rule> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Rule> findAll() {
        Map<Long, Rule> rules = new HashMap<>();

        String query = "SELECT * FROM rule";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            RuleMapper ruleMapper = new RuleMapper();

            while (rs.next()) {
                Rule rule = ruleMapper
                        .extractFromResultSet(rs);
                ruleMapper
                        .makeUnique(rules, rule);
            }

            return new ArrayList<>(rules.values());
        } catch (SQLException e) {
            LOGGER.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public void update(Rule entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
