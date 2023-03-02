package uedp.scoreboard.model;

import lombok.Getter;
import uedp.scoreboard.exceptions.ValidationException;

import java.time.Instant;
import java.util.Objects;

@Getter
public class Game {
    private final Instant startTime;
    private final Team homeTeam;
    private final Team awayTeam;
    private final Score score;

    private Game(Instant start, Team homeTeam, Team awayTeam, Score score) {
        validateTeams(homeTeam, awayTeam);
        validateScore(score);
        validateStartTime(start);
        startTime = start;
        this.score = score;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Game(Team homeTeam, Team awayTeam) {
        this(Instant.now(), homeTeam, awayTeam, new Score());
    }

    public static Game from(Game game, Score score) {
        if (game == null) {
            throw new NullPointerException("Game can't be null");
        }
        return new Game(game.startTime, game.homeTeam, game.awayTeam, score);
    }

    private void validateTeams(Team homeTeam, Team awayTeam) {
        if (homeTeam == null) {
            throw new NullPointerException("Home team can't be null");
        }
        if (awayTeam == null) {
            throw new NullPointerException("Away team can't be null");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new ValidationException("Single team game not allowed");
        }
    }

    private void validateScore(Score score) {
        if (score == null) {
            throw new NullPointerException("Score can't be null");
        }
    }

    private void validateStartTime(Instant startTime) {
        if (startTime == null) {
            throw new NullPointerException("Start time can't be null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return startTime.equals(game.startTime) &&
                homeTeam.equals(game.homeTeam) &&
                awayTeam.equals(game.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, homeTeam, awayTeam);
    }
}
