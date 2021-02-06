package ua.training.servlet_project.model.util;

import ua.training.servlet_project.controller.dto.BookingRequestCreationDTO;
import ua.training.servlet_project.controller.dto.BookingRequestItemDTO;
import ua.training.servlet_project.controller.dto.UserRegistrationDTO;
import ua.training.servlet_project.controller.dto.VacationDateDTO;
import ua.training.servlet_project.model.entity.RoomType;
import ua.training.servlet_project.model.entity.User;
import ua.training.servlet_project.model.exceptions.BookingRequestCreationException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ua.training.servlet_project.model.util.RequestParamsParser.*;

public class RequestConverter {
    private static final String CREATION_EXCEPTION_NOT_ENOUGH_PARAMETERS = "Not enough parameters for creation of request!";

    private RequestConverter() {

    }

    public static UserRegistrationDTO parseUserRegistrationDTO(HttpServletRequest request) {
        return UserRegistrationDTO.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
    }

    public static BookingRequestCreationDTO parseBookingRequestDTO(HttpServletRequest request) {
        VacationDateDTO vacationDateDTO = parseVacationDateFromRequest(request);
        Long userId = ((User) request.getSession().getAttribute("user")).getId();
        List<Integer> bedsCountInputList = parseListOfInteger(request.getParameterValues("bedsCountInput"),
                new BookingRequestCreationException(CREATION_EXCEPTION_NOT_ENOUGH_PARAMETERS));
        List<RoomType> typesList = parseListOfRoomType(request.getParameterValues("typeSelect"),
                new BookingRequestCreationException(CREATION_EXCEPTION_NOT_ENOUGH_PARAMETERS));

        if (bedsCountInputList.size() != typesList.size()) {
            throw new BookingRequestCreationException(CREATION_EXCEPTION_NOT_ENOUGH_PARAMETERS);
        }

        return BookingRequestCreationDTO.builder()
                .userId(userId)
                .startsAt(vacationDateDTO.getStartsAt())
                .endsAt(vacationDateDTO.getEndsAt())
                .requestItems(getBookingRequestItemsDTOList(bedsCountInputList, typesList))
                .build();
    }

    private static List<BookingRequestItemDTO> getBookingRequestItemsDTOList(
            List<Integer> bedsCountInputList, List<RoomType> typesList) {
        List<BookingRequestItemDTO> items = new ArrayList<>();

        for (int i = 0; i < bedsCountInputList.size(); i++) {
            items.add(
                    BookingRequestItemDTO.builder()
                            .bedsCount(bedsCountInputList.get(i))
                            .type(typesList.get(i))
                            .build()
            );
        }

        return items;
    }
}
