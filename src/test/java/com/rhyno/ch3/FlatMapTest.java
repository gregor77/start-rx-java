package com.rhyno.ch3;

import com.rhyno.rx.ch3.file.File;
import com.rhyno.rx.ch3.file.FileService;
import com.rhyno.rx.ch3.file.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class FlatMapTest {
    @Mock
    private FileService mockFileService;

    @Test
    public void whenFlatMapMutation_thenHasOnNextAndOnErrorAndOnCompleted() {
        UUID uuid = UUID.randomUUID();
        File file = File.builder().id(uuid).fileService(mockFileService).build();
        file.store(uuid);

        then(mockFileService).should().save(any(UUID.class), any(Rating.class));
    }

}
