package ssf.workshop3.addressbook.repo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssf.workshop3.addressbook.model.Contact;

@Repository
public class ContactsRedis {

    @Autowired @Qualifier("myredis")
    private RedisTemplate<String, String> template;

    public Map<String, String> listAll() throws IOException {

        HashOperations<String, String, String> hashOps = template.opsForHash();
        Map<String, String> map = new HashMap<>();

        if (null == template.keys("********")) {
            return map;
        }

        for (String users : template.keys("********")) {
                String name = hashOps.get(users, "name");
                map.put(users, name);
            }

        return map;
    }

    public List<String> find(String id) {

        HashOperations<String, String, String> hashOps = template.opsForHash();
        List<String> details = new ArrayList<>();

        if (template.hasKey(id)) {
            details.add(hashOps.get(id, "name"));
            details.add(hashOps.get(id, "email"));
            details.add(hashOps.get(id, "phone"));
            details.add(hashOps.get(id, "birthday"));
        }

        return details;
    }

    public void save(Contact contact) throws IOException {

        HashOperations<String, String, String> hashOps = template.opsForHash();

        hashOps.put(contact.getId(), "name", contact.getName());
        hashOps.put(contact.getId(), "email", contact.getEmail());
        hashOps.put(contact.getId(), "phone", contact.getPhone());
        hashOps.put(contact.getId(), "birthday", contact.getBirthday().toString());
    }
}
