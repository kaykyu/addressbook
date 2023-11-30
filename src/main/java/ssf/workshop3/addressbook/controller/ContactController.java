package ssf.workshop3.addressbook.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import ssf.workshop3.addressbook.model.Contact;
import ssf.workshop3.addressbook.repo.ContactsRedis;

@Controller
@RequestMapping(value = { "/", "/index.html" })
public class ContactController {

    @Autowired
    ContactsRedis repo;

    @GetMapping("/add")
    public String addInfo(Model model) {

        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        return "index";
    }

    @PostMapping("/contact")
    public ModelAndView saveContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult binding, Model model)
            throws IOException {

        LocalDate birthDate = LocalDate.ofInstant(contact.getBirthday().toInstant(), ZoneId.systemDefault());
        Period diff = Period.between(birthDate, LocalDate.now());
        int age = diff.getYears();
        ModelAndView mav = new ModelAndView();

        if (binding.hasErrors()) {
            mav.setViewName("index");
            return mav;
        } else if (age < 10 || age > 99) {
            model.addAttribute("ageError", "Age must be more than 10 and less than 100");
            mav.setViewName("index");
            return mav;
        }

        final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        contact.setId(sb.toString());

        repo.save(contact);
        mav.setStatus(HttpStatusCode.valueOf(201));
        mav.setViewName("success");
        return mav;
    }

    @GetMapping("/contact/{id}")
    public ModelAndView getContact(@PathVariable("id") String id, Model model) {

        List<String> info = repo.find(id);
        ModelAndView mav = new ModelAndView();

        if (info == null) {
            mav.setStatus(HttpStatusCode.valueOf(404));
            mav.setViewName("error404");
            return mav;
        }
        
        model.addAttribute("name", info.get(0));
        model.addAttribute("email", info.get(1));
        model.addAttribute("phone", info.get(2));
        model.addAttribute("birthday", info.get(3));

        mav.setViewName("contactlist");
        return mav;
    }

    @GetMapping("/list")
    public String list(Model model) throws IOException {

        Map<String, String> contactMap = repo.listAll();
        model.addAttribute("contacts", contactMap);
        return "list";
    }

}
