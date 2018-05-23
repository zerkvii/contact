package com.zenith.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public ContactRepository(JdbcTemplate jdbc){
        this.jdbc=jdbc;
    }
    public List<Contact> findAll(){
        return jdbc.query(
                "select id,phone,email,nickname " + "from contacts order by id",
                new RowMapper<Contact>() {
                    @Override
                    public Contact mapRow(ResultSet resultSet, int i) throws SQLException {
                        Contact contact=new Contact();
                        contact.setId(resultSet.getLong(1));
                        contact.setPhone(resultSet.getString(2));
                        contact.setEmail(resultSet.getString(3));
                        contact.setNickname(resultSet.getString(4));
                        return contact;
                    }
                }
        );
    }
    public void save(Contact contact){
        jdbc.update(" t into contacts "+"(id,phone,email,nickname) "+"values (?,?,?,?)",contact.getId(),contact.getPhone(),contact.getEmail(),contact.getNickname());

    }
}
