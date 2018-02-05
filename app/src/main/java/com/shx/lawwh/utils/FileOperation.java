package com.shx.lawwh.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * get file properties
 * 获取文件的信息
 * @author Mike
 *
 */
public class FileOperation {
 
 /**
  * get file size 通过URL地址获取文件大小
  * @param filePath * url file path
  * @return filesize
  * @throws MalformedURLException 
  */
 public static String getFileSize(String filePath) throws MalformedURLException {
  HttpURLConnection urlcon = null;
  String size="";
  //format double 
  java.text.DecimalFormat fnum = new java.text.DecimalFormat("#0.000");
  //create url link
  URL url=new URL(filePath);
  try {
   //open url
   urlcon = (HttpURLConnection)url.openConnection();
   //get url properties
   double filesize=urlcon.getContentLength();
   //format output
   System.out.print(filesize);
   size=fnum.format(filesize/1024);
  } catch (IOException e) {
   e.printStackTrace();
  } finally{
   //close connect
   urlcon.disconnect();
  }
  return size;
 }
 //test
 public static void main(String [] args){
   try {
   System.out.println(FileOperation.getFileSize("http://192.168.1.127:8080/files/7、SHT 3544-2009 石油化工对置式往复压缩机组施工及验收规范.pdf"));
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}