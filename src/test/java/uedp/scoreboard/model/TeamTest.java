package uedp.scoreboard.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import uedp.scoreboard.exceptions.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    @DisplayName("Null name for the team not allowed")
    public void null_name_not_allowed() {
        assertThrows(NullPointerException.class, () -> new Team(null));
    }

    @EmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @ParameterizedTest(name = "Input [{0}]")
    @DisplayName("Blank or empty name not allowed")
    public void blank_or_empty_name_not_allowed(String name) {
        assertThrows(ValidationException.class, () -> new Team(name));
    }

    @ValueSource(strings = {"Team1", "Team 2"})
    @ParameterizedTest(name = "Input [{0}]")
    @DisplayName("Allow to create the team")
    public void create_team(String name) {
        var team = new Team(name);

        assertEquals(name, team.getName());
    }
}
