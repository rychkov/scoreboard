package uedp.scoreboard.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uedp.scoreboard.exceptions.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    public static final String TEAM_NAME = "Team";
    public static final String ANOTHER_TEAM_NAME = "Another Team";

    @Test
    @DisplayName("Game from null game not allowed")
    public void game_from_null_game_not_allowed() {
        assertThrows(NullPointerException.class, () -> Game.from(null, new Score()));
    }

    @Test
    @DisplayName("Game without score not allowed")
    public void game_without_score_not_allowed() {
        var game = new Game(new Team(TEAM_NAME), new Team(ANOTHER_TEAM_NAME));

        assertThrows(NullPointerException.class, () -> Game.from(game, null));
    }

    @Test
    @DisplayName("Game without teams not allowed")
    public void game_without_teams_not_allowed() {
        assertThrows(NullPointerException.class, () -> new Game(null, null));
    }

    @Test
    @DisplayName("Single team home game not allowed")
    public void single_team_home_game_not_allowed() {
        var team = new Team(TEAM_NAME);

        assertThrows(NullPointerException.class, () -> new Game(team, null));
    }

    @Test
    @DisplayName("Single team away game not allowed")
    public void single_team_away_game_not_allowed() {
        var team = new Team(TEAM_NAME);

        assertThrows(NullPointerException.class, () -> new Game(null, team));
    }

    @Test
    @DisplayName("Self playing game not allowed")
    public void self_playing_game_not_allowed() {
        var team = new Team(TEAM_NAME);

        assertThrows(ValidationException.class, () -> new Game(team, team));
    }

    @Test
    @DisplayName("Home game is different from away game")
    public void home_game_is_different_from_away_game() {
        var team = new Team(TEAM_NAME);
        var anotherTeam = new Team(ANOTHER_TEAM_NAME);

        var homeGame = new Game(team, anotherTeam);
        var awayGame = new Game(anotherTeam, team);

        assertNotEquals(homeGame, awayGame);
    }
}