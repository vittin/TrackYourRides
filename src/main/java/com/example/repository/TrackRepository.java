package com.example.repository;

import com.example.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface TrackRepository extends JpaRepository<Track, Long> {
}
