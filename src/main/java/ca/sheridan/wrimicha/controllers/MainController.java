package ca.sheridan.wrimicha.controllers;

import ca.sheridan.wrimicha.database.DatabaseAccess;
import ca.sheridan.wrimicha.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    DatabaseAccess da;

    ModelAndView mv;

    @GetMapping("/")
    public ModelAndView index(){

        mv = new ModelAndView("home", "records",da.getRecords());
        //mv.addObject("record", new Record());

        return mv;
    }

    @GetMapping("/editList")
    public ModelAndView goToEditList(){

        mv = new ModelAndView("editList", "records",da.getRecords());
        //mv.addObject("record", new Record());

        return mv;
    }

    @GetMapping("/home")
    public String goHome(){
        return "home";
    }

    @GetMapping("/add")
    public String addRecord(Model model){

        model.addAttribute("record", new Record());
        return "add";
    }


    @GetMapping("/updateRecordById/{id}")
    public ModelAndView updateRecord(@PathVariable("id") Long id){

        Record record;
        record = da.getRecordById(id).get(0);
        mv = new ModelAndView("edit", "record",record);
        return mv;
    }

//    @GetMapping("/add")
//    public ModelAndView gotoAdd(Model model){
//
//        //mv = new ModelAndView("add", "records",da.getRecords());
//        mv.addObject("record", new Record());
//
//        return mv;
//    }

    @PostMapping ("/addRecord")
    public ModelAndView addRecord(@ModelAttribute Record record){

        da.insertRecord(record.getCountry(), record.getContinent(), record.getGamesPlayed(), record.getGamesWon(), record.getGamesDrawn(), record.getGamesLost());
        return new ModelAndView("home", "records", da.getRecords()); //get that list of students
    }

    @PostMapping ("/modifyRecord")
    public ModelAndView editRecord(@ModelAttribute Record record){

        da.updateRecordById(record);  //insert students
        return new ModelAndView("editList", "records", da.getRecords()); //get that list of students
    }
}
