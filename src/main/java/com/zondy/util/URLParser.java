package com.zondy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
public class URLParser {
    protected byte type;
    protected static final byte TYPE_URL = 1;
    protected static final byte TYPE_QUERY_STRING = 2;
    protected String url;
    protected String baseUrl;
    protected String queryString;
    protected String label;
    protected String charset = "utf-8";


    public static URLParser fromURL(String url) {
        URLParser parser = new URLParser();

        parser.type = 1;
        parser.url = url;

        String[] split = url.split("\\?", 2);
        parser.baseUrl = split[0];
        parser.queryString = (split.length > 1 ? split[1] : "");

        String[] split2 = url.split("#", 2);
        parser.label = (split2.length > 1 ? split2[1] : null);

        return parser;
    }

    public static URLParser fromQueryString(String queryString) {
        URLParser parser = new URLParser();

        parser.type = 2;
        parser.queryString = queryString;

        return parser;
    }

    public URLParser useCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public Map<String, String> compile() throws UnsupportedEncodingException {
        Map<String, String> parsedParams=null;
        String paramString = this.queryString.split("#")[0];
        String[] params = paramString.split("&");

        parsedParams = new HashMap<String, String>(params.length);
        for (String p : params) {
            String[] kv = p.split("=");
            if (kv.length == 2) {
                parsedParams.put(kv[0], URLDecoder.decode(kv[1], this.charset));
            }
        }

        return parsedParams;
    }


    public static void main(String args[]){
        String str="?signature=98f7e1e223f2f54f33d8a8fe5cb44364addff80b&msgcontent=%3Cxml%3E%3CToUserName%3E%3C%21%5BCDATA%5B100614%5D%5D%3E%3C%2FToUserName%3E%3CFromUserName%3E%3C%21%5BCDATA%5B601994%405.jsga-lanxin%5D%5D%3E%3C%2FFromUserName%3E%3CCreateTime%3E1531453570%3C%2FCreateTime%3E%3CMsgType%3E%3C%21%5BCDATA%5Btext%5D%5D%3E%3C%2FMsgType%3E%3CResourceId%3E%3C%21%5BCDATA%5B%5D%5D%3E%3C%2FResourceId%3E%3CContent%3E%3C%21%5BCDATA%5B123%5D%5D%3E%3C%2FContent%3E%3CMsgId%3E114071870%3C%2FMsgId%3E%3CRecordDomain%3E%3C%21%5BCDATA%5B5.jsga-lanxin%5D%5D%3E%3C%2FRecordDomain%3E%3C%2Fxml%3E&nonce=5707486&echostr=9Kb0Arolw&timestamp=1531453570704";
        try {
            Map<String,String> map=URLParser.fromURL(str).compile();
            for(String s: map.keySet()){
                System.out.println(s+"="+map.get(s));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
