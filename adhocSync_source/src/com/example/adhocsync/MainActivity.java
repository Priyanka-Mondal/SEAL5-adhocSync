package com.example.adhocsync;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	
	Queue<Integer> q = new LinkedList<Integer>();
	 Lock lock= new ReentrantLock();
	 final Handler mhand = new  Handler(Looper.getMainLooper());
	 boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Handler mainHandler = new  Handler(Looper.getMainLooper());
		Thread t = new Thread()
		{
			public void run()
			{
				Runnable myRunnable = new Runnable()
				{
					public void run()
					{
						
						//lock.lock();
						if(flag==true)
						{
							System.out.println("---------enters in C() as flag is true--------------"+ Thread.currentThread().getName());
							q.clear();
							System.out.println("---------flag is still true in C()------------"+ Thread.currentThread().getName());
						}
						else
						{
							//lock.unlock();
							System.out.println("---------C() can not access q as flag is false--------------");
							System.out.println("---------Exiting C()--------------");
						}
						
					};
				};
				mainHandler.post(myRunnable);	
			};
			
		};
		t.start();
		
		s();
		//Log.v("The main thread is:"+ Thread.currentThread().getName());
		A();
	
    }
    
    public void A()
	{
		
		//lock.lock();
    	if(flag==true)
    	{
	    	flag=false;
			System.out.println("---------flag set to false in A()--------------");
			if(!q.isEmpty())
			{
				
				Runnable myRunnable = new Runnable()
				{
					public void run()
					{
						q.remove();					
						//lock.unlock();
						flag=true;
						
						System.out.println("---------flag is set to true in B()--------------");
						System.out.println("---------Exiting B()-----------------");
					};
				};
				mhand.postDelayed(myRunnable,10000);	
			}
			else
			{
				//lock.unlock();
				flag=true;
				System.out.println("---------flag is set to true in A() as q is empty--------------");
			}
    	}
		System.out.println("---------End of A------------");
	}
    
    public void s()
	{
		q.add(55);
		q.add(155);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
