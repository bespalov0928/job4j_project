package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
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
public class DbCandidateRepository implements CandidateRepository {

    private final BasicDataSource pool;

    public DbCandidateRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Candidate save(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT into candidate(title, description, creationDate, visible, cityId, nameFile, photo) VALUES (?,?,?,?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getTitle());
            ps.setString(2, candidate.getDescription());
            ps.setObject(3, candidate.getCreationDate(), Types.TIMESTAMP);
            ps.setBoolean(4, candidate.isVisible());
            ps.setInt(5, candidate.getCityId());
            ps.setString(6, candidate.getNameFile());
            ps.setBytes(7, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from candidate where id=?")) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            rsl = row == 0 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public boolean update(Candidate candidate) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update candidate set title = ?, description=?, visible=?, cityId=?, nameFile=?, photo=? where id=?")) {
//             PreparedStatement ps = cn.prepareStatement("update candidate set title = ?, description=?, visible=?, cityId=?, nameFile=? where id=?")) {
            ps.setString(1, candidate.getTitle());
            ps.setString(2, candidate.getDescription());
            ps.setBoolean(3, candidate.isVisible());
            ps.setInt(4, candidate.getCityId());
            ps.setString(5, candidate.getNameFile());
            ps.setBytes(6, candidate.getPhoto());
            ps.setInt(7, candidate.getId());
            int row = ps.executeUpdate();
            rsl = (row == 0 ? false : true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from candidate where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    boolean visible = rs.getBoolean("visible");
                    int cityId = rs.getInt("cityId");
                    String nameFile = rs.getString("nameFile");
                    byte[] photo = rs.getBytes("photo");
                    candidate = new Candidate(id, title, description, visible, cityId);
                    candidate.setNameFile(nameFile);
                    candidate.setPhoto(photo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(candidate);
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int id = it.getInt("id");
                    String title = it.getString("title");
                    String description = it.getString("description");
                    boolean visible = it.getBoolean("visible");
                    int cityId = it.getInt("cityId");
                    String nameFile = it.getString("nameFile");
                    byte[] photo = it.getBytes("photo");
                    Candidate candidate = new Candidate(id, title, description, visible, cityId);
                    candidate.setNameFile(nameFile);
                    candidate.setPhoto(photo);
                    candidates.add(candidate);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }
}
