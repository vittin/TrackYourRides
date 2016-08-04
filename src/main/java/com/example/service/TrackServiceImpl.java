package com.example.service;

import com.example.model.Track;
import com.example.model.TrackRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Service
public class TrackServiceImpl implements TrackService {

    private final AuthService authService;
    private final TrackRepository trackRepo;

    @Autowired
    TrackServiceImpl(AuthService authService, TrackRepository trackRepo){
        this.authService = authService;
        this.trackRepo = trackRepo;
    }

    @Override
    public List<Track> getAllTracks(Long id) {
        return null;
    }

    @Override
    public Long addTrack(Track track) {
        Track saved = trackRepo.save(track);
        return saved.getTrackId();
    }

    @Override
    public void deleteTrack(Long trackId) {
        trackRepo.delete(trackId);
    }

    @Override
    public Track updateTrack(Long trackId, Track givenTrack) {

        trackRepo.getOne(trackId);
        copyNonNullProperties(givenTrack, trackId);

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
