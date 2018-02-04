package xyz.vivekc.webrtccodelab;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Webrtc_Step3
 * Created by vivek-3102 on 11/03/17.
 */

class SignallingClient {
    private static SignallingClient instance;
    private String roomName = null;
    private Socket socket;
    boolean isChannelReady = false;
    boolean isInitiator = false;
    boolean isStarted = false;
    private SignalingInterface callback;

    @SuppressLint("TrustAllX509TrustManager")
    private final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) {
        }
    }};

    public static SignallingClient getInstance() {
        if (instance == null) {
            instance = new SignallingClient();
        }
        if (instance.roomName == null) {
            instance.roomName = "9789957730";
        }
        return instance;
    }


    public void init(SignalingInterface signalingInterface) {
        this.callback = signalingInterface;
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, trustAllCerts, null);
            IO.setDefaultHostnameVerifier((hostname, session) -> true);
            IO.setDefaultSSLContext(sslcontext);
//            socket = IO.socket("https://webviander.com:8080");
            socket = IO.socket("https://webviander.com:1794");
            socket.connect();
            Log.d("SignallingClient", "init() called");

            if (!roomName.isEmpty()) {
                emit(roomName);
            }


            socket.on("created", args -> {
                Log.d("SignallingClient", "created call() called with: args = [" + Arrays.toString(args) + "]");
                isInitiator = true;
                callback.onCreatedRoom();
            });

            socket.on("full", args -> Log.d("SignallingClient", "full call() called with: args = [" + Arrays.toString(args) + "]"));

            socket.on("join", args -> {
                Log.d("SignallingClient", "join call() called with: args = [" + Arrays.toString(args) + "]");
                isChannelReady = true;
                callback.onNewPeerJoined();
            });
            socket.on("joined", args -> {
                Log.d("SignallingClient", "joined call() called with: args = [" + Arrays.toString(args) + "]");
                isChannelReady = true;
                callback.onJoinedRoom();
            });

            socket.on("log", args -> Log.d("SignallingClient", "log call() called with: args = [" + Arrays.toString(args) + "]"));

            socket.on("bye", args -> callback.onRemoteHangUp((String) args[0]));
            socket.on("message", args -> {
                Log.d("SignallingClient", "message call() called with: args = [" + Arrays.toString(args) + "]");
//{"type":"candidate","label":0,"id":"audio","candidate":"candidate:2609216701 1 tcp 1518280447 192.168.0.102 9 typ host tcptype active generation 0 ufrag l8SE network-id 1 network-cost 10"}
//{"type":"offer","sdp":"v=0\r\no=- 8093392003873814575 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE audio video\r\na=msid-semantic: WMS sbdlHKmFwsCDw8cYlndTZvOYalWHIQKHZGqy\r\nm=audio 9 UDP\/TLS\/RTP\/SAVPF 111 103 104 9 0 8 106 105 13 126\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:l8SE\r\na=ice-pwd:93qvjCFF385AIS+hScug+EpX\r\na=fingerprint:sha-256 19:0D:16:8B:0A:25:47:A5:0D:17:08:22:5D:81:72:BE:10:71:CC:A8:47:CC:C0:8C:F4:B5:91:2D:7E:35:49:02\r\na=setup:actpass\r\na=mid:audio\r\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\na=sendrecv\r\na=rtcp-mux\r\na=rtpmap:111 opus\/48000\/2\r\na=rtcp-fb:111 transport-cc\r\na=fmtp:111 minptime=10;useinbandfec=1\r\na=rtpmap:103 ISAC\/16000\r\na=rtpmap:104 ISAC\/32000\r\na=rtpmap:9 G722\/8000\r\na=rtpmap:0 PCMU\/8000\r\na=rtpmap:8 PCMA\/8000\r\na=rtpmap:106 CN\/32000\r\na=rtpmap:105 CN\/16000\r\na=rtpmap:13 CN\/8000\r\na=rtpmap:126 telephone-event\/8000\r\na=ssrc:3418250277 cname:oeerXdsaEYQMbLAW\r\na=ssrc:3418250277 msid:sbdlHKmFwsCDw8cYlndTZvOYalWHIQKHZGqy bd6f3054-9686-425a-93ac-91b8aababa14\r\na=ssrc:3418250277 mslabel:sbdlHKmFwsCDw8cYlndTZvOYalWHIQKHZGqy\r\na=ssrc:3418250277 label:bd6f3054-9686-425a-93ac-91b8aababa14\r\nm=video 9 UDP\/TLS\/RTP\/SAVPF 100 101 107 116 117 96 97 99 98\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:l8SE\r\na=ice-pwd:93qvjCFF385AIS+hScug+EpX\r\na=fingerprint:sha-256 19:0D:16:8B:0A:25:47:A5:0D:17:08:22:5D:81:72:BE:10:71:CC:A8:47:CC:C0:8C:F4:B5:91:2D:7E:35:49:02\r\na=setup:actpass\r\na=mid:video\r\na=extmap:2 urn:ietf:params:rtp-hdrext:toffset\r\na=extmap:3 http:\/\/www.webrtc.org\/experiments\/rtp-hdrext\/abs-send-time\r\na=extmap:4 urn:3gpp:video-orientation\r\na=extmap:5 http:\/\/www.ietf.org\/id\/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:6 http:\/\/www.webrtc.org\/experiments\/rtp-hdrext\/playout-delay\r\na=sendrecv\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:100 VP8\/90000\r\na=rtcp-fb:100 ccm fir\r\na=rtcp-fb:100 nack\r\na=rtcp-fb:100 nack pli\r\na=rtcp-fb:100 goog-remb\r\na=rtcp-fb:100 transport-cc\r\na=rtpmap:101 VP9\/90000\r\na=rtcp-fb:101 ccm fir\r\na=rtcp-fb:101 nack\r\na=rtcp-fb:101 nack pli\r\na=rtcp-fb:101 goog-remb\r\na=rtcp-fb:101 transport-cc\r\na=rtpmap:107 H264\/90000\r\na=rtcp-fb:107 ccm fir\r\na=rtcp-fb:107 nack\r\na=rtcp-fb:107 nack pli\r\na=rtcp-fb:107 goog-remb\r\na=rtcp-fb:107 transport-cc\r\na=fmtp:107 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\na=rtpmap:116 red\/90000\r\na=rtpmap:117 ulpfec\/90000\r\na=rtpmap:96 rtx\/90000\r\na=fmtp:96 apt=100\r\na=rtpmap:97 rtx\/90000\r\na=fmtp:97 apt=101\r\na=rtpmap:99 rtx\/90000\r\na=fmtp:99 apt=107\r\na=rtpmap:98 rtx\/90000\r\na=fmtp:98 apt=116\r\na=ssrc-group:FID 1793194698 2067397205\r\na=ssrc:1793194698 cname:oeerXdsaEYQMbLAW\r\na=ssrc:1793194698 msid:sbdlHKmFwsCDw8cYlndTZvOYalWHIQKHZGqy a8056b20-69a1-4b43-8a93-95c2ac3a1e9b\r\na=ssrc:1793194698 mslabel:sbdlHKmFwsCDw8cYlndTZvOYalWHIQKHZGqy\r\na=ssrc:1793194698 label:a8056b20-69a1-4b43-8a93-95c2ac3a1e9b\r\na=ssrc:2067397205 cname:oeerXdsaEYQMbLAW\r\na=ssrc:2067397205 msid:sbdlHKmFwsCDw8cYlndTZvOYalWHIQKHZGqy a8056b20-69a1-4b43-8a93-95c2ac3a1e9b\r\na=ssrc:2067397205 mslabel:sbdlHKmFwsCDw8cYlndTZvOYalWHIQKHZGqy\r\na=ssrc:2067397205 label:a8056b20-69a1-4b43-8a93-95c2ac3a1e9b\r\n"}

                Log.d("vivek1794", args[0].toString());
                if (args[0] instanceof String) {
                    Log.d("SignallingClient", "String received :: " + args[0]);
                    String data = (String) args[0];
                    if (data.equalsIgnoreCase("got user media")) {
                        callback.onTryToStart();
                    }
                    if (data.equalsIgnoreCase("bye")) {
                        callback.onRemoteHangUp(data);
                    }
                } else if (args[0] instanceof JSONObject) {
                    try {

                        JSONObject data = (JSONObject) args[0];
                        Log.d("SignallingClient", "Json Received :: " + data.toString());
                        String type = data.getString("type");
                        if (type.equalsIgnoreCase("offer")) {
                            callback.onOfferReceived(data);
//                                if (!isInitiator && !isStarted) {
//                                    maybeStart();
//                                }
//                                pc.setRemoteDescription(new RTCSessionDescription(message));
//                                doAnswer();
                        } else if (type.equalsIgnoreCase("answer") && isStarted) {
//                                pc.setRemoteDescription(new RTCSessionDescription(message));
                            callback.onAnswerReceived(data);
                        } else if (type.equalsIgnoreCase("candidate") && isStarted) {
                            callback.onIceCandidateReceived(data);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private void emit(String message) {
        Log.d("SignallingClient", "emit() called with: event = [" + "create or join" + "], message = [" + message + "]");
        socket.emit("create or join", message);
    }

    public void emitMessage(String message) {
        Log.d("SignallingClient", "emitMessage() called with: message = [" + message + "]");
        socket.emit("message", message);
    }

    public void emitMessage(SessionDescription message) {
        try {
            Log.d("SignallingClient", "emitMessage() called with: message = [" + message + "]");
            JSONObject obj = new JSONObject();
            obj.put("type", message.type.canonicalForm());
            obj.put("sdp", message.description);
            Log.d("emitMessage", obj.toString());
            socket.emit("message", obj);
            Log.d("vivek1794", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void emitIceCandidate(IceCandidate iceCandidate) {
        try {
            JSONObject object = new JSONObject();
            object.put("type", "candidate");
            object.put("label", iceCandidate.sdpMLineIndex);
            object.put("id", iceCandidate.sdpMid);
            object.put("candidate", iceCandidate.sdp);
            socket.emit("message", object);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void close() {
        socket.emit("bye", roomName);
        socket.disconnect();
        socket.close();
    }


    interface SignalingInterface {
        void onRemoteHangUp(String msg);

        void onOfferReceived(JSONObject data);

        void onAnswerReceived(JSONObject data);

        void onIceCandidateReceived(JSONObject data);

        void onTryToStart();

        void onCreatedRoom();

        void onJoinedRoom();

        void onNewPeerJoined();
    }
}
