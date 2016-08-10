package com.example.service;

import com.example.model.Track;
import com.example.model.User;
import com.example.repository.TrackRepository;
import com.example.repository.UserRepository;
import com.sun.istack.internal.Nullable;
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
    @Nullable
    public Track getTrack(long trackId){
        return user.getTracks().get(trackId);
    }

    @Override
    public Long addTrack(Track track) {

        long id = user.addTrack(track);
        userRepo.save(user);

        return id;
    }

    @Override
    public void deleteTrack(Long trackId) {

        Track track = user.getTracks().get(trackId);

        if (track != null){
            user.removeTrack(track);
            userRepo.save(user);
        }

    }

    @Override
    public long updateTrack(Long trackId, Track givenTrack) {

        Track track = user.getTracks().get(trackId);
        if (track != null){
            copyNonNullProperties(givenTrack, track);
            user.addTrack(track);
        } else {
            givenTrack.setTrackId(trackId);
            user.addTrack(givenTrack);
        }

        userRepo.save(user);

        return trackId;

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
