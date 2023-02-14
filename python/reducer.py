#!/usr/bin/env python3

import sys

current_key = None
current_value = 0


def reduce_row(key, value):
    global current_key, current_value

    if current_key == key:
        current_value += value
    else:
        print(f"\t{current_key}|{current_value}")

        current_key = key
        current_value = value


if __name__ == "__main__":
    line = sys.stdin.readline()
    tokens = line.strip().split("\t")
    current_key = tokens[0]
    current_value = int(tokens[1])

    for line in sys.stdin:
        tokens = line.strip().split("\t")
        reduce_row(tokens[0], int(tokens[1]))

    print(f"\t{current_key}|{current_value}")
