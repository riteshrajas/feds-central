# REBUILT - 2026 FRC Game

REBUILT presented by Haas is the 2026 FIRST Robotics Competition game. Two competing alliances score fuel, cross obstacles, and climb the tower before time runs out.

## Match Structure

A match is **2 minutes and 40 seconds** (160 seconds) total, split into distinct periods:

| Period | Duration | Description |
|--------|----------|-------------|
| **AUTON** | 20 seconds | Robots operate autonomously without driver input |
| **TELEOP** | 2:20 (140 seconds) | Drivers control robots remotely |

### TELEOP Breakdown

TELEOP is divided into segments that affect HUB scoring:

| Segment | Duration | Timer Values | HUB Status |
|---------|----------|--------------|------------|
| TRANSITION SHIFT | 10 seconds | 2:20 - 2:10 | Both active |
| SHIFT 1 | 25 seconds | 2:10 - 1:45 | Alternating* |
| SHIFT 2 | 25 seconds | 1:45 - 1:20 | Alternating* |
| SHIFT 3 | 25 seconds | 1:20 - 0:55 | Alternating* |
| SHIFT 4 | 25 seconds | 0:55 - 0:30 | Alternating* |
| END GAME | 30 seconds | 0:30 - 0:00 | Both active |

*The alliance that scores more FUEL during AUTON has their HUB go inactive first during SHIFT 1. HUB statuses alternate each shift.

---

## The Field

The REBUILT field is approximately **317.7in x 651.2in** (~8.07m x 16.54m).

### Field Elements

**HUB** - One per alliance, centered between two BUMPs. A 47in x 47in rectangular structure with a hexagonal opening on top where robots deliver FUEL. FUEL scored in an active HUB counts for points; FUEL in an inactive HUB scores nothing. FUEL exits the HUB randomly into the NEUTRAL ZONE.

**BUMP** - Four total (two per side). 73.0in wide, 44.4in deep, 6.5in tall ramps that robots drive over. Located on either side of each HUB.

**TRENCH** - Four total. 65.65in wide, 47.0in deep, 40.25in tall structures that robots drive underneath. The clearance under a TRENCH arm is 22.25in tall and 50.34in wide.

**DEPOT** - One per alliance along the ALLIANCE WALL. A 42.0in x 27.0in area with low steel barriers where FUEL is staged at match start.

**TOWER** - One per alliance, integrated into the ALLIANCE WALL. 49.25in wide, 45.0in deep, 78.25in tall climbing structure with rungs at three levels.

**NEUTRAL ZONE** - The center area of the field containing FUEL and bordered by BUMPs, TRENCHES, HUBs, and guardrails.

**OUTPOST** - Located at each end of the field. Contains a chute where HUMAN PLAYERS can feed FUEL onto the field.

---

## Game Pieces

**FUEL** - 5.91in (15.0cm) diameter high-density foam balls.

### FUEL Staging (504 total per match)
- 24 FUEL in each DEPOT (48 total)
- 24 FUEL in each OUTPOST chute (48 total)
- Up to 8 FUEL preloaded per robot (up to 48 total)
- Remaining ~360 FUEL arranged in the NEUTRAL ZONE

---

## Scoring

### Match Points

| Action | AUTON | TELEOP |
|--------|------|--------|
| FUEL scored in active HUB | 1 point | 1 point |
| FUEL scored in inactive HUB | 0 points | 0 points |
| Each robot at TOWER Level 1 | 15 points (max 2 robots) | 10 points |
| Each robot at TOWER Level 2 | - | 20 points |
| Each robot at TOWER Level 3 | - | 30 points |

### Tower Climbing Requirements

- **Level 1**: Robot no longer touching CARPET or TOWER BASE
- **Level 2**: Robot's BUMPER covers completely above the Level 2 RUNG
- **Level 3**: Robot's BUMPER covers completely above the Level 3 RUNG

A robot can only earn TOWER points for Level 1 during AUTON. During TELEOP, a robot can only earn points for a single level (its highest achieved).

### Ranking Points (Qualification Matches)

| Ranking Point | Requirement |
|--------------|-------------|
| Win | 3 RP |
| Tie | 1 RP |
| **ENERGIZED RP** | Score at least 100 FUEL in active HUB | 1 RP |
| **SUPERCHARGED RP** | Score at least 360 FUEL in active HUB | 1 RP |
| **TRAVERSAL RP** | Earn at least 50 TOWER points | 1 RP |

### Penalties

| Penalty | Points to Opponent |
|---------|-------------------|
| MINOR FOUL | 5 points |
| MAJOR FOUL | 15 points |

---

## Key Strategic Considerations

1. **AUTON Performance Matters**: The alliance that scores more FUEL in AUTON gets their HUB set inactive first during SHIFT 1 - meaning opponents score while you can't. However, you then get the next active window.

2. **HUB Cycling**: During ALLIANCE SHIFTS, only one HUB is active at a time. Teams must decide whether to score aggressively during active periods, play defense, or collect FUEL during inactive periods.

3. **END GAME**: Both HUBs are active during the final 30 seconds, creating a scoring frenzy while teams must also consider climbing the TOWER for bonus points.

4. **Ranking Point Thresholds**: 100 FUEL for ENERGIZED, 360 FUEL for SUPERCHARGED, 50 TOWER points for TRAVERSAL. These targets should inform alliance strategy.
