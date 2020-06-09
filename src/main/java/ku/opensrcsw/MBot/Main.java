package ku.opensrcsw.MBot;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ToolTipUI;

import org.jnativehook.GlobalScreen;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;

public class Main 
{
    public static void main( String[] args )
    {
    	Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    	logger.setLevel(Level.WARNING);
    	logger.setUseParentHandlers(false);
    	
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                	JDialog.setDefaultLookAndFeelDecorated(true);
                    JFrame.setDefaultLookAndFeelDecorated(false);
                    MaterialLookAndFeel material = new MaterialLookAndFeel(new MaterialOceanicTheme());
                    UIManager.setLookAndFeel(material);
                    UI ui = new UI(); 
                } catch (UnsupportedLookAndFeelException e) {
                	e.printStackTrace();
                }
            }
    	});
    }
}
