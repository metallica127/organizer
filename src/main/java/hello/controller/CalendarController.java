package hello;

import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;


import hello.model.Calendar;
import hello.repository.CalendarRepository;

@RestController
public class CalendarController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CalendarRepository calendarRepository;

    @GetMapping(path = "/calendar/add") // Map ONLY GET Requests
    public @ResponseBody
    String addNewCalendar(@RequestParam String name
            , @RequestParam String description) {
        Calendar n = new Calendar();
        n.setName(name);
        n.setDescription(description);
        calendarRepository.save(n);
        return "Saved";
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

    @GetMapping(path = "/calendar/{id}")
    public @ResponseBody
    String updateCalendar(@PathVariable Long id, @RequestParam(required = false) String name
            , @RequestParam(required = false) String description) {
        // This returns a JSON or XML with the users
        Calendar c = calendarRepository.findOne(id);
        if (null != name) {
            c.setName(name);
        }
        if (null != description) {
            c.setDescription(description);
        }
        calendarRepository.save(c);
        return "Saved";

    }

    @GetMapping(path = "/calendar/delete/{id}")
    public @ResponseBody
    String deleteCalendar(@PathVariable Long id) {
        // This returns a JSON or XML with the users
        calendarRepository.delete(id);
        return "Deleted";

    }


}