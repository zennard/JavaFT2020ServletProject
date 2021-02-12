package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.model.entity.*;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    Page<Order> findAllByOrderStatus(OrderStatus status, Pageable pageable);

    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    void updateOrderAndTimeslotStatus(Order order, List<ApartmentTimetable> schedule);

    Long saveNewOrder(Order order, List<ApartmentTimetable> schedule, List<OrderItem> items);
}
