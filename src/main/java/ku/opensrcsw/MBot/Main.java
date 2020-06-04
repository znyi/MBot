package ku.opensrcsw.MBot;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialOceanicTheme;

public class Main 
{
    public static void main( String[] args )
    {
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
