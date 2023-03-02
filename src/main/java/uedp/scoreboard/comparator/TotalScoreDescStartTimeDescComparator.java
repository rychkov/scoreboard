package uedp.scoreboard.comparator;

import uedp.scoreboard.model.Game;

import java.util.Comparator;

public class TotalScoreDescStartTimeDescComparator implements Comparator<Game> {
    @Override
    public int compare(Game o1, Game o2) {
        if (o1 == o2) {
            return 0;
        }

        var total1 = o1.getScore().total();
        var total2 = o2.getScore().total();

        if (total1 == total2) {
            return -(o1.getStartTime().compareTo(o2.getStartTime()));
        } else if (total1 < total2) {
            return 1;
        }
        return -1;
    }
}
