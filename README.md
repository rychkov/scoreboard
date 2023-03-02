Live Football World Cup Scoreboard
==================================

Assumptions
-----------
Java version >= 11
Maven

Score
-----
* Score must be non negative.
* We do not check the score of the game for not decreasing. 
Valid situation
```
0 - 0
1 - 0
1 - 5
3 - 0 
```
The correctness of the game score is controlled by the source of the game score.


Team
----

* Team may be listed on the scoreboard only once because only one active game for one team allowed.
* We don't allow teams with null or blank names.
* We don't modify the team name if it's not blank.

TODO
----
* Test refactoring
* Add caching
* Add thread safety
* Improve performance
* TBD