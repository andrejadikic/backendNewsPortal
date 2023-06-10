package com.example.backend.adapter.mysql.user;

import com.example.backend.application.MySqlAbstractRepository;
import com.example.backend.application.dto.*;
import com.example.backend.model.user.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {
    @Override
    public User add(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO users (email, name, surname, type, password, active) VALUES (?, ?,?, ?,?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getType());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setBoolean(6, user.getType().equals(User.UserType.ADMIN) || user.isActive());
            preparedStatement.executeUpdate();
            System.out.println(preparedStatement.getGeneratedKeys());
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
                user.setType(User.UserType.CREATOR);
                user.setActive(true);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
           this.closeStatement(preparedStatement);
           this.closeResultSet(resultSet);
           this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                users.add(new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("type"), resultSet.getString("password")
                ,resultSet.getBoolean("active")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
            this.closeStatement(statement);
        }
        return users;
    }

    @Override
    public User get(Integer id) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("type"), resultSet.getString("password"),
                        resultSet.getBoolean("active"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void delete(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM users where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public User get(String email, String password) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("type"), resultSet.getString("password"),
                        resultSet.getBoolean("active"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User get(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ?");
            preparedStatement.setString(1, email );
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("type"), resultSet.getString("password"),
                        resultSet.getBoolean("active"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void update(Integer id, UserUpdateDto user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE users SET email = ?, name = ?, surname = ?, type = ?, active = ? WHERE id = ?");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getType());
            preparedStatement.setBoolean(5, user.isActive());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
        }
    }
}
