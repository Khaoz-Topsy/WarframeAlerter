package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Files.DataFile;
import Files.UserFileReader;
import Files.UserFileWriter;

public class MyGUIDialog
{
	private static JFrame MyFrame = new JFrame();

	private static JTextField EmailTxt;// = new JTextField();
	private static JCheckBox Mod;// = new JCheckBox("Mod   ");
	private static JCheckBox Key;// = new JCheckBox("Key   ");
	private static JCheckBox Aura;// = new JCheckBox("Aura   ");
	private static JCheckBox Vauban;// = new JCheckBox("Vauban   ");
	private static JCheckBox Resource;// = new JCheckBox("Resource   ");
	private static JCheckBox Blueprint;// = new JCheckBox("Blueprint   ");

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public MyGUIDialog()
	{
	}

	public static String RefreshRateDialog()
	{
	    String MyList[] = new String[8];
	    MyList[0] = "5 Seconds";
	    MyList[1] = "15 Seconds";
	    MyList[2] = "30 Seconds";
	    MyList[3] = "60 Seconds";
	    MyList[4] = "300 Seconds (5 Minutes)";
	    MyList[5] = "600 Seconds (10 Minutes)";
	    MyList[6] = "900 Seconds (15 Minutes)";
	    MyList[7] = "1200 Seconds (20 Minutes)";

	    String Result = (String) JOptionPane.showInputDialog(MyFrame, "Choose the refresh rate", "Refresh Rate", JOptionPane.QUESTION_MESSAGE, null, MyList, "Refresh Rate");
	    
	    if(Result == null || (Result != null && ("".equals(Result))))   
	    {
			System.out.println("Pressed cancel on SetFreq");
			return "121 Seconds";
	    }
	    else
	    {
			return Result;
	    }
//	    return (String) JOptionPane.showInputDialog(MyFrame, "Choose the refresh rate", "Refresh Rate", JOptionPane.QUESTION_MESSAGE, null, MyList, "Refresh Rate");
	}

	public static void setEmailPreferences()
	{		
		MyFrame.setTitle("Email Preferences");
		MyFrame.setSize(400, 220);
		MyFrame.setResizable(false);
		MyFrame.setLocationRelativeTo(null);
		MyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		EmailTxt = new JTextField();
		Mod = new JCheckBox("Mod   ");
		Key = new JCheckBox("Key   ");
		Aura = new JCheckBox("Aura   ");
		Vauban = new JCheckBox("Vauban   ");
		Resource = new JCheckBox("Resource   ");
		Blueprint = new JCheckBox("Blueprint   ");
		
		JPanel MyPanel = new JPanel();
		MyPanel.setLayout(new BorderLayout());

		JPanel EmailPnl = new JPanel();
		EmailPnl.setLayout(new BorderLayout());

		EmailTxt = new JTextField("");
		
		EmailPnl.add(new JLabel("   "), BorderLayout.NORTH);
		EmailPnl.add(new JLabel("  Email Address: "), BorderLayout.WEST);
		EmailPnl.add(EmailTxt, BorderLayout.CENTER);
		EmailPnl.add(new JLabel("   "), BorderLayout.EAST);
		EmailPnl.add(new JLabel("   "), BorderLayout.SOUTH);
		
		Mod = new JCheckBox("Mod   ");
		Key = new JCheckBox("Key   ");
		Aura = new JCheckBox("Aura   ");
		Vauban = new JCheckBox("Vauban   ");
		Resource = new JCheckBox("Resource   ");
		Blueprint = new JCheckBox("Blueprint   ");
		
		JPanel CenterPnl = new JPanel();

		JPanel CenterBottomPnl = new JPanel();
		CenterBottomPnl.setLayout(new GridLayout(2,3));
		CenterBottomPnl.add(Mod); 
		CenterBottomPnl.add(Key); 
		CenterBottomPnl.add(Aura);
		CenterBottomPnl.add(Vauban); 
		CenterBottomPnl.add(Resource); 
		CenterBottomPnl.add(Blueprint);
		
		JLabel Temp = new JLabel("Please select what you would like to be emailed about");
		Temp.setForeground(Color.BLUE);
		CenterPnl.add(Temp, BorderLayout.NORTH);
		CenterPnl.add(CenterBottomPnl, BorderLayout.CENTER);

		JPanel BottomPnl = new JPanel();
		BottomPnl.setLayout(new BorderLayout());
		JButton Submit = new JButton("Submit");
		BottomPnl.add(new JLabel("   "), BorderLayout.NORTH);
		BottomPnl.add(new JLabel("      "), BorderLayout.WEST);
		BottomPnl.add(new JLabel("      "), BorderLayout.EAST);
		BottomPnl.add(new JLabel("   "), BorderLayout.SOUTH);
		BottomPnl.add(Submit, BorderLayout.CENTER);
		
		MyPanel.add(EmailPnl, BorderLayout.NORTH);
		MyPanel.add(CenterPnl, BorderLayout.CENTER);
		MyPanel.add(BottomPnl, BorderLayout.SOUTH);
		
		ReadSetEmailPrefferences();
		
		Submit.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(isValidEmail(EmailTxt.getText()))
				{
					boolean sMod = false;
					boolean sKey = false;
					boolean sAura = false;
					boolean sVauban = false;
					boolean sResource = false;
					boolean sBlueprint = false;

					if(Mod.isSelected()) {sMod = true;}
					if(Key.isSelected()) {sKey = true;}
					if(Aura.isSelected()) {sAura = true;}
					if(Vauban.isSelected()) {sVauban = true;}
					if(Resource.isSelected()) {sResource = true;}
					if(Blueprint.isSelected()) {sBlueprint = true;}
					
					UserFileWriter.writeEmailFile(System.getProperty("user.home") + "\\Warframe Alert.txt", new DataFile(EmailTxt.getText(), sMod, sKey, sAura, sVauban, sResource, sBlueprint));
					
					MyFrame.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid email address");
				}
			}
		});
		
		MyFrame.add(MyPanel);
		MyFrame.setVisible(true);
	}
	
	private static void ReadSetEmailPrefferences()
	{
		DataFile dTemp = UserFileReader.readEmailFile(System.getProperty("user.home") + "\\Warframe Alert.txt");
		
		EmailTxt.setText(dTemp.getEmail());
		if(dTemp.getMod() == true) {Mod.setSelected(true);}
		if(dTemp.getKey() == true) {Key.setSelected(true);}
		if(dTemp.getAura() == true) {Aura.setSelected(true);}
		if(dTemp.getVauban() == true) {Vauban.setSelected(true);}
		if(dTemp.getResource() == true) {Resource.setSelected(true);}
		if(dTemp.getBlueprint() == true) {Blueprint.setSelected(true);}
	}
	
	private static boolean isValidEmail(String SuspiciousEmail)
	{
		Pattern pattern;
		Matcher matcher;
		
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		matcher = pattern.matcher(SuspiciousEmail);
		return matcher.matches();
	}
}
