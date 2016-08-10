package com.example.service;

import com.example.model.Track;
import com.example.model.User;
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
    private User user;

    @Before
    public void setUp(){
        this.trackRepo = mock(TrackRepository.class);
        this.userRepository = mock(UserRepository.class);
        this.service = new TrackServiceImpl(userRepository, trackRepo);
        this.user = mock(User.class);
        service.setUser(user);
    }


    @Test
    public void shouldUpdateTrack(){

        final Track[] updated = new Track[1];

        Track newTrack = new Track(1,2,2,1);
        when(trackRepo.getOne(1L)).thenReturn(new Track(1,1,1,1));
        when(trackRepo.save(any(Track.class))).thenAnswer(arguments -> {
            updated[0] = (Track) arguments.getArguments()[0];
            return updated[0];
        });

        service.updateTrack(1L, newTrack);

        verify(trackRepo).getOne(1L);
        assertThat(newTrack, is(updated[0])); //WHY?
        assertThat(updated[0].getAverageSpeed(), is(2));

    }



}