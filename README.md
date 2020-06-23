## Update
As many might have noticed, I haven't been updating this codelab. It has been some time since I worked with WebRTC but it is in my to-do list to get this updated with as much information as possible. For now, I assume this is broken. PRs are welcome until I get the time to fix it myself. I am pretty sure the Signaling and ICE Servers are not working anymore. For ICE, I used a service - Xirsys which changed their APIs and pricing structure. You would have to look for alternatives (or install Coturn on an AWS instance to run the TURN servers yourself). For Signaling, it is not meant to be production ready. It requires a lot of changes to make it work. 

I would recommend looking at https://webrtc.org/getting-started/firebase-rtc-codelab for using Firebase for signalling. I intend on getting the following done

- Make change to the app code so that it uses Firebase for signalling and that would mean you can use Firebase RTC Codelab above to get the web component
- Try to create a multi-peer mesh network (smaller group calls without the need for a media server)
- Screen sharing support for mobiles

I don't have a timeline on when I can get to these. If someone wants to collaborate with me on this, I would love to see some PRs.

Thank you for the support! 

# webrtc-android-codelab
An attempt to provide a codelab for Webrtc in Android - Similar to codelab for web at https://codelabs.developers.google.com/codelabs/webrtc-web/

More at : https://vivekc.xyz/getting-started-with-webrtc-for-android-daab1e268ff4

## Setup Instructions

The test setup contains of three components:

1. Signaling server
2. WebRTC Android App
3. WebRTC example web site

Important: The IP address of the signaling server is hardcoded to `192.168.178.207` and need to be changed in files `SignallingClient.java` and `main.js`.

### Build Android App Client

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

### Start Web Client

```
cd signalling
python3 -m http.server
```

Now open `http://localhost:8000` in the browser.
You can use a different web server as the python buildin, of course.

## How to enable HTTPS

Change http to https in `signalling/index.js`, `signalling/js/main.js` and `Step-3/app/src/main/java/xyz/vivekc/webrtccodelab/SignallingClient.java`.

Then create files `key.pem` and `cert.pem` (e.g. with openssl) and uncomment the key options in `signalling/index.js`.

Command to create `key.pem` and `cert.pem`:
```
openssl req -newkey rsa:2048 -new -nodes -x509 -days 3650 -keyout key.pem -out cert.pem
```
