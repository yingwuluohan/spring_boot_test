package com.unisound.iot.controller.design_modle.proxy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @author  dfn  : 
* @date 创建时间：2015年12月31日 下午6:12:54 
* @version 1.0 
* @parameter   
* @return  
*/
public class TestProxy {
	public static void main( String[] args ){
		BookFacadeProxy book = new BookFacadeProxy();
		Count count = ( Count )book.bind( new CountProxy(  new CountImpl()) );
		count.queryCount();
		//transferLongToDate("MM/dd/yyyy HH:mm:ss" , 1452165900000L);
		getTimer();
	}
	
	   private static String transferLongToDate(String dateFormat,Long millSec){

		 SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		 Date date= new Date(millSec);
		 System.out.println( sdf.format(date));
				return sdf.format(date);

	   }

	   public static void getTimer(){
		   long lTime = 0L;
		   String sDt = "01/11/2016 16:20:00";
		   SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		   Date dt2=null;
		try {
			dt2 = sdf.parse(sDt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   //继续转换得到秒数的long型
		   lTime = dt2.getTime();
		   System.out.println(  lTime);
	   }
	   
}
