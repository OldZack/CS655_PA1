class MultithreadingDemo extends Thread 
{   String t_name;
    public MultithreadingDemo(String name){
        t_name = name;
    }
    public void run() 
    { 	
       while (true){
        System.out.println(t_name);
       }
    } 
} 
  

public class MultiThread 
{ 
    public static void main(String[] args) 
    {   
        new MultithreadingDemo("Thread - 1").start(); 
        new MultithreadingDemo("Thread - 2").start(); 
        
    } 
} 