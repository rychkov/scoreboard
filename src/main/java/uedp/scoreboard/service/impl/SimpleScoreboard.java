package uedp.scoreboard.service.impl;

import uedp.scoreboard.comparator.TotalScoreDescStartTimeDescComparator;
import uedp.scoreboard.exceptions.ValidationException;
import uedp.scoreboard.model.Game;
import uedp.scoreboard.model.Score;
import uedp.scoreboard.model.Team;
import uedp.scoreboard.service.Scoreboard;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleScoreboard implements Scoreboard {
    private static final TotalScoreDescStartTimeDescComparator COMPARATOR = new TotalScoreDescStartTimeDescComparator();
    private final Set<Team> teams = new HashSet<>();
    private final Set<Game> games = new HashSet<>();

    @Override
    public Game startGame(Team homeTeam, Team awayTeam) {
        validateTeams(homeTeam, awayTeam);
        var game = new Game(homeTeam, awayTeam);
        markTeamPlaying(homeTeam);
        markTeamPlaying(awayTeam);
        addGame(game);
        return game;
    }

    private void addGame(Game game) {
        games.add(game);
    }

    private void markTeamPlaying(Team team) {
        teams.add(team);
    }

    private void validateTeams(Team homeTeam, Team awayTeam) {
        isTeamPlaying(homeTeam);
        isTeamPlaying(awayTeam);
    }

    private void isTeamPlaying(Team team) {
        if (teams.contains(team)) {
            throw new ValidationException("Team [" + team.getName() + "] already playing game");
        }
    }

    @Override
    public void updateScore(Game game, int homeTeamScore, int awayTeamScore) {
        if (games.contains(game)) {
            games.remove(game);
            games.add(Game.from(game, new Score(homeTeamScore, awayTeamScore)));
        } else {
            throw new ValidationException("Unable to update game score. No active game.");
        }
    }

    @Override
    public void finishGame(Game game) {
        if (games.remove(game)) {
            teams.remove(game.getHomeTeam());
            teams.remove(game.getAwayTeam());
        }
    }

    @Override
    public List<String> getSummary() {
        var sortedList = games.stream().sorted(COMPARATOR).collect(Collectors.toList());
        var pos = 1;
        var result = new LinkedList<String>();
        for (Game game : sortedList) {
            result.add(getSummaryLine(game, pos));
            pos++;
        }
        return result;
    }

    private String getSummaryLine(Game game, int pos) {
        var score = game.getScore();
        return pos + ". " +
            game.getHomeTeam().getName() + ' ' + score.getHome() +
            " - " +
            game.getAwayTeam().getName() + ' ' + score.getAway();
    }
}
