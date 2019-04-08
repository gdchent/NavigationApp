package com.funtsui.updatelib.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class StatisticsBean {

    /**
     * data : {"channel_id":"market","name":"test","status":0,"sign":"8P7yhlqnYC5kynZsFqU5eA==","random":"ca15c01861eba35e"}
     * attributes : null
     * state : 0
     * message : ok
     */

    private StatisticsBean.DataBean data;
    private int state;
    private String message;

    public StatisticsBean.DataBean getData() {
        return data;
    }

    public void setData(StatisticsBean.DataBean data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * channel_id : market
         * name : test
         * status : 0
         * sign : 8P7yhlqnYC5kynZsFqU5eA==
         * random : ca15c01861eba35e
         */

        private String channel_id;
        private String name;
        private int status;
        private String sign;
        private String random;

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public static StatisticsBean.DataBean fromJson(JSONObject jsonObject) {
            StatisticsBean.DataBean bean = new StatisticsBean.DataBean();
            try {
                bean.status = jsonObject.getInt("status");
                bean.channel_id = jsonObject.getString("channel_id");
                bean.name = jsonObject.getString("name");
                bean.sign = jsonObject.getString("sign");
                bean.random = jsonObject.getString("random");
                return bean;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static StatisticsBean fromJson(JSONObject jsonObject) {
        StatisticsBean bean = new StatisticsBean();
        try {
            bean.state = jsonObject.getInt("state");
            bean.message = jsonObject.getString("message");
            JSONObject dataObject = jsonObject.getJSONObject("data");
            bean.data = DataBean.fromJson(dataObject);
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
