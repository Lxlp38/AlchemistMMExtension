# Alchemist MM Extension 

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/5833d4e6d8a849c5ba05c4ad5458a43b)](https://app.codacy.com?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade) [![CodeFactor](https://www.codefactor.io/repository/github/bedwarshurts/alchemistmmextension/badge)](https://www.codefactor.io/repository/github/bedwarshurts/alchemistmmextension)

An extension for MythicMobs that adds some Mechanics, Targeters, Conditions and utilises the faction feature to apply bonus damage to mobs.

## Mechanics

### BookGUIMechanic

```
- bookgui{contents=; author=; title=;}
```

### CancelPlayerDeathMechanic

```
- cancelplayerdeath{healthPercentage=; skill=;}
```

### SphereShapeMechanic

```
- sphereshape{particle=; radius=; particle-count=; dirMultiplier=; shiftRadius=; variance=; direction=; speed=; skill=; delay=;}
```

### OpenChestMechanic

```
- openchest{action=;}
```

## Targeters

### GroundLevelTargeter

```
@TGL{y=;}
```

### LocationPredictingTargeter

```
@TPL{time=; y=; ignoreY=; ignoreIfStill=;}
```

## Conditions

### StringContainsCondition

```
?stringcontains{s=;c=;}
```

### YLevelCondition

```
?isylevel{y=}
```

## Factions

Factions are used to tell when to apply extra damage to the mob. For example if the mob is in the faction "boss" then extra damage will be applied based on a placeholder such as %player_stat_boss_damage_bonus%. A mob can be in multiple factions, if the faction setting is for example boss,fire both bonuses will be applied.

### IsInFactionCondition

Checks if the boss is in a faction taking into account that it could be in multiple seperated by comma
```
?isinfaction{faction=}
```

## Placeholders

A placeholder to get a random color between 2 hex values was added. Requires PlaceholderAPI %alchemist_color_#hex1_hex2%, returns a random color between the hex values 1 and 2
