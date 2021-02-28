package ca.sheridan.wrimicha.controllers;

import ca.sheridan.wrimicha.database.DatabaseAccess;
import ca.sheridan.wrimicha.model.Record;
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


    @GetMapping("/")
    public ModelAndView index(){

        mv = new ModelAndView("home", "records",da.getRecords());
        //mv.addObject("record", new Record());

        return mv;
    }

    @GetMapping("/editList")
    public ModelAndView goToEditList(){

        mv = new ModelAndView("EditList", "records",da.getRecords());
        //mv.addObject("record", new Record());

        return mv;
    }

    @GetMapping("/deleteList")
    public ModelAndView goToDeleteList(){

        mv = new ModelAndView("DeleteList", "records",da.getRecords());
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

//    @PostMapping("/addCountry")
//    public String add(Model model){
//
//        model.addAttribute("country", new Record());
//        return "add";
//    }


    @GetMapping("/updateRecordById/{id}")
    public ModelAndView updateRecord(@PathVariable("id") Long id){

        Record record;
        record = da.getRecordById(id).get(0);
        mv = new ModelAndView("edit", "record",record);
        return mv;
    }

    @GetMapping("/deleteRecordById/{id}")
    public ModelAndView deleteRecord(@PathVariable("id") Long id){

        da.deleteRecordById(id);
        mv = new ModelAndView("DeleteList", "records",da.getRecords());
        return mv;
    }

    @GetMapping("/displayResults")
    public ModelAndView goToResults(){

        mv = new ModelAndView("DisplayResults", "records",da.getRecords());
        return mv;
    }

    @PostMapping ("/addRecord")
    public ModelAndView addRecord(@ModelAttribute Record record){

        da.insertRecord(record);
        //da.insertRecord(record.getCountry(), record.getContinent(), record.getGamesPlayed(), record.getGamesWon(), record.getGamesDrawn(), record.getGamesLost());
        return new ModelAndView("home", "records", da.getRecords()); //get that list of students
    }

    @PostMapping ("/modifyRecord")
    public ModelAndView editRecord(@ModelAttribute Record record){

        da.updateRecordById(record);  //insert students
        return new ModelAndView("EditList", "records", da.getRecords()); //get that list of students
    }

    @PostMapping("/filterResults")
    public ModelAndView filterResults(@RequestParam int filter){
        if(filter==0){
            mv = new ModelAndView("DisplayResults", "records", da.getRecordsByName());
        } else if (filter==1){
            mv = new ModelAndView("DisplayResults", "records", da.getRecordsByContinent());
        } else {
            mv = new ModelAndView("DisplayResults", "records", da.getRecordsByPoints());
        }
        return mv;
    }

    @PostMapping("/searchFilter")
    public ModelAndView searchFilter(String searchType, String searchValue){
        mv = new ModelAndView("EditList", "records", da.getRecordByCountryContinent(searchType, searchValue));
        return mv;
    }


//    @PostMapping("/searchFilter")
//    public ModelAndView searchFilter(@RequestParam String searchValue, @RequestParam int filter){
//        if(filter==0){
//            mv = new ModelAndView("editList", "records", da.searchRecordsByCountry(searchValue));
//        } else {
//            mv = new ModelAndView("editList", "records", da.searchRecordsByCountry(searchValue));
////            mv = new ModelAndView("editList", "records", da.searchRecordsByContinent(searchValue));
//        }
//        return mv;
//    }
}
