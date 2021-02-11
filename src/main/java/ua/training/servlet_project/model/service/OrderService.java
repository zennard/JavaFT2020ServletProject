package ua.training.servlet_project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.*;
import ua.training.servlet_project.model.dao.*;
import ua.training.servlet_project.model.entity.*;
import ua.training.servlet_project.model.exceptions.EmptyOrderException;
import ua.training.servlet_project.model.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);
    public static final String NOT_FOUND_ORDER = "Cannot delete non-existing order with id ";
    private static final String NOT_FOUND_RECORD = "Illegal record, record with this date doesn't exist!";
    private static final String RECORD_ALREADY_EXISTS = "Illegal record, record with this date already exists!";
    private final DaoFactory daoFactory;

    public OrderService() {
        daoFactory = DaoFactory.getInstance();
    }

    public Long createNewOrder(OrderCreationDTO orderDTO) {
        orderDTO.getOrderItems().stream()
                .filter(item -> recordExists(orderDTO.getStartsAt(), orderDTO.getEndsAt(), item.getApartmentId()))
                .findFirst()
                .ifPresent(item -> {
                    throw new IllegalArgumentException(RECORD_ALREADY_EXISTS);
                });

        User user;
        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.findByEmail(orderDTO.getUserEmail())
                    .orElseThrow(() -> new UserNotFoundException("User with this credentials wasn't found!"));
        }

        Order order = getOrder(orderDTO, user);

        List<OrderItem> orderItems = new ArrayList<>();
        List<ApartmentTimetable> schedule = new ArrayList<>();

        List<OrderItemDTO> orderItemsDTO = orderDTO.getOrderItems();
        for (OrderItemDTO o : orderItemsDTO) {
            Apartment apartment = new Apartment();
            apartment.setId(o.getApartmentId());

            ApartmentTimetable scheduleItem = getScheduleItem(orderDTO, apartment);
            schedule.add(scheduleItem);

            orderItems.add(
                    OrderItem.builder()
                            .order(order)
                            .startsAt(orderDTO.getStartsAt())
                            .endsAt(orderDTO.getEndsAt())
                            .apartment(apartment)
                            .schedule(scheduleItem)
                            .price(o.getPrice())
                            .amount(o.getAmount())
                            .build()
            );
        }

        return saveOrder(order, schedule, orderItems);
    }

    private Order getOrder(OrderCreationDTO orderDTO, User user) {
        return Order
                .builder()
                .orderDate(orderDTO.getOrderDate())
                .orderStatus(OrderStatus.NEW)
                .user(user)
                .build();
    }

    private ApartmentTimetable getScheduleItem(OrderCreationDTO orderDTO, Apartment apartment) {
        return ApartmentTimetable.builder()
                .apartment(apartment)
                .startsAt(orderDTO.getStartsAt())
                .endsAt(orderDTO.getEndsAt())
                .status(RoomStatus.BOOKED)
                .build();
    }

    public Long saveOrder(Order order, List<ApartmentTimetable> schedule, List<OrderItem> items) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.saveNewOrder(order, schedule, items);
        }
    }

    public boolean recordExists(LocalDateTime startsAt, LocalDateTime endsAt, Long apartmentId) {
        List<ApartmentTimetable> schedule;
        try (ApartmentTimetableDao apartmentTimetableDao = daoFactory.createApartmentTimetableDao()) {
            schedule = apartmentTimetableDao.findAllScheduleByApartmentIdAndDate(
                    startsAt, endsAt, apartmentId);
        }
        return !schedule.isEmpty();
    }

    public void updateOrderStatus(UpdateOrderDTO newOrderDTO) {
        //@TODO pay for order with money
        Order order;
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            order = orderDao.findById(newOrderDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ORDER + newOrderDTO.getId()));
        }
        order.setOrderStatus(newOrderDTO.getStatus());

        List<ApartmentTimetable> schedule = new ArrayList<>();
        if (newOrderDTO.getStatus().equals(OrderStatus.PAID)) {
            List<OrderItem> items;
            try (OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
                items = orderItemDao.findAllByOrderId(newOrderDTO.getId());
            }
            items.forEach(item -> {
                ApartmentTimetable timeslot;
                try (ApartmentTimetableDao apartmentTimetableDao = daoFactory.createApartmentTimetableDao()) {
                    timeslot = apartmentTimetableDao
                            .findById(item.getSchedule().getId())
                            .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_RECORD));
                }

                timeslot.setStatus(RoomStatus.PAID);
                schedule.add(timeslot);
            });
        }

        updateOrder(order, schedule);
    }

    private void updateOrder(Order order, List<ApartmentTimetable> schedule) {
        LOGGER.info(order);
        LOGGER.info(schedule);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.updateOrderAndTimeslotStatus(order, schedule);
        }
    }

    public Page<OrderDTO> getAllNewOrders(Pageable pageable) {
        Page<Order> ordersPage;
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            ordersPage = orderDao.findAllByOrderStatus(OrderStatus.NEW, pageable);
        }

        List<OrderDTO> ordersDTO = ordersPage.getContent().stream()
                .map(this::getOrderDTO)
                .collect(Collectors.toList());

        return new Page<>(ordersDTO, ordersPage.getPageable(),
                ordersPage.getTotalPages());
    }

    private OrderDTO getOrderDTO(Order o) {
        List<OrderItem> items;
        try (OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
            items = orderItemDao.findAllByOrderId(o.getId());
        }

        Optional<User> user;
        try (UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.findById(o.getUser().getId());
        }

        if (items.isEmpty()) {
            throw new EmptyOrderException("Cannot create order without order items!");
        }

        String userEmail = user.orElseThrow(() -> new UserNotFoundException("User not found with id: " + o.getId()))
                .getEmail();
        List<OrderItemDTO> itemsDTO = getOrderItemDTOS(items);

        return OrderDTO.builder()
                .id(o.getId())
                .userEmail(userEmail)
                .orderDate(o.getOrderDate())
                .startsAt(items.get(0).getStartsAt())
                .endsAt(items.get(0).getEndsAt())
                .orderItems(itemsDTO)
                .build();
    }

    private List<OrderItemDTO> getOrderItemDTOS(List<OrderItem> items) {
        return items
                .stream()
                .map(item -> OrderItemDTO.builder()
                        .amount(item.getAmount())
                        .price(item.getPrice())
                        .apartmentId(item.getApartment().getId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public Page<UserOrderDTO> getAllUserOrders(Pageable pageable, Long userId) {
        Page<Order> ordersPage;
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            ordersPage = orderDao.findAllByUserId(userId, pageable);
        }

        List<UserOrderDTO> ordersDTO = ordersPage.getContent().stream()
                .map(this::getUserOrderDTO)
                .collect(Collectors.toList());

        return new Page<>(ordersDTO, ordersPage.getPageable(),
                ordersPage.getTotalPages());
    }

    private UserOrderDTO getUserOrderDTO(Order o) {
        List<OrderItem> items;
        try (OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
            items = orderItemDao.findAllByOrderId(o.getId());
        }

        if (items.isEmpty()) {
            throw new EmptyOrderException("Cannot create order without order items!");
        }

        List<OrderItemDTO> itemsDTO = getOrderItemDTOS(items);

        return UserOrderDTO.builder()
                .id(o.getId())
                .status(o.getOrderStatus())
                .orderDate(o.getOrderDate())
                .startsAt(items.get(0).getStartsAt())
                .endsAt(items.get(0).getEndsAt())
                .orderItems(itemsDTO)
                .build();
    }
}
