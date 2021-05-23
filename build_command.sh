#!/bin/bash

# Firebase service account decrypt
- openssl aes-256-cbc -K $encrypted_XXXXXXXXXXXX_key -iv $encrypted_XXXXXXXXXXXX_iv
  -in social-media-clone-314615-cf7c72d3468d.json.enc -out social-media-clone-314615-cf7c72d3468d.json -d
#  Install Google Cloud SDK
wget --quiet --output-document=/tmp/google-cloud-sdk.tar.gz https://dl.google.com/dl/cloudsdk/channels/rapid/google-cloud-sdk.tar.gz  
mkdir -p /opt  
tar zxf /tmp/google-cloud-sdk.tar.gz --directory /opt  
/opt/google-cloud-sdk/install.sh --quiet
source /opt/google-cloud-sdk/path.bash.inc

# Setup and configure the project
gcloud components update
echo FIREBASE_PROJECT_ID
gcloud config set project FIREBASE_PROJECT_ID

# Activate cloud credentials
gcloud auth activate-service-account --key-file social-media-clone-314615-cf7c72d3468d.json

# List available options for logging purpose only (so that we can review available options)
gcloud firebase test android models list
gcloud firebase test android versions list
# Build apk and test apk
./gradlew build assembleDebug
./gradlew testDebugUnitTest
./gradlew :app:assembleDebugAndroidTest
# Run instrumented test cases in firebase test lab
# Command for running Robo Test 
gcloud firebase test android run --app app/build/outputs/apk/debug/app-debug.apk --type=robo --device model=Pixel2,version=28
# Command for running Instrumented Test. This is the command to run the test on the physical device on the test lab
gcloud firebase test android run \
    --type instrumentation \
    --app app/build/outputs/apk/debug/app-debug.apk \
    --test app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
    --device model=Pixel2,version=28,locale=en,orientation=portrait  \
    --device model=NexusLowRes,version=24,locale=en,orientation=portrait
# Command for running Instrumented Test. This is the command to run the test on the virtual device on the test lab. You just need to omit the device model section in it.
gcloud firebase test android run \
    --type instrumentation \
    --app app/build/outputs/apk/debug/app-debug.apk \
    --test app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
