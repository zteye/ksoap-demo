package com.demo.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

public class WSDemo {
    //定义获取手机信息的SoapAction与命名空间,作为常量
    private static final String AddressnameSpace = "http://WebXml.com.cn/";
    //天气查询相关参数
    private static final String Weatherurl   = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";
    private static final String Weathermethod = "getWeather";
    private static final String WeathersoapAction = "http://WebXml.com.cn/getWeather";
    //归属地查询相关参数
    private static final String Addressurl = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
    private static final String Addressmethod = "getMobileCodeInfo";
    private static final String AddresssoapAction = "http://WebXml.com.cn/getMobileCodeInfo";

private String result;

    Vector resultVector;


    //定义一个获取某城市天气信息的方法：
    public  void getWether(String city) {
        result = "";
        SoapObject soapObject = new SoapObject(AddressnameSpace, Weathermethod);
        soapObject.addProperty("theCityCode", city);
        soapObject.addProperty("theUserID", "079c92cbd6d64a468c52dec5c2b05214");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(Weatherurl);
        System.out.println("天气服务设置完毕,准备开启服务");
        try {
            httpTransportSE.debug=true;
            httpTransportSE.call(WeathersoapAction, envelope);
            System.out.println("调用WebService服务成功");

            //获得服务返回的数据,并且开始解析
            SoapObject object = (SoapObject) envelope.bodyIn;
            System.out.println("获得服务数据"+object);
            result = object.toString();

            String[] resultArr = result.split("; string=");

           // for(String arritem:resultArr){
               // System.out.println("@@:"+arritem);
           // }


            System.out.println("发送完毕,textview显示天气信息:"+result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用WebService服务失败");
        }


    }


    //定义一个获取号码归属地的方法：
    public void getland(String number) {
        result = "";
        SoapObject soapObject = new SoapObject(AddressnameSpace, Addressmethod);
        soapObject.addProperty("mobileCode", number);
        soapObject.addProperty("userid", "079c92cbd6d64a468c52dec5c2b05214");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(Addressurl);
        //	System.out.println("号码信息设置完毕,准备开启服务");
        try {
            httpTransportSE.call(AddresssoapAction, envelope);
            //System.out.println("调用WebService服务成功");
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("调用WebService服务失败");
        }

        //获得服务返回的数据,并且开始解析
        SoapObject object = (SoapObject) envelope.bodyIn;//System.out.println("获得服务数据");
        result = object.getProperty(0).toString();//System.out.println("获取信息完毕,向主线程发信息");
        //System.out.println("发送完毕,归属地为:"+result);
    }


    public static void main(String[] args) {

        WSDemo demo = new  WSDemo();
        String city="1344"; //1344=西安
        demo.getWether(city);

    }
}
