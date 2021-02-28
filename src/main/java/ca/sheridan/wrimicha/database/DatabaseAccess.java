package ca.sheridan.wrimicha.database;

import ca.sheridan.wrimicha.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    NamedParameterJdbcTemplate jdbc;


    public void  insertRecord(@ModelAttribute Record record){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO record(country, continent, gamesPlayed, gamesWon, gamesDrawn, gamesLost)" +
                " VALUES(:country, :continent, :gamesPlayed, :gamesWon, :gamesDrawn, :gamesLost)";
        namedParameters.addValue("country" , record.getCountry());
        namedParameters.addValue("continent" , record.getContinent());
        namedParameters.addValue("gamesPlayed" , record.getGamesPlayed());
        namedParameters.addValue("gamesWon" , record.getGamesWon());
        namedParameters.addValue("gamesDrawn" , record.getGamesDrawn());
        namedParameters.addValue("gamesLost" , record.getGamesLost());
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0)
            System.out.println("Record record was added successfully!");

    }

//    public void  insertRecord(String  country, String continent, int gamesPlayed, int gamesWon, int gamesDrawn, int gamesLost){
//
//        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//        String query = "INSERT INTO record(country, continent, gamesPlayed, gamesWon, gamesDrawn, gamesLost) VALUES(:country, :continent, :gamesPlayed, :gamesWon, :gamesDrawn, :gamesLost)";
//        namedParameters.addValue("country" , country);
//        namedParameters.addValue("continent" , continent);
//        namedParameters.addValue("gamesPlayed" , gamesPlayed);
//        namedParameters.addValue("gamesWon" , gamesWon);
//        namedParameters.addValue("gamesDrawn" , gamesDrawn);
//        namedParameters.addValue("gamesLost" , gamesLost);
//        int rowsAffected = jdbc.update(query,namedParameters);
//        if (rowsAffected > 0)
//            System.out.println("Record record was added successfully!");
//
//    }

    public List<Record> getRecords(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));

    }


    public List<Record> searchRecordsByCountry(String searchValue){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record WHERE country LIKE %:searchValue%";
        namedParameters.addValue("country" , searchValue);
        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));
    }

//    public List<Record> searchRecordsByContinent(int id){
//
//        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//        String query = "SELECT * FROM record ORDER BY continent";
//
//        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));
//
//    }


    public List<Record> getRecordsByName(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record ORDER BY country";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));

    }

    public List<Record> getRecordsByContinent(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record ORDER BY continent";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));

    }

    public List<Record> getRecordsByPoints(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record ORDER BY (gamesWon * 3 + gamesDrawn) DESC";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));

    }

    public List<Record> getRecordById(Long id){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM record WHERE id =:id";
        namedParameters.addValue("id" , id);
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

    public void deleteRecordById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM record WHERE id = :id";
        namedParameters.addValue("id", id);
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0)
            System.out.println("Team record was deleted successfully!");

    }

    public List<Record> getRecordByCountryContinent(String searchType, String searchValue){
        String query;
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        if (searchType.equals("country")){
            query = "SELECT * FROM record WHERE country LIKE :country";
            namedParameters.addValue("country", "%" + searchValue + "%");
        } else {
            query = "SELECT * FROM record WHERE continent LIKE :continent";
            namedParameters.addValue("continent", "%" + searchValue + "%");
        }
        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Record>(Record.class));
    }


}