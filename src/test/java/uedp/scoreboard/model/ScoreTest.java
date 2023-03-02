package uedp.scoreboard.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uedp.scoreboard.exceptions.ValidationException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @ParameterizedTest(name = "Input [{0}] - [{1}]")
    @DisplayName("Negative score not allowed")
    @MethodSource("negative_score_not_allowed_params")
    public void negative_score_not_allowed(int home, int away) {
        assertThrows(ValidationException.class, () -> new Score(home, away));
    }
    private static Stream<Arguments> negative_score_not_allowed_params() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(0, -1),
                Arguments.of(-1, -1)
        );
    }

    @ParameterizedTest(name = "Input [{0}] - [{1}]")
    @DisplayName("Total score equals test")
    @MethodSource("total_score_equals_params")
    public void total_score_equals(Score src, Score dst) {
        assertEquals(src.total(), dst.total());
    }
    private static Stream<Arguments> total_score_equals_params() {
        return Stream.of(
                Arguments.of(new Score(), new Score()),
                Arguments.of(new Score(1, 0), new Score(0, 1)),
                Arguments.of(new Score(1, 1), new Score(0, 2)),
                Arguments.of(new Score(1, 1), new Score(2, 0))
        );
    }

    @ParameterizedTest(name = "Input [{0}] - [{1}] = [{2}]")
    @DisplayName("Total score test")
    @MethodSource("total_score_params")
    public void total_score(int home, int away, int total) {
        var score = new Score(home, away);

        assertEquals(total, score.total());
    }
    private static Stream<Arguments> total_score_params() {
        return Stream.of(
                Arguments.of(0, 0, 0),
                Arguments.of(0, 1, 1),
                Arguments.of(1, 0, 1),
                Arguments.of(1, 1, 2)
        );
    }
}
