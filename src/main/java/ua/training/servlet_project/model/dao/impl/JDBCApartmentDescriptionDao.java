package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.ApartmentDescriptionDao;
import ua.training.servlet_project.model.dao.mapper.ApartmentDescriptionMapper;
import ua.training.servlet_project.model.entity.ApartmentDescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCApartmentDescriptionDao implements ApartmentDescriptionDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCApartmentTimetableDao.class);
    private Connection connection;

    public JDBCApartmentDescriptionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(ApartmentDescription entity) {

    }

    @Override
    public Optional<ApartmentDescription> findById(Long id) {
        return null;
    }

    @Override
    public Optional<ApartmentDescription> findApartmentDescriptionByApartmentIdAndLang(Long id, String lang) {
        Optional<ApartmentDescription> description = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT * FROM apartment_description " +
                        "WHERE apartment_id = ? AND lang = ?")) {
            ps.setLong(1, id);
            ps.setString(2, lang.toUpperCase());

            ResultSet rs = ps.executeQuery();
            ApartmentDescriptionMapper mapper = new ApartmentDescriptionMapper();
            if (rs.next()) {
                description = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }

        return description;
    }

    @Override
    public List<ApartmentDescription> findAll() {
        return null;
    }

    @Override
    public void update(ApartmentDescription entity) {

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
