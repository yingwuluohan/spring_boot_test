package com.unisound.iot.controller.jdk.neibu_callback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface P {
    String REQUST_SERVICE = "requestService";
    String PROBE_SERVICE = "probeService";
    String NORMALEND_SERVICE = "normalEndService";
    String REQUEST_ENTITY = "requestEntity";
    String AUDIO_VERSION = "audioVersion";
    String SERVICE_TYPE = "serviceType";
    String SERVICE_REQ_CMD = "serviceReqCmd";
    String SERVICE_PARAMETER = "serviceParameter";
    String SERVICE_PARAMETER_MAP = "serviceParameterMap";
    String TRAFFIC_PARAMETER = "trafficParameter";
    String TRAFFIC_PARAMETER_MAP = "trafficParameterMap";
    String CRYPT_VERSION = "cryptVersion";
    String SESSION_REQUEST_ID = "sessionRequestID";
    String REQUEST_ID = "requestID";
    String HTTP_ID = "httpID";
    String TOKEN = "token";
    String APPKEY = "appKey";
    String USERID = "userID";
    String IMEI = "imei";
    String SESSION_ID = "sessionID";
    String CLIENT_INFO = "ClientInfo";
    String TRAFFIC_IP = "trafficIP";
    String REMOTE_IP = "remoteIP";
    String SERVICE_TYPES = "serviceTypes";
    String SERVERS = "servers";
    String RESULT_HANDLE_FILTER = "resultHandleFilter";
    String NLU_SECRET = "nluSecret";
    String NLU_APPVR = "appvr";
    String REQUEST_INFO = "requestInfo";
    String FILTER = "filter";
    String REQUEST_VO = "requestVo";
    String REQUEST_IDS = "request_ids";
    String PROBE_NUM = "probeNum";
    String HTTP_RESUTRN_CODE = "http_return_code";
    String UDID = "udid";
    String DEVICE_TOKEN = "DEVICE_TOKEN";
    String SERVER_IP = "serverIP";
    String SERVER_PORT = "serverPort";
    String SERVER_SERIAL = "serverSerial";
    String SERVER_PROPERTY = "serverProperty";
    String TYPE_CODE = "typeCode";
    String PREFIX = "prefix";
    String PRIORITY = "priority";
    String RESOURCE_NUM = "resource_num";
    String PARAM_TEXTFORMAT = "textFormat";
    String CONTENT_TYPE = "ContentType";
    String CONTENT_LENGTH = "Content-Lenght";
    String REQUEST_STATUS = "RequestStatus";
    String ASR = "asr";
    String TTS = "tts";
    String VPR = "vpr";
    String SPEAKER = "speaker";
    String INNER_SECRET = "audioPlatform123";
    Map<String, Integer> TPYT_CODE = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 1L;

        public Integer get(Object key) {
            if ("tts".equals(key)) {
                return 2;
            } else if ("asr".equals(key)) {
                return 1;
            } else if ("vpr".equals(key)) {
                return 8;
            } else {
                return "speaker".equals(key) ? 4 : 0;
            }
        }
    };
    ConcurrentHashMap<String, Map<String, String>> ids = new ConcurrentHashMap();
    String IS_OLD_SDK = "isOldSdk";
    String CORE_SERVER_LOG = "CoreServerLog";
    String SECRET = "secret";
    String NLU_TEXT = "nlu_text";
    String CORE_PARAM = "core_param";
}
