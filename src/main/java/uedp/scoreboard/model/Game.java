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

    public Game(Team homeTeam, Team awayTeam) {
        validateTeams(homeTeam, awayTeam);
        startTime = Instant.now();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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
