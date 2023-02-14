#!/usr/bin/env php
<?php

const DATE = 0;
const ZIP_CODE = 2;
const FIRST_STREET = 6;
const SECOND_STREET = 7;
const THIRD_STREET = 8;
const PEDESTRIANS_INJURED = 11;
const PEDESTRIANS_KILLED = 12;
const CYCLIST_INJURED = 13;
const CYCLIST_KILLED = 14;
const MOTORIST_INJURED = 15;
const MOTORIST_KILLED = 16;

function getVictimType($index) {
    switch($index) {
        case PEDESTRIANS_INJURED:
        case PEDESTRIANS_KILLED:
            return "PEDESTRIAN";
        case CYCLIST_INJURED:
        case CYCLIST_KILLED:
            return "CYCLIST";
        case MOTORIST_INJURED:
        case MOTORIST_KILLED:
            return "MOTORIST";
        default:
            echo "[ERROR] VICTIM TYPE UNKNOWN: " . $index;
            exit(111);
    }
}

function getInjuriesType($index) {
    switch ($index) {
        case PEDESTRIANS_INJURED:
        case CYCLIST_INJURED:
        case MOTORIST_INJURED:
            return "INJURED";
        case PEDESTRIANS_KILLED:
        case CYCLIST_KILLED:
        case MOTORIST_KILLED:
            return "KILLED";
        default:
            echo "[ERROR] INJURIES TYPE UNKNOWN: " . $index;
            exit(111);
    }
}

function mapRow($row) {
    $date = explode("/", $row[DATE]);
    if(count($date) != 3) {
        return;
    }

    $year = (int)($date[2]);
    $zipCode = $row[ZIP_CODE];

    if ($year <= 2012 || $zipCode === "") {
        return;
    }

    for ($streetIndex = FIRST_STREET; $streetIndex <= THIRD_STREET; $streetIndex++) {
        $street = $row[$streetIndex];
        if($street === "") {
            continue;
        }
        for ($numberIndex = PEDESTRIANS_INJURED; $numberIndex <= MOTORIST_KILLED; $numberIndex++) {
            $victimType = getVictimType($numberIndex);
            $injuriesType = getInjuriesType($numberIndex);
            $number = $row[$numberIndex];
            printf("%s|%s|%s|%s\t%s\n", $street, $zipCode, $victimType, $injuriesType, $number);
        }
    }
}

fgets(STDIN);
while(($line = fgets(STDIN)) !== false) {
    $tokens = explode(",", $line);
    mapRow($tokens);
}