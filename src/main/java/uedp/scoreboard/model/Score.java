package uedp.scoreboard.model;

import lombok.Getter;
import uedp.scoreboard.exceptions.ValidationException;

@Getter
public class Score {
    private final int home;
    private final int away;

    public Score() {
        home = 0;
        away = 0;
    }


    /**
     * Create score object.
     *
     * @param homeScore home score can't be negative
     * @param awayScore away score can't be negative
     */
    public Score(int homeScore, int awayScore) {
        validateScore(homeScore, awayScore);
        home = homeScore;
        away = awayScore;
    }

    /**
     * Total score is sum of home and awa score.
     *
     * @return the total score
     */
    public int total() {
        return home + away;
    }

    private void validateScore(int homeScore, int awayScore) {
        if (homeScore < 0) {
            throw new ValidationException("Home score can't be negative");
        }
        if (awayScore < 0) {
            throw new ValidationException("Away score can't be negative");
        }
    }
}
