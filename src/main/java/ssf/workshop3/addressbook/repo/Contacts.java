package ssf.workshop3.addressbook.repo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ssf.workshop3.addressbook.model.Contact;

@Repository
public class Contacts {

    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> findAll() {
        return contacts;
    }
    
    public void save(Contact contact) throws IOException {

        contacts.add(contact);

        File f = new File(String.format("C://opt/tmp/data/%s.txt", contact.getId()));

        OutputStream os = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(os);
        pw.println(contact.getName());
        pw.println(contact.getEmail());
        pw.println(contact.getPhone());
        pw.println(contact.getBirthday().toString());
        pw.flush();
        os.close();
    }
}
