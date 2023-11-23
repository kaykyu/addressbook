package ssf.workshop3.addressbook.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf.workshop3.addressbook.model.Contact;
import ssf.workshop3.addressbook.repo.Contacts;

@Controller
@RequestMapping(value = { "/", "/index.html" })
public class ContactController {

    @Autowired
    Contacts repo;

    @GetMapping("/add")
    public String addInfo(Model model) {

        Contact contact = new Contact();
        model.addAttribute("contact", contact);

        return "index";
    }

    @PostMapping("/contact")
    public String saveContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult binding, Model model)
            throws IOException {

        LocalDate birthDate = LocalDate.ofInstant(contact.getBirthday().toInstant(), ZoneId.systemDefault());
        Period diff = Period.between(birthDate, LocalDate.now());
        int age = diff.getYears();

        if (binding.hasErrors()) {
            return "index";
        } else if (age < 10 || age > 100) {
            model.addAttribute("ageError", "Age must be more than 10 and less than 100");
            return "index";
        }

        final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        contact.setId(sb.toString());

        repo.save(contact);
        return "success";
    }


    


}
