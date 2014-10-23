package userplugin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "UserPlugin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private String serverName = "127.0.0.1";
	private String dbName = "glass_data";
	private String url = "jdbc:mysql://" + serverName +  "/" + dbName;
	private String username = "root";
	private String password = "Gl@ss2014";
	public static Connection MySQLConnection;
	public static String identification;
	
	/**
	 * The constructor
	 * @throws IOException 
	 */
	public Activator() throws IOException {
		identification = JOptionPane.showInputDialog("Please type your name:");
		
		// Connect with database
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Problem finding driver");
			e.printStackTrace();
		}
		try {
			MySQLConnection = DriverManager.getConnection(
			        url,
			        username,
			        password);
		} catch (SQLException e) {
			System.out.println("Problem making connection");
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
