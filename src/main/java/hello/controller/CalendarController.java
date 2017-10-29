package hello.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;


import hello.model.Calendar;
import hello.repository.CalendarRepository;


//@RestController
@Controller
public class CalendarController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CalendarRepository calendarRepository;


    @RequestMapping("/calendar")
    public String calendar(Model model) {
        model.addAttribute("calendars", calendarRepository.findAll());
        return "calendar";
    }

    @RequestMapping(path = "/calendar/add")
    public String addNewCalendar(@RequestParam String name
            , @RequestParam String description, @RequestParam(required = false) Boolean isPrivate) {
        Calendar n = new Calendar();
        n.setName(name);
        n.setDescription(description);
        n.setPrivate(isPrivate);
        if (null == isPrivate) {
            n.setPrivate(false);
        }
        calendarRepository.save(n);
        //return "Saved";
        return "redirect:/calendar";
    }

    @GetMapping(path = "/calendar/all")
    public @ResponseBody
    Iterable<Calendar> getAllCalendars() {
        // This returns a JSON or XML with the users
        return calendarRepository.findAll();
    }

    @GetMapping(path = "/calendar/{id}")
    public @ResponseBody
    Calendar getCalendar(@PathVariable Long id) {
        // This returns a JSON or XML with the users
        return calendarRepository.findOne(id);
        //return "Deleted";

    }

    @RequestMapping(path = "/calendar/update")
    public String updateCalendar(@RequestParam Long id, @RequestParam(required = false) String name
            , @RequestParam(required = false) String description) {
        Calendar c = calendarRepository.findOne(id);
        if (null != name) {
            c.setName(name);
        }
        if (null != description) {
            c.setDescription(description);
        }
        calendarRepository.save(c);
        //return "Saved";
        return "redirect:/calendar";
    }

    @RequestMapping(path = "/calendar/delete")
    public String deleteCalendar(@RequestParam Long id) {
        calendarRepository.delete(id);
        //return "Deleted";
        return "redirect:/calendar";
    }
}