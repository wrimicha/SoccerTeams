package ca.sheridan.wrimicha.database;

import ca.sheridan.wrimicha.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    NamedParameterJdbcTemplate jdbc;

    public void  insertRecord(String  country, String continent, int gamesPlayed, int gamesWon, int gamesDrawn, int gamesLost){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO record(country, continent, gamesPlayed, gamesWon, gamesDrawn, gamesLost) VALUES(:country1, :continent1, :gamesPlayed1, :gamesWon1, :gamesDrawn1, :gamesLost1)";
        namedParameters.addValue("country1" , country);
        namedParameters.addValue("continent1" , continent);
        namedParameters.addValue("gamesPlayed1" , gamesPlayed);
        namedParameters.addValue("gamesWon1" , gamesWon);
        namedParameters.addValue("gamesDrawn1" , gamesDrawn);
        namedParameters.addValue("gamesLost1" , gamesLost);
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0)
            System.out.println("Record record was added successfully!");

    }

    public List<Record> getRecords(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));

    }

}