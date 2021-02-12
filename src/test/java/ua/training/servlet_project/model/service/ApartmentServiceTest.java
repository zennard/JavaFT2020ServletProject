package ua.training.servlet_project.model.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.training.servlet_project.controller.dto.ApartmentCriteriaDTO;
import ua.training.servlet_project.controller.dto.OrderItemDTO;
import ua.training.servlet_project.controller.dto.VacationDateDTO;
import ua.training.servlet_project.model.dao.ApartmentDao;
import ua.training.servlet_project.model.dao.ApartmentDescriptionDao;
import ua.training.servlet_project.model.dao.ApartmentTimetableDao;
import ua.training.servlet_project.model.dao.DaoFactory;
import ua.training.servlet_project.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private ApartmentDao apartmentDao;
    @Mock
    private ApartmentTimetableDao apartmentTimetableDao;
    @Mock
    private ApartmentDescriptionDao apartmentDescriptionDao;

    @InjectMocks
    private ApartmentService testedInstance;

    @Test
    void shouldGetAllAvailableApartmentsByValidDate() {
        //given
        when(apartmentDao.findAllAvailableByDate(any(), any(), any()))
                .thenReturn(givenApartmentsPage());
        when(daoFactory.createApartmentDao())
                .thenReturn(apartmentDao);
        LocalDate startsAt = LocalDate.of(2020, 1, 1);
        LocalDate endsAt = LocalDate.of(2020, 1, 2);
        //when
        Page<Apartment> apartments = testedInstance.getAllAvailableApartmentsByDate(null, new VacationDateDTO(startsAt, endsAt));
        //then
        assertThat(apartments.getContent().get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("bedsCount", 2)
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(3000))
                .hasFieldOrPropertyWithValue("type", RoomType.DELUXE);

        assertThat(apartments.getContent().get(0).getSchedule().get(0))
                .hasFieldOrPropertyWithValue("status", RoomStatus.PAID);

        assertThat(apartments.getContent().get(1).getSchedule().get(0))
                .hasFieldOrPropertyWithValue("status", RoomStatus.FREE);
    }

    private Page<Apartment> givenApartmentsPage() {
        return new Page<>(Arrays.asList(
                Apartment.builder()
                        .id(1L)
                        .price(BigDecimal.valueOf(3000))
                        .bedsCount(2)
                        .type(RoomType.DELUXE)
                        .isAvailable(true)
                        .schedule(Collections.singletonList(
                                ApartmentTimetable.builder()
                                        .id(1L)
                                        .status(RoomStatus.PAID)
                                        .startsAt(LocalDateTime.of(2020, 1, 1, 14, 0))
                                        .endsAt(LocalDateTime.of(2020, 1, 2, 12, 0))
                                        .apartment(Apartment.builder().id(1L).build())
                                        .build()
                                )
                        )
                        .build(),
                Apartment.builder()
                        .id(2L)
                        .price(BigDecimal.valueOf(1000))
                        .bedsCount(2)
                        .type(RoomType.STANDARD)
                        .isAvailable(true)
                        .schedule(new ArrayList<>())
                        .build()
        ), null, 1);
    }

    private Page<Apartment> givenFreeApartments() {
        return new Page<>(Arrays.asList(
                Apartment.builder()
                        .id(1L)
                        .price(BigDecimal.valueOf(3000))
                        .bedsCount(2)
                        .type(RoomType.DELUXE)
                        .isAvailable(true)
                        .schedule(new ArrayList<>())
                        .build(),
                Apartment.builder()
                        .id(2L)
                        .price(BigDecimal.valueOf(1000))
                        .bedsCount(2)
                        .type(RoomType.STANDARD)
                        .isAvailable(true)
                        .schedule(new ArrayList<>())
                        .build()
        ), null, 1);
    }

    @Test
    void shouldGetAllAvailableApartmentsByCorrectDateAndCriteria() {
        //given
        when(apartmentDao.findAllFreeAvailableByDate(any(), any(), any(), any()))
                .thenReturn(givenFreeApartments());
        when(daoFactory.createApartmentDao())
                .thenReturn(apartmentDao);
        LocalDate startsAt = LocalDate.of(2020, 1, 1);
        LocalDate endsAt = LocalDate.of(2020, 1, 2);
        //when
        Page<Apartment> apartments = testedInstance.getAllAvailableApartmentsByDate(null,
                new VacationDateDTO(startsAt, endsAt),
                new ApartmentCriteriaDTO(Arrays.asList(RoomType.STANDARD, RoomType.DELUXE), RoomStatus.FREE));
        //then
        assertThat(apartments.getContent().get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("bedsCount", 2)
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(3000))
                .hasFieldOrPropertyWithValue("type", RoomType.DELUXE);

        assertThat(apartments.getContent().get(0).getSchedule().get(0))
                .hasFieldOrPropertyWithValue("status", RoomStatus.FREE);

        assertThat(apartments.getContent().get(1).getSchedule().get(0))
                .hasFieldOrPropertyWithValue("status", RoomStatus.FREE);
    }

    @Test
    void shouldGetApartmentByCorrectIdAndDate() {
        //given
        when(apartmentDao.findById(any()))
                .thenReturn(givenApartment());
        when(apartmentTimetableDao.findAllScheduleByApartmentIdAndDate(any(), any(), any()))
                .thenReturn(givenSchedule());
        when(apartmentDescriptionDao.findApartmentDescriptionByApartmentIdAndLang(any(), any()))
                .thenReturn(givenDescription());
        when(daoFactory.createApartmentDao())
                .thenReturn(apartmentDao);
        when(daoFactory.createApartmentTimetableDao())
                .thenReturn(apartmentTimetableDao);
        when(daoFactory.createApartmentDescriptionDao())
                .thenReturn(apartmentDescriptionDao);
        LocalDate startsAt = LocalDate.of(2020, 1, 1);
        LocalDate endsAt = LocalDate.of(2020, 1, 4);

        //when
        Apartment apartment = testedInstance.getApartmentByIdAndDate(1L,
                new VacationDateDTO(startsAt, endsAt), "en");
        //then
        assertThat(apartment)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("description", "desc en")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(5000))
                .hasFieldOrPropertyWithValue("type", RoomType.PRESIDENT)
                .hasFieldOrPropertyWithValue("bedsCount", 1);
    }

    private Optional<ApartmentDescription> givenDescription() {
        return Optional.of(
                ApartmentDescription.builder()
                        .id(1L)
                        .apartment(Apartment.builder().id(1L).build())
                        .lang(Language.EN)
                        .description("desc en")
                        .build()
        );
    }

    private List<ApartmentTimetable> givenSchedule() {
        return Arrays.asList(
                ApartmentTimetable.builder()
                        .id(1L)
                        .apartment(Apartment.builder().id(1L).build())
                        .startsAt(LocalDateTime.of(2020, 1, 1, 14, 0))
                        .endsAt(LocalDateTime.of(2020, 1, 2, 12, 0))
                        .status(RoomStatus.PAID)
                        .build(),
                ApartmentTimetable.builder()
                        .id(2L)
                        .apartment(Apartment.builder().id(1L).build())
                        .startsAt(LocalDateTime.of(2020, 1, 3, 14, 0))
                        .endsAt(LocalDateTime.of(2020, 1, 4, 12, 0))
                        .status(RoomStatus.BOOKED)
                        .build()
        );
    }

    private Optional<Apartment> givenApartment() {
        return Optional.of(
                Apartment.builder()
                        .id(1L)
                        .type(RoomType.PRESIDENT)
                        .price(BigDecimal.valueOf(5000))
                        .bedsCount(1)
                        .isAvailable(true)
                        .build()
        );
    }

    @Test
    void shouldReturnApartmentsByIds() {
        //given
        when(apartmentDao.findAllByIds(any()))
                .thenReturn(givenApartments());
        when(daoFactory.createApartmentDao())
                .thenReturn(apartmentDao);

        //when
        List<OrderItemDTO> apartments = testedInstance.getAllApartmentsByIds(Arrays.asList(1L, 2L));
        //then
        assertThat(apartments.get(0))
                .hasFieldOrPropertyWithValue("apartmentId", 1L)
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(3000));
    }

    private List<Apartment> givenApartments() {
        return Arrays.asList(
                Apartment.builder()
                        .id(1L)
                        .type(RoomType.PRESIDENT)
                        .price(BigDecimal.valueOf(3000))
                        .bedsCount(1)
                        .isAvailable(true)
                        .build(),
                Apartment.builder()
                        .id(2L)
                        .type(RoomType.STANDARD)
                        .price(BigDecimal.valueOf(1000))
                        .bedsCount(1)
                        .isAvailable(true)
                        .build()
        );
    }
}