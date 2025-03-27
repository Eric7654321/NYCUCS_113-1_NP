package project3.service;

import project3.pojo.Feedback;
import project3.pojo.Game;

import java.util.List;

public interface GameService {
    void save(Game game);

    List<Game> list();

    Game find(String name);

    void addFeedback(Feedback feedback);

    List<Feedback> getAllFeedback();
}
