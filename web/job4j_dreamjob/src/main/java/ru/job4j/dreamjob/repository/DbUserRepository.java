package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class DbUserRepository implements UserRepository {

    private final BasicDataSource pool;

    public DbUserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<User> save(User user) {
        User userNew = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT into users(email, password) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    int idNew = id.getInt(1);
                    userNew = new User(idNew, user.getEmail(), user.getPassword());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(userNew);
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from users where id=?")) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            if (row != 0) {
                rsl = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public boolean update(User user) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update users set email = ?, password=? where id=?")) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            int row = ps.executeUpdate();
            if (row != 0) {
                rsl = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Optional<User> findById(int id) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    user = new User(id, email, password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(user);
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    int id = it.getInt("id");
                    String email = it.getString("email");
                    String password = it.getString("password");
                    User user = new User(id, email, password);
                    users.add(user);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        User userNew = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String password = rs.getString("password");
                    userNew = new User(id, email, password);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(userNew);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        User userNew = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where email = ? and password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    userNew = new User(id, email, password);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(userNew);
    }

}
