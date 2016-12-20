#!/bin/bash

# Build the code
./build.sh

# Clean up old report files
rm -rf ./output/*

# Loop through configuration files and run tests
FILES=./config/*
for f in $FILES
do
  echo "Processing $f"

  # Adopted from http://stackoverflow.com/questions/965053/extract-filename-and-extension-in-bash
  filename=$(basename "$f")
  filename="${filename%.*}"

  java EvComp_TSP.Driver $f 1 1
  # >> ./output/${filename##*/}.csv;
done
