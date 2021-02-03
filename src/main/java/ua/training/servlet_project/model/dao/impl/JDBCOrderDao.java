package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.OrderDao;
import ua.training.servlet_project.model.dao.mapper.OrderMapper;
import ua.training.servlet_project.model.entity.*;
import ua.training.servlet_project.model.exceptions.EntityCreationException;
import ua.training.servlet_project.model.exceptions.OrderNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCOrderDao implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCOrderDao.class);
    private static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "Cannot find order with id: ";
    private Connection connection;

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Order entity) {

    }

    @Override
    public void updateOrderAndTimeslotStatus(Order order, List<ApartmentTimetable> schedule) {
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareCall("UPDATE `order` " +
                    " SET order_status = ? " +
                    "WHERE id = ? ")) {
                ps.setString(1, order.getOrderStatus().toString());
                ps.setLong(2, order.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new EntityCreationException(ex);
            }

            if (!schedule.isEmpty()) {
                try (PreparedStatement ps = connection.prepareCall("UPDATE apartment_timetable " +
                        "SET status = ? " +
                        "WHERE id = ? ")) {

                    for (ApartmentTimetable item : schedule) {
                        ps.setString(1, item.getStatus().toString());
                        ps.setLong(2, item.getId());
                        ps.addBatch();
                    }

                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new EntityCreationException(ex);
                }
            }

            LOGGER.info("committing...");

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception ex) {
            LOGGER.error(ex);
            try {
                connection.rollback();
            } catch (SQLException throwable) {
                LOGGER.error(throwable);
            }
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<Order> order = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM `order` WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            OrderMapper mapper = new OrderMapper();
            if (rs.next()) {
                order = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE + id);
        }

        LOGGER.info(order);
        return order;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Page<Order> findAllByOrderStatus(OrderStatus status, Pageable pageable) {
        Map<Long, Order> orders = new LinkedHashMap<>();
        int totalPages = 0;

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT * FROM `order` o " +
                        "WHERE o.order_status = ? " +
                        "ORDER BY o.id " +
                        "LIMIT ? " +
                        "OFFSET ? ");
             PreparedStatement countQuery = connection.prepareCall(
                     "SELECT COUNT(*) FROM `order` o " +
                             "WHERE o.order_status = ? "
             )
        ) {
            ps.setString(1, status.toString());
            ps.setInt(2, pageable.getPageSize());
            ps.setInt(3, pageable.getPageNumber() * pageable.getPageSize());

            countQuery.setString(1, status.toString());

            ResultSet rs = ps.executeQuery();
            ResultSet totalElementsResultSet = countQuery.executeQuery();

            OrderMapper orderMapper = new OrderMapper();
            while (rs.next()) {
                Order order = orderMapper
                        .extractFromResultSet(rs);
                orderMapper
                        .makeUnique(orders, order);
            }

            if (totalElementsResultSet.next()) {
                totalPages = totalElementsResultSet.getInt("COUNT(*)") / pageable.getPageSize();
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
        return new Page<>(new ArrayList<>(orders.values()), pageable, totalPages);
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwable) {
            LOGGER.error(throwable);
        }
    }
}
