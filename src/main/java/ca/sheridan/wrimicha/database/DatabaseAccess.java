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
        String query = "INSERT INTO record(country, continent, gamesPlayed, gamesWon, gamesDrawn, gamesLost) VALUES(:country, :continent, :gamesPlayed, :gamesWon, :gamesDrawn, :gamesLost)";
        namedParameters.addValue("country" , country);
        namedParameters.addValue("continent" , continent);
        namedParameters.addValue("gamesPlayed" , gamesPlayed);
        namedParameters.addValue("gamesWon" , gamesWon);
        namedParameters.addValue("gamesDrawn" , gamesDrawn);
        namedParameters.addValue("gamesLost" , gamesLost);
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0)
            System.out.println("Record record was added successfully!");

    }

    public List<Record> getRecords(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));

    }

    public void updateRecordById(Record record){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query =
                "UPDATE record SET country =:country, continent =:continent, gamesPlayed =:gamesPlayed, gamesWon =:gamesWon, gamesDrawn =:gamesDrawn, gamesLost =:gamesLost WHERE id = :id";
        namedParameters.addValue("id" , record.getId());
        namedParameters.addValue("country" , record.getCountry());
        namedParameters.addValue("continent" , record.getContinent());
        namedParameters.addValue("gamesPlayed" , record.getGamesPlayed());
        namedParameters.addValue("gamesWon" , record.getGamesWon());
        namedParameters.addValue("gamesDrawn" , record.getGamesDrawn());
        namedParameters.addValue("gamesLost" , record.getGamesLost());
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0)
            System.out.println("Student record was updated successfully!");
    }

}