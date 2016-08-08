package com.example.service;

import com.example.model.Track;
import com.example.model.User;
import com.example.repository.TrackRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepo;
    private final UserRepository userRepo;
    private User user;

    @Autowired
    TrackServiceImpl(UserRepository userRepo, TrackRepository trackRepo){
        this.userRepo = userRepo;
        this.trackRepo = trackRepo;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Map<Long, Track> getAllTracks() {
        return user.getTracks();
    }

    @Override
    public Long addTrack(Track track) {

        user.addTrack(track);
        userRepo.save(user);

        return trackRepo.save(track).getTrackId();
    }

    @Override
    public void deleteTrack(Long trackId) {

        Track track = trackRepo.findOne(trackId);

        user.removeTrack(track);
        userRepo.save(user);
        trackRepo.delete(trackId);
    }

    @Override
    public Track updateTrack(Long trackId, Track givenTrack) {

        Track track = trackRepo.getOne(trackId);
        copyNonNullProperties(givenTrack, track);

        user.addTrack(track);
        userRepo.save(user);
        trackRepo.save(track);

        return trackRepo.save(givenTrack);

    }

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private  String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
