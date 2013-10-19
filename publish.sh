#!/bin/sh
# publish.sh

# Find the old branch to go back later
OLD_BRANCH=`git branch --no-color | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'`

git checkout gh-pages || exit $?

# Remove old and set up new javadoc
mv -f doc/javadoc javadoc/ \
&& git add javadoc \
&& git commit -am "Regenerating javadoc"

git push origin gh-pages || exit $?

# Switch back to the old branch
git checkout $OLD_BRANCH || exit $?

# Based on http://dephraser.net/blog/2012/04/16/ant-javadox-and-github/
