package kap.com.smarthome.android.communication.bean.base.DATABean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/10/27 0027.
 *
 * “TRIGGERDEVICE”:[ {“DEVICEID”:”1234”, “TRIGGERVALUE” :”1234”, “RELAYBOXID”:”1234”},
 {“DEVICEID”:”1234”, “TRIGGERVALUE” :”1234”, “RELAYBOXID”:”1234”}]
 *
 */

public class UDPScenesTriggerData  {

    @JSONField(name = "DEVICEID", ordinal = 1)
    private String DEVICEID ;

    @JSONField(name = "TRIGGERVALUE", ordinal = 2)
    private String TRIGGERVALUE ;

    @JSONField(name = "RELAYBOXID", ordinal = 3)
    private String RELAYBOXID ;

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getTRIGGERVALUE() {
        return TRIGGERVALUE;
    }

    public void setTRIGGERVALUE(String TRIGGERVALUE) {
        this.TRIGGERVALUE = TRIGGERVALUE;
    }

    public String getRELAYBOXID() {
        return RELAYBOXID;
    }

    public void setRELAYBOXID(String RELAYBOXID) {
        this.RELAYBOXID = RELAYBOXID;
    }
}
