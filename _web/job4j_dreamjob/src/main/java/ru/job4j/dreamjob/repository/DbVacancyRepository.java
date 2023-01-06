package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class DbVacancyRepository implements VacancyRepository {

    private final BasicDataSource pool;

    public DbVacancyRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT into vacancy(title, description, creationDate, visible, cityId) VALUES (?,?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, vacancy.getTitle());
            ps.setString(2, vacancy.getDescription());
            ps.setObject(3, vacancy.getCreationDate(), Types.TIMESTAMP);
            ps.setBoolean(4, vacancy.isVisible());
            ps.setInt(5, vacancy.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    vacancy.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from vacancy where id=?")) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            rsl = row == 0 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update vacancy set title = ?, description=?, visible=?, cityId=? where id=?")) {
            ps.setString(1, vacancy.getTitle());
            ps.setString(2, vacancy.getDescription());
            ps.setBoolean(3, vacancy.isVisible());
            ps.setInt(4, vacancy.getCityId());
            ps.setInt(5, vacancy.getId());
            int row = ps.executeUpdate();
            rsl = (row == 0 ? false : true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        Vacancy vacancy = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from vacancy where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
//                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    boolean visible = rs.getBoolean("visible");
                    int cityId = rs.getInt("cityId");
                    vacancy = new Vacancy(id, title, description, visible, cityId);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(vacancy);
//        return Optional.empty();
    }

    @Override
    public Collection<Vacancy> findAll() {
        List<Vacancy> vacancies = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from vacancy")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int id = it.getInt("id");
                    String title = it.getString("title");
                    String description = it.getString("description");
                    boolean visible = it.getBoolean("visible");
                    int cityId = it.getInt("cityId");
                    Vacancy vacancy = new Vacancy(id, title, description, visible, cityId);
                    vacancies.add(vacancy);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vacancies;
    }
}
