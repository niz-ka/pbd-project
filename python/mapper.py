#!/usr/bin/env python3

import sys

DATE = 0
ZIP_CODE = 2
FIRST_STREET = 6
SECOND_STREET = 7
THIRD_STREET = 8
PEDESTRIANS_INJURED = 11
PEDESTRIANS_KILLED = 12
CYCLIST_INJURED = 13
CYCLIST_KILLED = 14
MOTORIST_INJURED = 15
MOTORIST_KILLED = 16


def get_victim_type(index):
    return {
        PEDESTRIANS_INJURED: "PEDESTRIAN",
        PEDESTRIANS_KILLED: "PEDESTRIAN",
        CYCLIST_INJURED: "CYCLIST",
        CYCLIST_KILLED: "CYCLIST",
        MOTORIST_INJURED: "MOTORIST",
        MOTORIST_KILLED: "MOTORIST",
    }[index]


def get_injuries_type(index):
    return {
        PEDESTRIANS_INJURED: "INJURED",
        CYCLIST_INJURED: "INJURED",
        MOTORIST_INJURED: "INJURED",
        PEDESTRIANS_KILLED: "KILLED",
        MOTORIST_KILLED: "KILLED",
        CYCLIST_KILLED: "KILLED",
    }[index]


def map_row(row):
    date = row[DATE].split("/")
    if len(date) != 3:
        return

    year = int(date[2])
    zip_code = row[ZIP_CODE].strip()

    if year <= 2012 or zip_code == "":
        return

    for street_index in range(FIRST_STREET, THIRD_STREET + 1):
        street = row[street_index]
        if street == "":
            continue

        for number_index in range(PEDESTRIANS_INJURED, MOTORIST_KILLED + 1):
            victim_type = get_victim_type(number_index)
            injuries_type = get_injuries_type(number_index)
            number = int(row[number_index])
            print(f"{street}|{zip_code}|{victim_type}|{injuries_type}\t{number}")


if __name__ == "__main__":
    sys.stdin.readline()
    for line in sys.stdin:
        tokens = line.strip().split(",")
        if len(tokens) != 28:
            continue
        map_row(tokens)
