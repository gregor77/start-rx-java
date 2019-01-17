package com.rhyno.rx.ch4.developer;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeveloperServiceTest {

    @Autowired
    private DeveloperService subject;

    @Autowired
    private DeveloperRepository developerRepository;

    @Before
    public void setUp() {
        developerRepository.saveAll(Lists.newArrayList(
                Developer.builder().name("rhyno").preferredTech(PreferredTech.BACK_END)
                        .role(Role.TECH_LEAD)
                        .build(),
                Developer.builder().name("ki").preferredTech(PreferredTech.BACK_END)
                        .role(Role.TECH_LEAD)
                        .build(),
                Developer.builder().name("ethan").preferredTech(PreferredTech.FRONT_END)
                        .role(Role.TECH_LEAD)
                        .build()
        ));
    }

    @Test
    @Transactional
    public void whenGetAll_thenReturnAllDevelopers() {
        assertThat(subject.getAll().size()).isEqualTo(3);
        assertThat(subject.getAll2().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void whenGetDevelopersByPreferredTech_thenReturnDevelopers() {
        List<Developer> backEndPreferredDevs = subject.getDevelopersByPreferredTech(PreferredTech.BACK_END);
        assertThat(backEndPreferredDevs.size()).isEqualTo(2);

        List<Developer> backEndPreferredDevs2 = subject.getDevelopersByPreferredTech2(PreferredTech.BACK_END);
        assertThat(backEndPreferredDevs2.size()).isEqualTo(2);

        ArrayList<Developer> result = subject.getDevelopersByPreferredTech3(PreferredTech.BACK_END)
                .reduce(new ArrayList<Developer>(), (accumulator, dev) -> {
                    accumulator.add(dev);
                    return accumulator;
                })
                .doOnSubscribe(System.out::println)
                .blockingGet();
        assertThat(result.size()).isEqualTo(2);
    }
}
