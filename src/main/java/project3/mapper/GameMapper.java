package project3.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import project3.pojo.Feedback;
import project3.pojo.Game;

import java.util.List;

@Mapper
public interface GameMapper {
    @Select("select * from np_project3.game where file_name = #{fileName}")
    Game checkByFileName(String fileName);

    @Insert("insert into np_project3.game(file_name, file_path, file_type, file_size, name, creator, version, description, rating, played, create_time, update_time)" +
            "values (#{fileName}, #{filePath}, #{fileType}, #{fileSize}, #{name}, #{creator}, #{version}, #{description}, #{rating}, #{played}, #{createTime}, #{updateTime})")
    void add(Game game);

    @Update("update np_project3.game set version = #{version}, file_size = #{fileSize}, update_time = #{updateTime} where name = #{name} and creator = #{creator}")
    void updateInfo(Game game);

    @Select("select * from np_project3.game")
    List<Game> listAll();

    @Select("select * from np_project3.game where name = #{name}")
    Game findByName(String name);

    @Insert("insert into np_project3.feedback(game_mode, writer, rating, feedback, create_time, game_author) values (#{gameMode}, #{writer}, #{rating}, #{feedBack}, #{createTime}, #{gameAuthor})")
    void addFeedback(Feedback feedback);

    @Update("update np_project3.game set rating = #{rating}, played = #{played} where name = #{name}")
    void updateRating(Game game);

    @Select("select * from np_project3.feedback")
    List<Feedback> listAllFeedback();
}
