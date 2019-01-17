package com.rhyno.rx.ch4.developer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Integer> {
    List<Developer> findByPreferredTech(PreferredTech preferredTech);

    @Override
    List<Developer> findAll();
}
