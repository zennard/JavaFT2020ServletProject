package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.model.entity.OrderItem;

import java.util.List;

public interface OrderItemDao extends GenericDao<OrderItem> {
    List<OrderItem> findAllByOrderId(Long orderId);
}
