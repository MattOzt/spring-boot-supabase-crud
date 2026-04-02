package com.example.adp;

import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface PojoRepository extends ListCrudRepository<MyPOJO, Long> {
    List<MyPOJO> findByLastNameIgnoreCase(String lastname);
}