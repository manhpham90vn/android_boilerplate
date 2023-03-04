#!/usr/bin/env bash
set -e

if ! bundle --version &> /dev/null
then
    export BUNDLER_VERSION=$(cat Gemfile.lock | tail -1 | tr -d " ")
    sudo gem install bundler:$BUNDLER_VERSION
fi

bundle install

echo "Done"