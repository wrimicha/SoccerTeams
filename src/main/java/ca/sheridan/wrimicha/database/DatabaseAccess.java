package ca.sheridan.wrimicha.database;

import ca.sheridan.wrimicha.model.Team;
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

    public void  insertRecord(@ModelAttribute Team team){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO Teams(country, continent, gamesPlayed, gamesWon, gamesDrawn, gamesLost)" +
                " VALUES(:country, :continent, :gamesPlayed, :gamesWon, :gamesDrawn, :gamesLost)";
        namedParameters.addValue("country" , team.getCountry());
        namedParameters.addValue("continent" , team.getContinent());
        namedParameters.addValue("gamesPlayed" , team.getGamesPlayed());
        namedParameters.addValue("gamesWon" , team.getGamesWon());
        namedParameters.addValue("gamesDrawn" , team.getGamesDrawn());
        namedParameters.addValue("gamesLost" , team.getGamesLost());
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0)
            System.out.println("Team record was added successfully!");
    }

    public List<Team> getRecords(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> searchRecordsByCountry(String searchValue){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams WHERE country LIKE %:searchValue%";
        namedParameters.addValue("country" , searchValue);
        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> getRecordsByName(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams ORDER BY country";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> getRecordsByContinent(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams ORDER BY continent";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> getRecordsByPoints(){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams ORDER BY (gamesWon * 3 + gamesDrawn) DESC";

        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public List<Team> getRecordById(Long id){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM Teams WHERE id =:id";
        namedParameters.addValue("id" , id);
        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }

    public void updateRecordById(Team team){

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query =
                "UPDATE Teams SET country =:country, continent =:continent, gamesPlayed =:gamesPlayed, gamesWon =:gamesWon, gamesDrawn =:gamesDrawn, gamesLost =:gamesLost WHERE id = :id";
        namedParameters.addValue("id" , team.getId());
        namedParameters.addValue("country" , team.getCountry());
        namedParameters.addValue("continent" , team.getContinent());
        namedParameters.addValue("gamesPlayed" , team.getGamesPlayed());
        namedParameters.addValue("gamesWon" , team.getGamesWon());
        namedParameters.addValue("gamesDrawn" , team.getGamesDrawn());
        namedParameters.addValue("gamesLost" , team.getGamesLost());
        int rowsAffected = jdbc.update(query,namedParameters);
        if (rowsAffected > 0)
            System.out.println("Student record was updated successfully!");
    }

    public void deleteRecordById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM Teams WHERE id = :id";
        namedParameters.addValue("id", id);
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0)
            System.out.println("Team record was deleted successfully!");
    }

    public List<Team> getRecordByCountryContinent(String searchType, String searchValue){
        String query;
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        if (searchType.equals("country")){
            query = "SELECT * FROM Teams WHERE country LIKE :country";
            namedParameters.addValue("country", "%" + searchValue + "%");
        } else {
            query = "SELECT * FROM Teams WHERE continent LIKE :continent";
            namedParameters.addValue("continent", "%" + searchValue + "%");
        }
        return jdbc.query(query,namedParameters,new BeanPropertyRowMapper<Team>(Team.class));
    }
}