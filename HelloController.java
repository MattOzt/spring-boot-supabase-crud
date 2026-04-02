package com.example.adp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@RestController
class HelloController {

    //private final TreeMap<Long, MyPOJO> map = new TreeMap<>();

    private final PojoRepository pojoRepository;
    private final PostLogRepository postLogRepository;

    public HelloController(PojoRepository pojoRepository, PostLogRepository postLogRepository){
        this.pojoRepository = pojoRepository;
        this.postLogRepository = postLogRepository;
    }


    @GetMapping("/")
    public String hello() {
        return "hello world!";
    }

    @GetMapping("/helloagain")
    public String[] helloAgain() {
        return new String[]{"hello world!", "again", "hang on what's this?", "evict", "dimly", "opal"};
    }

//    @GetMapping("/pojo")
//    public Collection<MyPOJO> getAll() {
//        return map.values();
//    }
    @GetMapping("/pojo")
    public Collection<MyPOJO> getAll() {
        return this.pojoRepository.findAll();
    }

//    @GetMapping("/pojo/{id}")
//    public MyPOJO getById(@PathVariable("id") Long id) {
//        MyPOJO item = map.get(id);
//        if (item == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such id " + id);
//        } else {
//            return item;
//        }
//    }
    @GetMapping("/pojo/{id}")
    public MyPOJO getById(@PathVariable("id") Long id) {
        return this.pojoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such id " + id));
    }

    @GetMapping("/pojo/lastnocase/{lastname}")
    public Collection<MyPOJO> findByLastNameAuto(@PathVariable("lastname") String lastname) {
        return this.pojoRepository.findByLastNameIgnoreCase(lastname);
    }

    @GetMapping("/appinfo")
    public String[] getInfo() {
        final Class c = this.getClass();
        final String cResourceName = c.getName().replace('.', '/') + ".class";
        final URL classURL = c.getClassLoader().getResource(cResourceName);
        try {
            String protocol = classURL.getProtocol();
            if (protocol.equals("file")) {
                String pathToPackageRoot = URLDecoder.decode(classURL.getPath(), StandardCharsets.UTF_8);
                pathToPackageRoot = pathToPackageRoot.substring(0, pathToPackageRoot.length() - (cResourceName.length() + 1));
                return new String[] { protocol, pathToPackageRoot };
            } else if (protocol.equals("jar")) {
                String jarURL = classURL.toURI().getRawSchemeSpecificPart();
                final int i = jarURL.indexOf("!");
                if (i != -1) { jarURL = jarURL.substring(0, i); }
                return new String[] { protocol, jarURL };
            }
        } catch (final URISyntaxException x) {
            x.printStackTrace();
        }
        return null;
    }

//    @PostMapping("/pojo")
//    public MyPOJO postPojo(@RequestBody MyPOJO body) {
//        long nextId = 1;
//        if (!this.map.isEmpty()) {
//            nextId = ((SortedSet<Long>) this.map.keySet()).last() + 1;
//        }
//        MyPOJO newItem = new MyPOJO(body.getFirstName(), body.getLastName(), nextId);
//        this.map.put(nextId, newItem);
//        return newItem;
//    }
    @PostMapping("/pojo")
    public MyPOJO postPojo(@RequestBody MyPOJO body) {
        body.setIdNumber(0); // clear any ID number to prevent update
        MyPOJO pojo = this.pojoRepository.save(body);
        this.postLogRepository.save(new PostLog(pojo.getIdNumber()));
        return pojo;
    }

    @PostMapping("pojos")
    public List<MyPOJO> postABunch(@RequestBody List<MyPOJO> list) {
        List<MyPOJO> returnList = new ArrayList<>(list.size());
        for(MyPOJO pojo : list) {
            returnList.add(postPojo(pojo));
        }
        return returnList;
    }

    @GetMapping("/pojo/{first}/{last}/{id}")
    public MyPOJO myPojo(@PathVariable("first") String firstName,
                         @PathVariable("last") String lastName,
                         @PathVariable("id") int idNumber) {
        return new MyPOJO(firstName, lastName, idNumber);
    }

//    @PutMapping("/pojo")
//    public MyPOJO putPojo(@RequestBody MyPOJO body) {
//        this.map.put(body.getIdNumber(), body);
//        return body;
//    }
    @PutMapping("/pojo")
    public MyPOJO putPojo(@RequestBody MyPOJO body) {
        return this.pojoRepository.save(body);
    }
//    @DeleteMapping("/pojo/{id}")
//    public String[] deletePojo(@PathVariable long id) {
//        if (this.map.remove(id) == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such id " + id);
//        } else {
//            return new String[] {"item removed"};
//        }
//    }
    @DeleteMapping("/pojo/{id}")
    public String[] deletePojo(@PathVariable long id) {
        if (this.pojoRepository.existsById(id)) {
            this.pojoRepository.deleteById(id);
            return new String[] {"item removed"};
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such id " + id);
        }
    }

    @GetMapping("/postlogs")
    public Collection<PostLog> getAllPostLogs() {
        return this.postLogRepository.findAll();
    }
}