package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract ApartmentDao createApartmentDao();

    public abstract ApartmentTimetableDao createApartmentTimetableDao();

    public abstract ApartmentDescriptionDao createApartmentDescriptionDao();

    public abstract BookingRequestDao createBookingRequestDao();

    public abstract BookingRequestItemDao createBookingRequestItemDao();

    public abstract OrderDao createOrderDao();

    public abstract OrderItemDao createOrderItemDao();

    public abstract UserDao createUserDao();

    public abstract RuleDao createRuleDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
