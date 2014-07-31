package com.maojm.code.others.test;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class TimerTest {
public static void main(String[] args){
	ActionListener listenner = new TimerPrinter();
	Timer t=new Timer(1000,listenner);
	t.start();
	JOptionPane.showMessageDialog(null, "Quit Program");
	System.exit(0);
}
	
}

class TimerPrinter implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		Date now = new Date();
		System.out.println("At the tone,the time is " + now);
		Toolkit.getDefaultToolkit().beep();
		
	}
	
}
