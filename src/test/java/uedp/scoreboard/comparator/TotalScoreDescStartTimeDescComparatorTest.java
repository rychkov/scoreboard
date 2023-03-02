package uedp.scoreboard.comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uedp.scoreboard.model.Game;
import uedp.scoreboard.model.Score;
import uedp.scoreboard.model.Team;

import static org.junit.jupiter.api.Assertions.*;

class TotalScoreDescStartTimeDescComparatorTest {
    private Team team = new Team("Team");
    private Team anotherTeam = new Team("Another team");

    private static final TotalScoreDescStartTimeDescComparator INSTANCE = new TotalScoreDescStartTimeDescComparator();
    @Test
    @DisplayName("Same total score for the same game")
    void compare_same_total_score() {
        var game = new Game(team, anotherTeam);

        assertEquals(0, INSTANCE.compare(game, game));
    }

    @Test
    @DisplayName("Game with less total score should return positive value when compared with higher total score game")
    void compare_less_score_should_return_positive_value() {
        var game = new Game(team, anotherTeam);
        var score = new Score(1, 1);

        assertTrue(INSTANCE.compare(game, Game.from(game, score)) > 0);
    }

    @Test
    @DisplayName("Game with same total score should return positive value when compared with later game")
    void compare_same_score_should_return_positive_value() {
        var game = new Game(team, anotherTeam);
        var laterGame = new Game(team, anotherTeam);

        assertTrue(INSTANCE.compare(game, laterGame) > 0);
    }
}