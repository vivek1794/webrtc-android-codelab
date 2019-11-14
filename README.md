# webrtc-android-codelab
An attempt to provide a codelab for Webrtc in Android - Similar to codelab for web at https://codelabs.developers.google.com/codelabs/webrtc-web/

More at : https://vivekc.xyz/getting-started-with-webrtc-for-android-daab1e268ff4

## Setup Instructions

The test setup contains of three components:

1. Signaling server
2. WebRTC Android App
3. WebRTC example web site

Important: The IP address of the signaling server is hardcoded to `192.168.178.207` and need to be changed in files `SignallingClient.java` and `main.js`.

### Build Android App

- "Open an existing Android Studio project"
- Select the `Step-3` folder
- On "Unable to get Gradle wrapper properties from:" click "Ok" to recreate gradle files
- Ignore/Cancel all git related questions
- Agree to update Gradle
- Now a warning appears, agree to "Remove Build Tools version and sync project"
- Select "Files" "Sync Project with Gradle Files"
- Building and installing the App should work at this point

### Start Signaling Server

The signaling server works uses npm and nodejs:

```
cd signalling
npm install
node index.js
```

The last command start the signalling server.

### Start WebRTC site

```
cd signalling
python3 -m http.server
```

Now open `http://localhost:8000` in the browser and use room name `some_room_name`.
You can use a different web server as the python buildin, of course.
