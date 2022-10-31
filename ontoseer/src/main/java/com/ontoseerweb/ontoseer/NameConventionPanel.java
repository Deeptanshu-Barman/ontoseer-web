package com.ontoseerweb.ontoseer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

public class NameConventionPanel extends JFrame{
	
	//JFileChooser fileChooser = new JFileChooser();
//    private JLabel labelEnterdescription = new JLabel("Enter class/property name for which you want to get recommendation");
//    private JLabel recommondedName = new JLabel("Recommonded Name is ");
//    private JTextField textDescription = new JTextField(20);
//    private JTextArea textarea= new JTextArea("");
//    
//    private JButton buttonLogin = new JButton("Class Name Recommendation");
//    private JButton buttonLogin1 = new JButton("Property Name Recommendation");
//    private JButton buttoncontinue = new JButton("Want more recommendation?");
//    private JButton close = new JButton("Close the panel");
//    
//    private ActionListener refreshAction = e ->vocab(null);
//    private ActionListener refreshAction2 = e->vocab1(null);
//    private ActionListener refreshAction1 = e ->continues();
//    private ActionListener closeAction = e->dispose();
    
//    private boolean flag1 = false, flag2 = false;
    
    public NameConventionPanel() {
        super("Naming Convention Recommendation Panel");
        //System.out.println("Name Convention Panel");
  
        // create a new panel with GridBagLayout manager
//        JPanel newPanel = new JPanel(new GridBagLayout());
//
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.anchor = GridBagConstraints.WEST;
//        constraints.insets = new Insets(25, 25, 25, 25);
//         
//        // add components to the panel label
//        constraints.gridx = 0;
//        constraints.gridy = 0;     
//        newPanel.add(labelEnterdescription, constraints);
//        
//        // Edit Text
//        constraints.gridx = 1;
//        newPanel.add(textDescription, constraints);
//        
//        // Recommendation display Edit Text
//        constraints.gridx = 0;
//        constraints.gridy = 2; 
//        constraints.gridwidth = 1;
//        newPanel.add(recommondedName, constraints);
//        constraints.gridx = 1;
//        newPanel.add(textarea, constraints);
//        textarea.setLineWrap(true);
//        textarea.setWrapStyleWord(true);
//        textarea.setEditable(false);
//        //textarea.setSize(150, 250);
//
//        //Class Recommendation Button
//        constraints.gridx = 0;
//        constraints.gridy = 6;
//        constraints.gridwidth = 1;
//        newPanel.add(buttonLogin, constraints);
//        buttonLogin.addActionListener(refreshAction);
//        
//        // Property Recommendation Button
//        constraints.gridx = 1;
//        newPanel.add(buttonLogin1, constraints);
//        buttonLogin1.addActionListener(refreshAction2);
//        
//        // Continue Button 
//        constraints.gridx = 0;
//        constraints.gridy = 8;
//        constraints.gridwidth = 1;
//        //constraints.anchor = GridBagConstraints.CENTER;
//        newPanel.add(buttoncontinue, constraints);
//        buttoncontinue.addActionListener(refreshAction1);
//        
//        // close Button
//        constraints.gridx = 1;
//        newPanel.add(close, constraints);
//        close.addActionListener(closeAction);
//        
//        // set border for the panel
//        newPanel.setBorder(BorderFactory.createTitledBorder(
//                BorderFactory.createEtchedBorder(), "Naming convention Recommendation Panel"));
//
//        add(newPanel);
//        pack();
//        setLocationRelativeTo(null);  
    }
     
    // Action for class recommendation button
    public HashMap<String, String> vocab(List<String> list) {
    	HashMap<String, String>reClass = new HashMap<>();
    	List<String>ls=new ArrayList<String>();
    	//if(!flag1) {
	    	ClassNameConvention v = new ClassNameConvention();
	    	//System.out.println("OWLAPIFirst Classes "+list.size());
	    	reClass = v.classRecommendation(list);
	    	//ls.addAll(v.classRecommendation(list));
//	    	System.out.println("\n\nClass Name\t\tRecommended Class Name");
//	    	System.out.println("=======================================================");
//	    	for(int i=0;i<ls.size();i++) {
//	    		System.out.println(list.get(i)+"\t\t" +ls.get(i));
//	    	}
//	    	System.out.println("=======================================================\n");
//	    	for(int i =0;i<ls.size();i++) {
//	    		textarea.append(ls.get(i)+"\n");
//	    	}
//	    	flag1 = true;
//    	}
    	//return ls;
	    	//System.out.println("Recommended Class === > "+reClass.get(cls));
	    return reClass;
    }
	 
    // Action for property recommendation button
	public HashMap<String, String> vocab1(List<String> propertylist) {
    	List<String>ls=new ArrayList<String>();
    	HashMap<String, String>reProperty = new HashMap<>();
  //  	if(!flag2) {
    	ClassNameConvention v = new ClassNameConvention();    	
    	//ls.addAll(v.propertiesRecommendation(propertylist));
    	reProperty = v.propertiesRecommendation(propertylist);
    	
//    	System.out.println("\n\nProperty Name\t\tRecommended Property Name");
//    	System.out.println("=======================================================");
//    	for(int i=0;i<ls.size();i++) {
//    		System.out.println(propertylist.get(i)+"\t\t" +ls.get(i));
//    	}
//    	System.out.println("=======================================================\n");
//    	for(int i =0;i<ls.size();i++) {
//    		textarea.append(ls.get(i)+"\n");
//    	}
//    	flag2 = true;
//    	}
//    	return ls;
    	//System.out.println("Recommended Property === > "+reProperty.get(property));
    	return reProperty;
	}
      
//    public void continues() {
//    	flag1 = flag2 = false;
//    	textDescription.setText("");
//    	textarea.setText("");
//    }
//    
//    public void result() {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//         
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new NameConventionPanel().setVisible(true);
//            }
//        });
//    }
    
    public static void main(String[] args) {
        // set look and feel to the system look and feel
    		NameConventionPanel p=new NameConventionPanel();
    		//p.result();
    }

}
