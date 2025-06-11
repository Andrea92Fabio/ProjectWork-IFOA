package it.ifts.ifoa.teletubbies.repository;

import java.sql.*;
import java.time.Instant;

import it.ifts.ifoa.teletubbies.entity.User;
import it.ifts.ifoa.teletubbies.exception.InsertFailedException;

public class UserRepository
{
    private final Connection connection;

    public UserRepository(Connection connection)
    {
        this.connection = connection;
    }

    //insert user into the db
    public void saveUser(User user)
    {
        String sql = """
                INSERT INTO customers (email, name, surname,\
                 birthdate, gender, fiscalCode,\
                 phoneNumber, residencyCountry, residencyAddress,\
                 residencyZipCode, shipCountry, shipAddress,\
                 shipZipCode, privacy, rules) \
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        try
        {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());

            statement.setDate(4, Date.valueOf(user.getBirthDate()));
            statement.setString(5, user.getGender());
            statement.setString(6, user.getFiscalCode());

            statement.setString(7, user.getPhoneNumber());
            statement.setString(8, user.getResidencyCountry());
            statement.setString(9, user.getResidencyAddress());

            statement.setString(10, user.getResidencyZipCode());
            statement.setString(11, user.getShipCountry());
            statement.setString(12, user.getShipAddress());

            statement.setString(13, user.getShipZipCode());
            statement.setBoolean(14, user.getPrivacy());
            statement.setBoolean(15, user.getRules());

            if (statement.executeUpdate() < 1)
            {
                //should never happen, just to be sure
                throw new InsertFailedException("insert failed");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    //if user is italian, verify that fiscal code isn't already taken
    public boolean isFiscalCodeAlreadyTaken(User user)
    {
        String sql = "SELECT id FROM customers WHERE fiscalCode = ?";
        boolean retvalue = false;
        try
        {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, user.getFiscalCode());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                retvalue = true;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return retvalue;
    }

    //verify that email isn't already taken
    public boolean isEmailAlreadyTaken(User user)
    {
        String sql = "SELECT id FROM customers WHERE email = ?";
        boolean retvalue = false;
        try
        {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                retvalue = true;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return retvalue;
    }

    public void doubleOptIn(String tokenId)
    {
        String sql = "UPDATE customers SET name = ? WHERE tokenId = ?";
        try
        {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, "suca");
            statement.setString(2, tokenId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean isConfirmationTop499(String tokenId)
    {
        String sql = "SELECT COUNT(*) FROM (" +
                "SELECT tokenId FROM customers WHERE confirmedDate IS NOT NULL ORDER BY confirmedDate ASC LIMIT 499) AS top " +
                "WHERE tokenId = ?";
        try
        {
            PreparedStatement candidateStatement = this.connection.prepareStatement(sql);
            candidateStatement.setString(1, tokenId);
            ResultSet resultSet = candidateStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1) > 0;
            } return false;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}




