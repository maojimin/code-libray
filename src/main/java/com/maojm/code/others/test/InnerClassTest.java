package com.maojm.code.others.test;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class InnerClassTest {
	public static void main(String[] args)
	{
		TalkingClok clock = new TalkingClok(1000,true);
		clock.start();
		JOptionPane.showMessageDialog(null, "Quit program?");
		System.exit(0);
	}
	
}

class TalkingClok
{
	private int interval;
	private boolean beep;
	public TalkingClok(int interval, boolean beep) {
		this.interval = interval;
		this.beep = beep;
	}
	public void start()
	{
		ActionListener listener = new TimerPrinter();
	    Timer t = new Timer(interval,listener);
	    t.start();
	}
	private class TimerPrinter implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			Date now = new Date();
			System.out.println("At the tone,the time is " + now);
			if (beep)Toolkit.getDefaultToolkit().beep();
			
		}
		
	}
	
	
	
	
}