#! /bin/bash
#
# This file is part of Audioboo, an android program for audio blogging.
# Copyright (C) 2011 Audioboo Ltd. All rights reserved.
#
# Author: Jens Finkhaeuser <jens@finkhaeuser.de>
#
# $Id: bootstrap.sh 1272 2010-11-01 14:07:29Z unwesen $

# 1. Make sure we're in the project root. We'll look for AndroidManifest.xml,
#    and check whether that includes a reference to Audioboo.
PROJECT_PATH="/Users/Gyuhyeon/Project/VoiceArchieving"
MANIFEST="${PROJECT_PATH}/AndroidManifest.xml"
if [ ! -f "${MANIFEST}" ] ; then
  echo "No AndroidManifest.xml file found!" >&2
  echo "You need to run this script in the project root." >&2
  exit 1
fi

# 3. Build native stuff. Force rebuilding; if you don't want to rebuild, run
#    ndk/ndk-build manually without the -B parameter:
#    $ ndk/ndk-build V=1
ndk-build -B "$@"

# 4. Build external libs. The artefacts end up in the libs subdirectory.
sudo sh ./build-externals.sh "$@"