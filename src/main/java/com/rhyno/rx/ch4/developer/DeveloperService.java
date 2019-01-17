package com.rhyno.rx.ch4.developer;

import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {
    private DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<Developer> getAll() {
        return developerRepository.findAll();
    }

    public List<Developer> getAll2() {
        return Observable.fromArray(developerRepository.findAll())
                .blockingSingle();
    }

    public List<Developer> getDevelopersByPreferredTech(PreferredTech preferredTech) {
        return developerRepository.findByPreferredTech(preferredTech);
    }

    public List<Developer> getDevelopersByPreferredTech2(PreferredTech preferredTech) {
        return Observable.fromIterable(developerRepository.findByPreferredTech(preferredTech))
                .toList()
                .blockingGet();

//        //anti pattern
//        return Observable.fromIterable(developerRepository.findAll())
//                .filter(dev -> dev.getPreferredTech().equals(preferredTech))
//                .toList()
//                .blockingGet();
    }

    public Observable<Developer> getDevelopersByPreferredTech3(PreferredTech preferredTech) {
        return Observable.fromIterable(developerRepository.findByPreferredTech(preferredTech));
    }
}
