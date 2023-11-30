// package ssf.workshop3.addressbook.repo;

// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.FileReader;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.io.PrintWriter;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.springframework.stereotype.Repository;

// import ssf.workshop3.addressbook.model.Contact;

// @Repository
// public class Contacts {

//     private static String database = "";

//     public static void setDatabase(String database) {
//         Contacts.database = database;
//     }

//     public Map<String, String> listAll() throws IOException {

//         Map<String, String> contactMap = new HashMap<>();
//         File f = new File(database);
//         File[] files = f.listFiles();
//         for (File file : files) {
//             FileReader fr = new FileReader(file);
//             BufferedReader br = new BufferedReader(fr);
//             contactMap.put(file.getName().substring(0, (file.getName().length() - 4)), br.readLine());
//             fr.close();
//         }

//         return contactMap;
//     }

//     public List<String> find(String id) {

//         File f = new File(database);
//         File[] files = f.listFiles();
//         for (File file : files) {
//             if (file.getName().equals(id + ".txt")) {
//                 f = file;
//             }
//         }

//         List<String> list = new ArrayList<>();

//         try (FileReader fr = new FileReader(f)) {
//             BufferedReader br = new BufferedReader(fr);
//             list = br.lines().toList();
//         } catch (FileNotFoundException e) {
//             return null;
//         } catch (IOException e) {
//             System.out.println("error");
//         }

//         return list;
//     }

//     public void save(Contact contact) throws IOException {

//         File f = new File(String.format("%s/%s.txt", database, contact.getId()));

//         OutputStream os = new FileOutputStream(f);
//         PrintWriter pw = new PrintWriter(os);
//         pw.println(contact.getName());
//         pw.println(contact.getEmail());
//         pw.println(contact.getPhone());
//         pw.println(contact.getBirthday().toString());
//         pw.flush();
//         os.close();
//     }

// }
