# Testing and running our git-managed source files in IDE

- Create new Java Application Project in IDE, DO NOT create from existing sources
- I recommend creating from the standard command-line template because it helps you create a MAIN class automagically.
- You can name it whatever you want, but for consistency, please use "EvComp_TSP" for the base package name
- Open a terminal
- Navigate to the base package folder ```../<ProjectFolder>/EvComp_TSP/src/EvComp_TSP```
- Enter the following commands:
```
git init
git remote add origin https://aXXXXXXX@github.cs.adelaide.edu.au/a1600254/EvComp_TSP.git
git fetch
git checkout -t origin/master
```
where aXXXXXXX refers to your student ID. This allows faster authentication by not having to enter your username (just a small convenience).

Now you can use the IDE to pull/push/update/branch/merge/rebase/etc.
Having the IDE connected to git also allows you to see which lines are modified/new/deleted immediately and view between different branches without much hassle.
