package ua.training.servlet_project.model.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.training.servlet_project.controller.dto.*;
import ua.training.servlet_project.model.dao.*;
import ua.training.servlet_project.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderItemDao orderItemDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ApartmentTimetableDao apartmentTimetableDao;

    @InjectMocks
    private OrderService testedInstance;

    @Test
    void shouldGetAllUserOrders() {
        //given
        when(orderDao.findAllByUserId(anyLong(), any()))
                .thenReturn(givenUserOrders());
        when(orderItemDao.findAllByOrderId(anyLong()))
                .thenReturn(Collections.singletonList(givenOrderItems().get(0)),
                        Collections.singletonList(givenOrderItems().get(1)));
        when(daoFactory.createOrderItemDao())
                .thenReturn(orderItemDao);
        when(daoFactory.createOrderDao())
                .thenReturn(orderDao);

        //when
        Page<UserOrderDTO> orders = testedInstance.getAllUserOrders(null, 1L);
        //then
        assertThat(orders.getContent().get(1))
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("startsAt", LocalDateTime.of(2020, 10, 3, 10, 20))
                .hasFieldOrPropertyWithValue("endsAt", LocalDateTime.of(2020, 10, 4, 10, 20))
                .hasFieldOrPropertyWithValue("status", OrderStatus.APPROVED)
                .hasFieldOrPropertyWithValue("orderDate", LocalDateTime.of(2001, 2, 1, 10, 10));

        assertThat(orders.getContent().get(1).getOrderItems().get(0))
                .hasFieldOrPropertyWithValue("apartmentId", 2L);
    }

    private List<OrderItem> givenOrderItems() {
        return Arrays.asList(
                OrderItem.builder()
                        .id(1L)
                        .startsAt(LocalDateTime.of(2020, 10, 1, 10, 20))
                        .endsAt(LocalDateTime.of(2020, 10, 2, 10, 20))
                        .price(BigDecimal.valueOf(1000))
                        .order(Order.builder().id(1L).build())
                        .schedule(ApartmentTimetable.builder().id(1L).build())
                        .apartment(Apartment.builder().id(1L).build())
                        .build(),
                OrderItem.builder()
                        .id(2L)
                        .startsAt(LocalDateTime.of(2020, 10, 3, 10, 20))
                        .endsAt(LocalDateTime.of(2020, 10, 4, 10, 20))
                        .price(BigDecimal.valueOf(2000))
                        .order(Order.builder().id(2L).build())
                        .schedule(ApartmentTimetable.builder().id(2L).build())
                        .apartment(Apartment.builder().id(2L).build())
                        .build());
    }

    private Page<Order> givenUserOrders() {
        return new Page<>(Arrays.asList(
                Order.builder()
                        .id(1L)
                        .user(User.builder().id(1L).build())
                        .orderStatus(OrderStatus.NEW)
                        .orderDate(LocalDateTime.of(2000, 2, 1, 10, 10))
                        .build(),
                Order.builder()
                        .id(2L)
                        .user(User.builder().id(1L).build())
                        .orderStatus(OrderStatus.APPROVED)
                        .orderDate(LocalDateTime.of(2001, 2, 1, 10, 10))
                        .build()
        ),
                null, 1);
    }

    @Test
    void shouldGetAllNewOrders() {
        //given
        when(orderDao.findAllByOrderStatus(any(), any()))
                .thenReturn(givenUserOrders());
        when(orderItemDao.findAllByOrderId(anyLong()))
                .thenReturn(Collections.singletonList(givenOrderItems().get(0)),
                        Collections.singletonList(givenOrderItems().get(1)));
        when(userDao.findById(any()))
                .thenReturn(Optional.of(User.builder().id(1L).email("a@a.com").build()),
                        Optional.of(User.builder().id(2L).email("b@b.com").build()));
        when(daoFactory.createOrderItemDao())
                .thenReturn(orderItemDao);
        when(daoFactory.createUserDao())
                .thenReturn(userDao);
        when(daoFactory.createOrderDao())
                .thenReturn(orderDao);

        //when
        Page<OrderDTO> orders = testedInstance.getAllNewOrders(null);
        //then
        assertThat(orders.getContent().get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("startsAt", LocalDateTime.of(2020, 10, 1, 10, 20))
                .hasFieldOrPropertyWithValue("endsAt", LocalDateTime.of(2020, 10, 2, 10, 20))
                .hasFieldOrPropertyWithValue("orderDate", LocalDateTime.of(2000, 2, 1, 10, 10));

        assertThat(orders.getContent().get(0).getOrderItems().get(0))
                .hasFieldOrPropertyWithValue("apartmentId", 1L);
    }

    @Test
    void shouldCreateNewOrder() {
        //given
        when(apartmentTimetableDao.findAllScheduleByApartmentIdAndDate(any(), any(), any()))
                .thenReturn(new ArrayList<>());
        when(userDao.findByEmail(any()))
                .thenReturn(Optional.of(User.builder().id(1L).email("a@a.com").build()));
        when(orderDao.saveNewOrder(any(), any(), any()))
                .thenReturn(1L);

        when(daoFactory.createApartmentTimetableDao())
                .thenReturn(apartmentTimetableDao);
        when(daoFactory.createUserDao())
                .thenReturn(userDao);
        when(daoFactory.createOrderDao())
                .thenReturn(orderDao);

        //when
        Long newOrderId = testedInstance.createNewOrder(givenOrder());
        //then
        assertThat(newOrderId).isEqualTo(1L);
    }

    private OrderCreationDTO givenOrder() {
        return OrderCreationDTO.builder()
                .orderDate(LocalDateTime.of(2020, 10, 1, 1, 1))
                .startsAt(LocalDateTime.of(2020, 11, 2, 10, 10))
                .endsAt(LocalDateTime.of(2020, 11, 3, 10, 10))
                .userEmail("a@a.com")
                .orderItems(givenOrderItemsDTO())
                .build();
    }

    private List<OrderItemDTO> givenOrderItemsDTO() {
        return Arrays.asList(
                OrderItemDTO.builder()
                        .price(BigDecimal.valueOf(3000))
                        .apartmentId(1L)
                        .build(),
                OrderItemDTO.builder()
                        .price(BigDecimal.valueOf(2000))
                        .apartmentId(2L)
                        .build()
        );
    }

    @Test
    void shouldUpdateOrderStatusCallOrderDaoUpdate() {
        //given
        when(orderDao.findById(any()))
                .thenReturn(givenOrderOptional());
        when(daoFactory.createOrderDao())
                .thenReturn(orderDao);
        when(daoFactory.createOrderItemDao())
                .thenReturn(orderItemDao);

        //when
        testedInstance.updateOrderStatus(UpdateOrderDTO.builder()
                .id(1L)
                .status(OrderStatus.PAID)
                .build());
        //then
        verify(orderDao, times(1)).updateOrderAndTimeslotStatus(any(), any());
    }

    private Optional<Order> givenOrderOptional() {
        return Optional.of(
                Order.builder()
                        .id(1L)
                        .orderDate(LocalDateTime.of(2020, 5, 1, 10, 10))
                        .user(User.builder().id(1L).build())
                        .orderStatus(OrderStatus.NEW)
                        .build()
        );
    }
}