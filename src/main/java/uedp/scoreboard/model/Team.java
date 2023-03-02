package uedp.scoreboard.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import uedp.scoreboard.exceptions.ValidationException;

import java.util.Objects;

@Getter
public class Team {
    private final String name;

    public Team(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null) {
            throw new NullPointerException("Team name can't be null");
        }
        if (StringUtils.isBlank(name)) {
            throw new ValidationException("Team name can't be blank or empty");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return name.equals(team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
