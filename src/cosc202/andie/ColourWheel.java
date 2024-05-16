package cosc202.andie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColourWheel {

    private static Color chosenColour;
    public static JColorChooser chooser;
    public static Color a;

    /**The constructor
     * @author Kevin Steve Sathyanath
     */
    public ColourWheel(){

    }

    /**A method to pick a new colour with JColourChooser
     * @author Kevin Steve Sathyanath
     * @version 1.0
     */
    public static void pickColour(){

        try{
        JFrame colorFrame = new JFrame();
        colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorFrame.setSize(500, 500);

        JPanel panel = new JPanel();
        JColorChooser chooser = new JColorChooser();

        ActionListener okListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Color a = chooser.getColor();
                if(a!=null){
                    chosenColour = a;
                }
            }

        };

        ActionListener cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent f){
                colorFrame.dispose();
            }
        };
        try{
        a = JColorChooser.showDialog(colorFrame, "Pick a colour", Color.BLACK, true);
        }catch(HeadlessException h){
            System.out.println(h);
        }

        if(a!=null){
            chosenColour = a;
        }
     }//End of try
    
        catch(Exception err){
            if (err instanceof NullPointerException) {
                JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            } else {
                System.out.println(err);
            }
        } //End of catch

    } //Method of pickColour
    


    /**A getter method for the class that allows the program to get the colour the user has chosen. */
    public static Color getChosenColour(){
        return chosenColour;
    }


    
}
