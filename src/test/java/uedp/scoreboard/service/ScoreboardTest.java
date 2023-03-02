package uedp.scoreboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uedp.scoreboard.exceptions.ValidationException;
import uedp.scoreboard.model.Game;
import uedp.scoreboard.model.Score;
import uedp.scoreboard.model.Team;
import uedp.scoreboard.service.impl.SimpleScoreboard;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {

    public static final String TEAM_NAME = "Team";
    public static final String ANOTHER_TEAM_NAME = "Another Team";

    private final Scoreboard scoreboard = new SimpleScoreboard();


    @Test
    @DisplayName("Allow start game for two teams with zero score")
    void start_game() {
        var homeTeam = new Team(TEAM_NAME);
        var awayTeam = new Team(ANOTHER_TEAM_NAME);

        var game = scoreboard.startGame(homeTeam, awayTeam);

        assertNotNull(game);
        assertEquals(new Score(), game.getScore());
    }

    @ParameterizedTest(name = "Input [{0}] - [{1}]")
    @DisplayName("Game start not allowed")
    @MethodSource("game_start_not_allowed_params")
    public void game_start_not_allowed_params(Team home, Team away) {
        assertThrows(RuntimeException.class, () -> scoreboard.startGame(home, away));
    }
    private static Stream<Arguments> game_start_not_allowed_params() {
        var team = new Team(TEAM_NAME);

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(team, null),
                Arguments.of(null, team),
                Arguments.of(team, team)
        );
    }

    @Test
    @DisplayName("One team can't play more than one game")
    public void one_team_cant_play_more_than_ome_game() {
        var team = new Team(TEAM_NAME);
        var awayTeam = new Team(ANOTHER_TEAM_NAME);
        var oneMoreTeam = new Team("one more");
        scoreboard.startGame(team, awayTeam);

        assertThrows(ValidationException.class, () -> scoreboard.startGame(oneMoreTeam, team));
    }

    @Test
    @DisplayName("Update score for the game")
    public void update_score() {
        addGameAndUpdateScoreAndReturnGame("Mexico", 0, "Canada", 5);

        assertEquals(List.of("1. Mexico 0 - Canada 5"), scoreboard.getSummary());
    }

    @Test
    @DisplayName("Update score for non existing game not allowed")
    public void update_score_for_non_existing_game_not_allowed() {
        var game = new Game(new Team(TEAM_NAME), new Team(ANOTHER_TEAM_NAME));

        assertThrows(ValidationException.class, () -> scoreboard.updateScore(game, 1, 1));
    }

    @Test
    @DisplayName("Finish existing game")
    void finish_game() {
        var game = addGameAndUpdateScoreAndReturnGame("Mexico", 0, "Canada", 5);

        scoreboard.finishGame(game);
        assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    @DisplayName("Finish not existing game")
    void finish_not_existing_game() {
        var game = new Game(new Team(TEAM_NAME), new Team(ANOTHER_TEAM_NAME));

        scoreboard.finishGame(game);
        assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    @DisplayName("Summary test")
    void list() {
        addGameAndUpdateScoreAndReturnGame("Mexico", 0, "Canada", 5);
        addGameAndUpdateScoreAndReturnGame("Spain", 10, "Brazil", 2);
        addGameAndUpdateScoreAndReturnGame("Germany", 2, "France", 2);
        addGameAndUpdateScoreAndReturnGame("Uruguay",6, "Italy", 6);
        addGameAndUpdateScoreAndReturnGame("Argentina", 3,"Australia", 1);

        var expected = List.of(
            "1. Uruguay 6 - Italy 6",
            "2. Spain 10 - Brazil 2",
            "3. Mexico 0 - Canada 5",
            "4. Argentina 3 - Australia 1",
            "5. Germany 2 - France 2"
        );

        assertEquals(expected, scoreboard.getSummary());
    }

    private Game addGameAndUpdateScoreAndReturnGame(String homeTeamName, int homeScore, String awayTeamName, int awayScore) {
        var homeTeam = new Team(homeTeamName);
        var awayTeam = new Team(awayTeamName);
        var game = scoreboard.startGame(homeTeam, awayTeam);
        scoreboard.updateScore(game, homeScore, awayScore);
        return game;
    }
}