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
- In the SignallingClient class, go to line 74 and replace the placeholder for the IP address with the IP address of your computer. Set the port number to  
- Doenload Node.js here : https://nodejs.org/en/download
- Navigate to the heroku_signalling folder located in the signalling folder
- Install the required packages by running `npm install`
- Run the signalling server code using `node index.js`
- 
 

 
