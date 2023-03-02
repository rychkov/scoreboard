package uedp.scoreboard.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import uedp.scoreboard.exceptions.ValidationException;

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
}
