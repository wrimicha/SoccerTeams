package ca.sheridan.wrimicha.controllers;

import ca.sheridan.wrimicha.database.DatabaseAccess;
import ca.sheridan.wrimicha.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/add")
    public String addRecord(Model model){

        model.addAttribute("record", new Record());
        return "add";
    }

    @GetMapping("/edit")
    public String editRecord(Model model, Record record){

        model.addAttribute("record", record);
        return "add";
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
    public ModelAndView processRecord(@ModelAttribute Record record){

        da.insertRecord(record.getCountry(), record.getContinent(), record.getGamesPlayed(), record.getGamesWon(), record.getGamesDrawn(), record.getGamesLost());  //insert students
        return new ModelAndView("home", "records", da.getRecords()); //get that list of students
    }
}
