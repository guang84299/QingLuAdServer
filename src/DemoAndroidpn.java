
import java.util.ArrayList;
import java.util.Date;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//
//  DemoAndroidpn.java
//  FeOA
//
//  Created by LuTH on 2012-3-26.
//  Copyright 2012 flyrise. All rights reserved.
//

public class DemoAndroidpn {

	private static final String[] portArray = new String[] { "80"};
	private static int num = 0;

	public static void main(String[] args) throws Exception {

		// show status like '%Threads_connected%';
		Date d = new Date();
		Thread.sleep(4000);
		Date d2 = new Date();
		
		System.out.println(d2.getTime()-d.getTime());
//		while(num < 1)
//		{
//			Thread.sleep(5000);
//			num ++;
//			test();
//			System.out.println("=====================num="+num);
//		}
    }  
	
	public static void test()
	{
		CountDownLatch begin = new CountDownLatch(1);  
		CallHttpRequest.successRequest = 0;
		CallHttpRequest.failRequest = 0;
		CallHttpRequest.timeOutRequest = 0;
	       
        ArrayList<String> arrayList = new ArrayList<String>();
        int count = 100;
        while(count > 0)
        {
        	count --;
        	
        	arrayList.add("http://localhost:8080/pushStatistics.do?action=updateShowNum&data=56&&&&&1");
        }
        int allRequestSize = arrayList.size();  
        System.out.println("all request size is " + allRequestSize);  
        //设置最大的并发数量为60  
        ExecutorService exec = Executors.newFixedThreadPool(100);  
  
        CountDownLatch end = new CountDownLatch(allRequestSize);  
      int i = 0;  
        for (String str : arrayList) {  
            exec.execute(new CallHttpRequest( str, begin, end));  
              
            /*如果想测试60个线程并发的访问,发配到同一台服务器上的两个tomcat，就用下面注释掉的代码 
             * if (i % 2 == 0) { 
                exec.execute(new CallHttpRequest(portArray[0], str, begin, end)); 
            } else if (i % 2 == 1) { 
                exec.execute(new CallHttpRequest(portArray[1], str, begin, end)); 
            } */  
          i++;  
        //  System.out.println("=="+i);
        }  
        long startTime = System.currentTimeMillis();  //34703 ms69 ms
        //当60个线程，初始化完成后，解锁，让六十个线程在4个双核的cpu服务器上一起竞争着跑，来模拟60个并发线程访问tomcat  
        begin.countDown();  
  
        try {  
            end.await();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } finally {  
        	System.out.println("all url requests is done!");  
        	System.out.println("the success size: " + CallHttpRequest.successRequest);  
        	System.out.println("the fail size: " + CallHttpRequest.failRequest);  
        	System.out.println("the timeout size: " + CallHttpRequest.timeOutRequest);  
            double successRate = (double)CallHttpRequest.successRequest / allRequestSize;  
            System.out.println("the success rate is: " + successRate*100+"%");  
            long endTime = System.currentTimeMillis();  
            long costTime = endTime - startTime;  
            if(CallHttpRequest.failRequest > 0)
            {
            	num = 5000;
            }
            System.out.println("the total time cost is: " + costTime + " ms");  
            System.out.println("every request time cost is: " + costTime / allRequestSize  
                    + " ms");  
        }  
        exec.shutdown();  
        System.out.println("main method end");  
  
	}

}