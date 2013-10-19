#!/bin/sh
# publish.sh
RED="\033[1;31m"
RES="\033[0m"

# Find the old branch to go back later
OLD_BRANCH=`git branch --no-color | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'`

# Testing if there is no unstaged/staged changes to be commited.
# As we use 'git stash' above, it's better to avoid problems...
if [ -n "$(git diff --exit-code)" ]; then
    echo "${RED}Local unstaged changes! Check git status for more info.${RES}"
    exit
elif [ -n "$(git diff --cached --exit-code)" ]; then
    echo "${RED}Local staged changes! Check git status for more info.${RES}"
    exit
elif [ ! -d "doc/javadoc/" ]; then
    echo "${RED}Create javadoc first!${RES}" 
    exit
fi

# Adding and stashing javadoc
cp -pr doc/javadoc javadoc/ \
&& git add javadoc          \
&& git stash || exit $?

# Switching to gh-pages
git checkout gh-pages || exit $?

# Remove old and set up new javadoc
git rm -rf javadoc \
&& git stash pop   \
&& git commit -am "Regenerating javadoc" || exit $?

# Pushing to remote server
git push origin gh-pages || exit $?

# Switch back to the old branch
git checkout $OLD_BRANCH || exit $?

# Based on http://dephraser.net/blog/2012/04/16/ant-javadox-and-github/
