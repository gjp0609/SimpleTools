package me.rainbow.service.impl;

import me.rainbow.dao.MusicDao;
import me.rainbow.entity.Music;
import me.rainbow.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author guojinpeng
 * @date 17.12.29 15:53
 */
@Transactional
@Service("musicService")
public class MusicServiceImpl implements MusicService {
    private final MusicDao dao;

    @Autowired
    public MusicServiceImpl(MusicDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Music> getAllMusic() {
        return dao.selectAll();
    }

    @Override
    public void saveMusic(Music music) {
        dao.save(music);
    }

    @Override
    public Music getMusic(String id) {
        return dao.selectById(id);
    }
}
