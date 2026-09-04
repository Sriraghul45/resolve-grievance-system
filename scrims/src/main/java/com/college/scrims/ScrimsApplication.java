package com.college.scrims;

import com.college.scrims.model.CR;
import com.college.scrims.repository.CRRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScrimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrimsApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(CRRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                
                // 1. Sri Raghul K
                CR cr1 = new CR();
                cr1.setName("Sri Raghul K");
                cr1.setStudyYear(3);
                cr1.setDepartment("CSE");
                cr1.setSection("D");
                cr1.setPassword("123");
                repo.save(cr1);

                // 2. Shameer A
                CR cr2 = new CR();
                cr2.setName("Shameer A");
                cr2.setStudyYear(3);
                cr2.setDepartment("CSE");
                cr2.setSection("C");
                cr2.setPassword("124");
                repo.save(cr2);

                // 3. Vedharasan T
                CR cr3 = new CR();
                cr3.setName("Vedharasan T");
                cr3.setStudyYear(3);
                cr3.setDepartment("CSE");
                cr3.setSection("B");
                cr3.setPassword("134");
                repo.save(cr3);

                // 4. Shankaran R
                CR cr4 = new CR();
                cr4.setName("Shankaran R");
                cr4.setStudyYear(3);
                cr4.setDepartment("CSE");
                cr4.setSection("A");
                cr4.setPassword("145");
                repo.save(cr4);
            }
        };
    }
}