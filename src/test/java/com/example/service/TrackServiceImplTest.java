package com.example.service;

import com.example.model.Track;
import com.example.repository.TrackRepository;
import com.example.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * Created by Mateusz on 2016-08-04.
 */

public class TrackServiceImplTest {

    private TrackRepository trackRepo;
    private UserRepository userRepository;
    private TrackService service;

    @Before
    public void setUp(){
        this.trackRepo = mock(TrackRepository.class);
        this.userRepository = mock(UserRepository.class);
        this.service = new TrackServiceImpl(userRepository, trackRepo);
    }


    @Test
    public void shouldUpdateTrack(){

        when(trackRepo.getOne(1L)).thenReturn(new Track(1,1,1,1));

        Track newTrack = new Track(1,2,2,1);

        when(trackRepo.save(any(Track.class))).thenAnswer(arguments -> arguments.getArguments()[0]);

        Track updated = service.updateTrack(1L, newTrack);

        verify(trackRepo).getOne(1L);

        assertThat(updated, is(newTrack));

        assertThat(updated.getAverageSpeed(), is(2));

    }



}