package	main.java.com.benny.mcprofileimporter;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;


public class mygui extends JPanel
{
	private static final long serialVersionUID = 1L;
	JButton	browse;
	JFileChooser chooser;
	public static JTextField directory;
	JLabel intro;
	JPanel browser;
	JLabel info;
	public static JLabel reminder;
	JButton proceed;
	String choosertitle;
	public static JFrame frame;
	ImageIcon icon;
	Image image;
	public static Scanner text;
	
	public static String workingDirectory;
	
	public static void main(String[] args) throws IOException
	{
		findMinecraftDir();
		mygui panel = new mygui();
		frame = new JFrame("MC Profile Importer");
		frame.setResizable(false);
		frame.addWindowListener(new	WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		frame.add(panel,"Center");
		frame.setSize(350, 270);
		frame.setVisible(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
	
	public mygui() throws IOException
	{
		text = new Scanner(getClass().getResourceAsStream("/main/resources/gui/text.txt"), "UTF-8");
	    BufferedImage resizedImg = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/main/resources/gui/logo.png")), 0, 0, 70, 70, null);
	    g2.dispose();
		icon = new ImageIcon(resizedImg);
		intro = new JLabel(text.nextLine(), icon, JLabel.CENTER);
		intro.setVerticalTextPosition(JLabel.BOTTOM);
		intro.setHorizontalTextPosition(JLabel.CENTER);
		intro.setAlignmentX(Component.CENTER_ALIGNMENT);
		info = new JLabel("select .minecraft directory if non-default", JLabel.CENTER);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		directory = new JTextField(workingDirectory += "\\.minecraft");
		browser = new JPanel();
		browser.setLayout(new BoxLayout(browser, BoxLayout.LINE_AXIS));
		browser.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 10));
		browser.add(directory);
		browser.add(Box.createRigidArea(new Dimension(10, 0)));
		browse = new JButton(new AbstractAction("Browse") {
			private static final long serialVersionUID = 1L;

			@Override
	        public void actionPerformed( ActionEvent e ) {
				dirBrowser();
	        }
	    });
		browser.setMaximumSize(new Dimension(500, 37));
		browser.setPreferredSize(new Dimension(500, 37));
		browser.add(browse);
		browser.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(intro, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(info, BorderLayout.CENTER);
		add(browser, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(0, 15)));
		proceed = new JButton("Proceed");
		proceed.addActionListener(new copydir());
		proceed.setAlignmentX(Component.CENTER_ALIGNMENT);
		reminder = new JLabel("make sure your MC launcher is closed!");
		reminder.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(reminder);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(proceed, BorderLayout.CENTER);
	}
	
	public void dirBrowser()
	{
		chooser = new JFileChooser();	
		chooser.setCurrentDirectory(new File(workingDirectory += "\\.minecraft"));		
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{	
			directory.setText(chooser.getSelectedFile().toString());
		}
	}
	
	public static void findMinecraftDir()
	{
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN"))
		{
		    workingDirectory = System.getenv("AppData");
		}
		else
		{
		    workingDirectory = System.getProperty("user.home");
		    workingDirectory += "\\Library\\Application Support";
		}
		
	}
}