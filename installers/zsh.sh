#!/bin/sh

mkdir -p ~/icaro/cli

if [ "$(find ~/icaro/cli | wc -l)" -eq 0 ]
then
   # get latest tag
   curl https://api.github.com/repos/$org/$repo/releases/latest -s | jq .name -r
   wget https://github.com/icarolang/lang/releases/latest/download/cli.jar
   echo "alias icaro="java -jar ~/icaro/cli.jar"" >> .zshrc
fi

mkdir -p icaro/lang && cd icaro/lang || exit