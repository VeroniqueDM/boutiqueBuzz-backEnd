//package com.api.boutiquebuzz.web;
//
//import com.api.boutiquebuzz.utils.DataInitializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/init-data")
//public class DataInitializationController {
//    private final DataInitializer dataInitializer;
//
//    @Autowired
//    public DataInitializationController(DataInitializer dataInitializer) {
//        this.dataInitializer = dataInitializer;
//    }
//
//    @PostMapping
//    public ResponseEntity<String> initializeData() {
//        dataInitializer.initializeData();
//        return ResponseEntity.ok("Data initialization completed.");
//    }
//}