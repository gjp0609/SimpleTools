package me.rainbow.service;

import me.rainbow.entity.Music;

import java.util.List;

/**
 * @author guojinpeng
 * @date 17.12.29 15:51
 */
public interface MusicService {
    List<Music> getAllMusic();

    void saveMusic(Music music);

    Music getMusic(String id);
}
