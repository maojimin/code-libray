package com.maojm.code.others.test;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;


public class AnonymousInnerClassTest {
  public static void main(String[]args){
	  TalkingClok2 clock = new TalkingClok2();
		clock.start(1000,true);
		JOptionPane.showMessageDialog(null, "Quit program?");
		System.exit(0);
  }
}
class TalkingClok2
{
	
	public void start(int interval,final boolean beep)
	{
		ActionListener listener = new 
		ActionListener()
		{

			public void actionPerformed(ActionEvent e) {
				Date now = new Date();
				System.out.println("At the tone,the time is " + now);
				if (beep)Toolkit.getDefaultToolkit().beep();
			}
		};
	    Timer t = new Timer(interval,listener);
	    t.start();
	}

	
	
	
	
}