package ca.sheridan.wrimicha.controllers;

import ca.sheridan.wrimicha.database.DatabaseAccess;
import ca.sheridan.wrimicha.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    DatabaseAccess da;

    ModelAndView mv;

    public String continents[] = {"Africa", "Asia", "Austrailia/Oceania", "Europe", "North America", "South America"};

    @GetMapping(value = {"/", "/home"})
    public String index() {

        return "home";
    }

    @GetMapping("/editList")
    public ModelAndView goToEditList() {

        mv = new ModelAndView("EditList", "teams", da.getRecords());
        return mv;
    }

    @GetMapping("/deleteList")
    public ModelAndView goToDeleteList() {

        mv = new ModelAndView("DeleteList", "teams", da.getRecords());
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addRecord(Model model) {

        mv = new ModelAndView("add", "team", new Team());
        mv.addObject("continents", continents);

        return mv;
    }

    @GetMapping("/updateRecordById/{id}")
    public ModelAndView updateRecord(@PathVariable("id") Long id) {

        Team team;
        team = da.getRecordById(id).get(0);
        mv = new ModelAndView("edit", "team", team);
        mv.addObject("continents", continents);
        return mv;
    }

    @GetMapping("/deleteRecordById/{id}")
    public ModelAndView deleteRecord(@PathVariable("id") Long id) {

        da.deleteRecordById(id);
        mv = new ModelAndView("DisplayResults", "teams", da.getRecords());
        return mv;
    }

    @GetMapping("/displayResults")
    public ModelAndView goToResults() {

        mv = new ModelAndView("DisplayResults", "teams", da.getRecords());
        return mv;
    }

    @PostMapping("/addRecord")
    public ModelAndView addRecord(@ModelAttribute Team team) {

        da.insertRecord(team);
        return new ModelAndView("DisplayResults", "teams", da.getRecords()); //get that list of students
    }

    @PostMapping("/modifyRecord")
    public ModelAndView editRecord(@ModelAttribute Team team) {

        da.updateRecordById(team);  //insert students
        return new ModelAndView("EditList", "teams", da.getRecords()); //get that list of students
    }

    @PostMapping("/filterResults")
    public ModelAndView filterResults(@RequestParam int filter) {
        if (filter == 0) {
            mv = new ModelAndView("DisplayResults", "teams", da.getRecordsByName());
        } else if (filter == 1) {
            mv = new ModelAndView("DisplayResults", "teams", da.getRecordsByContinent());
        } else {
            mv = new ModelAndView("DisplayResults", "teams", da.getRecordsByPoints());
        }
        return mv;
    }

    @PostMapping("/searchFilter")
    public ModelAndView searchFilter(String searchType, String searchValue) {
        mv = new ModelAndView("EditList", "teams", da.getRecordByCountryContinent(searchType, searchValue));
        return mv;
    }
}