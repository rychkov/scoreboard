package uedp.scoreboard.service;

import uedp.scoreboard.exceptions.ValidationException;
import uedp.scoreboard.model.Game;
import uedp.scoreboard.model.Team;

import java.util.List;

public interface Scoreboard {
    /**
     * Start the game.
     *
     * @param homeTeam home team
     * @param awayTeam away team
     * @return started game
     */
    Game startGame(Team homeTeam, Team awayTeam);

    /**
     * Update the game score.
     *
     * @param game game
     * @param homeTeamScore home team score
     * @param awayTeamScore away team score
     * @throws ValidationException if no active game or score param is invalid
     */
    void updateScore(Game game, int homeTeamScore, int awayTeamScore);

    /**
     * Finish the game and remove it from the scoreboard.
     *
     * @param game the game
     */
    void finishGame(Game game);

    /**
     * Return summary for the scoreboard.
     * Games are sorted by total score in descending order and then by start time in reverse chronological order.
     *
     * @return summary
     */
    List<String> getSummary();
}
