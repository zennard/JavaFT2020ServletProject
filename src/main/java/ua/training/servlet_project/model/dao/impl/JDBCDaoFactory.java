package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private static final Logger LOGGER = LogManager.getLogger(JDBCDaoFactory.class);
    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public ApartmentDao createApartmentDao() {
        return new JDBCApartmentDao(getConnection());
    }

    @Override
    public ApartmentTimetableDao createApartmentTimetableDao() {
        return new JDBCApartmentTimetableDao(getConnection());
    }

    @Override
    public ApartmentDescriptionDao createApartmentDescriptionDao() {
        return new JDBCApartmentDescriptionDao(getConnection());
    }

    @Override
    public BookingRequestDao createBookingRequestDao() {
        return new JDBCBookingRequestDao(getConnection());
    }

    @Override
    public BookingRequestItemDao createBookingRequestItemDao() {
        return new JDBCBookingRequestItemDao(getConnection());
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao(getConnection());
    }

    @Override
    public OrderItemDao createOrderItemDao() {
        return new JDBCOrderItemDao(getConnection());
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public RuleDao createRuleDao() {
        return new JDBCRuleDao(getConnection());
    }

    private Connection getConnection() {
        try {
            LOGGER.info("connecting");
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
