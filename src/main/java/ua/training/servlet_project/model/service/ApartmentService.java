package ua.training.servlet_project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.OrderItemDTO;
import ua.training.servlet_project.controller.dto.VacationDateDTO;
import ua.training.servlet_project.model.dao.ApartmentDao;
import ua.training.servlet_project.model.dao.ApartmentDescriptionDao;
import ua.training.servlet_project.model.dao.ApartmentTimetableDao;
import ua.training.servlet_project.model.dao.DaoFactory;
import ua.training.servlet_project.model.entity.*;
import ua.training.servlet_project.model.exceptions.ApartmentNotFoundException;
import ua.training.servlet_project.model.exceptions.DescriptionNotFoundException;
import ua.training.servlet_project.model.exceptions.IllegalDateException;
import ua.training.servlet_project.model.util.PropertiesLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ApartmentService {
    private static final Logger LOGGER = LogManager.getLogger(ApartmentService.class);
    private static final int SETTLEMENT_MINUTES = 0;
    //@TODO move to some constants class
    private final Integer checkInHours;
    private final Integer checkOutHours;
    private final DaoFactory daoFactory;

    public ApartmentService() {
        daoFactory = DaoFactory.getInstance();
        Properties properties = PropertiesLoader.getProperties("application.properties");
        checkInHours = Integer.valueOf(properties.getProperty("apartment.check.in.time"));
        checkOutHours = Integer.valueOf(properties.getProperty("apartment.check.out.time"));
    }

    public Page<Apartment> getAllAvailableApartmentsByDate(Pageable pageable,
                                                           VacationDateDTO vacationDateDTO) {
        LocalDate startsAt = vacationDateDTO.getStartsAt();
        LocalDate endsAt = vacationDateDTO.getEndsAt();

        if (endsAt.isBefore(startsAt)) throw new IllegalDateException("Check out time cannot go before check-in!");

        LocalDateTime checkIn = LocalDateTime.of(startsAt, LocalTime.of(checkInHours, SETTLEMENT_MINUTES));
        LocalDateTime checkOut = LocalDateTime.of(endsAt, LocalTime.of(checkOutHours, SETTLEMENT_MINUTES));

        Page<Apartment> apartmentsPage;
        try (ApartmentDao apartmentDao = daoFactory.createApartmentDao()) {
            apartmentsPage = apartmentDao.findAllAvailableByDate(checkIn, checkOut, pageable);
        }

        for (Apartment a : apartmentsPage.getContent()) {
            LOGGER.info("{}", a);
            LOGGER.info("{}", a.getSchedule());
            LOGGER.info("\n---\n");

            List<ApartmentTimetable> schedule = a.getSchedule();
            if (schedule.isEmpty()) {
                schedule.add(
                        ApartmentTimetable.builder()
                                .status(RoomStatus.FREE)
                                .startsAt(checkIn)
                                .endsAt(checkOut)
                                .build()
                );
            }
        }

        return apartmentsPage;
    }

    public Apartment getApartmentByIdAndDate(Long id, VacationDateDTO vacationDateDTO, String lang) {
        //@TODO rewrite

        Apartment apartment;
        try (ApartmentDao apartmentDao = daoFactory.createApartmentDao()) {
            apartment = apartmentDao.findById(id)
                    .orElseThrow(() -> new ApartmentNotFoundException("Cannot find apartment with id " + id));
            LOGGER.info(apartment);
        }

        LocalDateTime checkIn = LocalDateTime.of(vacationDateDTO.getStartsAt(),
                LocalTime.of(checkInHours, SETTLEMENT_MINUTES));
        LocalDateTime checkOut = LocalDateTime.of(vacationDateDTO.getEndsAt(),
                LocalTime.of(checkOutHours, SETTLEMENT_MINUTES));

        List<ApartmentTimetable> schedule;
        try (ApartmentTimetableDao apartmentTimetableDao = daoFactory.createApartmentTimetableDao()) {
            schedule = apartmentTimetableDao.findAllScheduleByApartmentIdAndDate(checkIn, checkOut, apartment.getId());
        }

        if (schedule.isEmpty()) {
            schedule.add(
                    ApartmentTimetable.builder()
                            .status(RoomStatus.FREE)
                            .build()
            );
        }

        LOGGER.info("{}", schedule);

        ApartmentDescription description;
        try (ApartmentDescriptionDao apartmentDescriptionDao = daoFactory.createApartmentDescriptionDao()) {
            description = apartmentDescriptionDao.findApartmentDescriptionByApartmentIdAndLang(id, lang)
                    .orElseThrow(() -> new DescriptionNotFoundException("Cannot find description for apartment with id " + id));
        }

        apartment.setSchedule(schedule);
        apartment.setDescription(description.getDescription());

        return apartment;
    }

    public List<OrderItemDTO> getAllApartmentsByIds(List<Long> ids) {
        List<Apartment> apartments;
        try (ApartmentDao apartmentDao = daoFactory.createApartmentDao()) {
            apartments = apartmentDao.findAllByIds(ids);
        }

        return apartments
                .stream()
                .map(a -> OrderItemDTO.builder()
                        .apartmentId(a.getId())
                        .price(a.getPrice())
                        .amount(1)
                        .build()
                )
                .collect(Collectors.toList());
    }
}
