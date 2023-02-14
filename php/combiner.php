#!/usr/bin/env php
<?php


$line = fgets(STDIN);
$tokens = explode("\t", $line);
$currentKey = $tokens[0];
$currentValue = (int)($tokens[1]);

function combineRow($key, $value) {
    global $currentKey, $currentValue;

    if ($currentKey === $key) {
        $currentValue += $value;
    } else {
        printf("%s\t%s\n", $currentKey, $currentValue);

        $currentKey = $key;
        $currentValue = $value;
    }
}

while(($line = fgets(STDIN)) !== false) {
    $tokens = explode("\t", $line);
    combineRow($tokens[0], (int)($tokens[1]));
}

printf("%s\t%s\n", $currentKey, $currentValue);