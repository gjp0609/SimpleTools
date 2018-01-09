package me.rainbow.dao;

import me.rainbow.entity.Music;

import java.util.List;

/**
 * @author guojinpeng
 * @date 17.12.29 15:54
 */
public interface MusicDao {
    List<Music> selectAll();

    Music selectById(String id);

    void save(Music log);
}
