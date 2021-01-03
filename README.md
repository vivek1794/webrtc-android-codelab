## About
This fork buids on vivek1794's WebRTC Android codelab. I have fixed some minor issues and bugs with the code, especially in the signalling server code. I have also migrated Step 3 of the codelab (the finished app) to androidx. I have also updated the code to work with the new Xirsys ICE server framework. The quickstart for the project also includes a guide to host the signalling server code on Heroko. Along with the ICE servers, this allows the app to work with the two phones running the app on separate networks, in distant locations. I have added a demo of using the DataChannel to send or receive messages. You can find details about how to use the DataChannel below. The ScreenCaptureActivity adds screen sharing support using the ScreenCapturerAndroid class.



## Quickstart
First, download this repository as a ZIP file.
### Build the Android Client

* Open the Step-3 folder in Android Studio
* If Android Studio asks you to configure the android framework, configure it.
* You can now push the app to your android devices. But, since the signalling server is not up yet, the app won't work now.
### Run the signalling server on your local machine
- Find your computer's local IP address using `ipconfig`
- In the SignallingClient class, go to line 74 and replace the placeholder for the url with the `http://` followed by the IP address of your computer. Set the port number to 1794. For example, if your computer's local IP address is 192.168.0.110, line 74 will look like
```socket = IO.socket("http://192.168.0.110:1794");```
- Download Node.js [here](https://nodejs.org/en/download)
- Navigate to the heroku_signalling folder located in the signalling folder
- Install the required packages by running `npm install`
- Run the signalling server code using `node index.js`
- The app will now work on 2 devices connected to the same network as the computer running the signalling server
### Run the signnaling server on Heroku
- Create an account on Heroku
- Download the Heroku CLI [here](https://devcenter.heroku.com/articles/heroku-cli#download-and-install)
- Open a command prompt and navigate to the [heroku_signalling](./signalling/heroku_signalling) folder
- Initialize an empty git repository : `git init`
- Add everything in the folder to the folder : `git add .`
- Commit the changes to the repo : `git commit -m "Commit message here"`
- Login to Heroku : `heroku login`
- Create a heroku app : `heroku create`
- Push the local repo to Heroku : `git push heroku master`
- In line 74 of SignallingClient.java, set the URL to the URL of your Heroku app and set the port number to 443. For example, if the URL of your Heroku app is https://peaceful-cliffs-12345.herokuapp.com, line 74 of SignallingClient.java will look like 
```
socket = IO.socket("https://peaceful-cliffs-12345.herokuapp.com:443");
```
- Signalling will now work if you push the app to 2 devices on different networks. However, you might still get a black screen in place of the video since we have not added any ICE servers yet.
 
