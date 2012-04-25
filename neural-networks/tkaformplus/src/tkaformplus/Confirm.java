package tkaformplus;
//import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// Changed by Sebastien Baehni in order to be
// deprecated compliant.
public class Confirm extends Dialog {

    Button ok;
    Button cancel;
    Panel  buttonpanel;
    Panel  textpanel;
    final static public Boolean OK = true;
    final static public Boolean CANCEL = false;
    public static Boolean answer;
    
    public Confirm(Frame frame, boolean mode, String text)
    {
	super(frame, mode);
	setTitle("Confirm");

	textpanel = new Panel();
	textpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	textpanel.add(new Label(text));

	buttonpanel = new Panel();
	buttonpanel.setLayout(new FlowLayout(FlowLayout.CENTER));

	ok = new Button("Ok");
	ActionListener okListener = new ActionListener() {
            @Override
	    public void actionPerformed(ActionEvent e) {
		okPerformed();
	    }
	};
	ok.addActionListener(okListener);
	buttonpanel.add(ok);

	cancel = new Button("Cancel");
	ActionListener cancelListener = new ActionListener() {
            @Override
	    public void actionPerformed(ActionEvent e) {
		cancelPerformed();
	    }
	};
	cancel.addActionListener(cancelListener);
	buttonpanel.add(cancel);

	add("Center", textpanel);
	add("South", buttonpanel);
	setSize(200, 128);
	answer = CANCEL;
	this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);

	// We put the dialog in the center of the screen.
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(Math.abs((dimension.width-this.getSize().width)/2),Math.abs((dimension.height-this.getSize().height)/2));

    }
    
    public void okPerformed() {
	answer = OK;
	this.dispose();
    }
    
    public void cancelPerformed() {
	this.dispose();
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {	
	if (e.getID() == WindowEvent.WINDOW_CLOSING) {	 
	    this.dispose();	    
	    super.processWindowEvent(e);
	}
    }     
}
