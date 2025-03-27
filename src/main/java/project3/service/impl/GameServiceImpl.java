package project3.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.mapper.GameMapper;
import project3.pojo.Feedback;
import project3.pojo.Game;
import project3.service.GameService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameMapper gameMapper;

    @Override
    public void save(Game game) {
        if(gameMapper.checkByFileName(game.getFileName()) == null){
            game.setPlayed(0);
            game.setRating(0);
            game.setCreateTime(LocalDateTime.now());
            game.setUpdateTime(LocalDateTime.now());
            //game.setId();
            gameMapper.add(game);
        }else {
            game.setUpdateTime(LocalDateTime.now());
            gameMapper.updateInfo(game);
        }
    }

    @Override
    public List<Game> list() {
        return gameMapper.listAll();
    }

    @Override
    public Game find(String name) {
        return gameMapper.findByName(name);
    }

    @Override
    public void addFeedback(Feedback feedback) {
        feedback.setCreateTime(LocalDateTime.now());

        Game game = gameMapper.findByName(feedback.getGameMode());
        game.setPlayed(game.getPlayed() + 1);
        Integer people = game.getPlayed();
        game.setRating((game.getRating() * (people - 1) + feedback.getRating()) / people);

        feedback.setGameAuthor(game.getCreator());

        gameMapper.addFeedback(feedback);
        gameMapper.updateRating(game);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return gameMapper.listAllFeedback();
    }
}
