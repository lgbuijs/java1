package java1;

/******************************************************************************
 * 
 * Program Name:         Java Calculator Applet
 * Author:               Jason Elias
 * Last Modified:        April 12, 2001
 * Purpose:              Provide all of the basic functions of a calculator in 
 *                       a GUI environment -- an applet. Also illustrates how 
 *                       to multithread an applet with two different 
 *                       implementations to illustrate it: one with a Thread 
 *                       internally defined in the applet, and a Clock thread
 *                       defined outside of the applet.
 * 
 *  * Notes:             Feel free to use and improve this code, and I would love
 *                       to see any improvements that are made.
 * 
 *                       Email:   jasonelias@hotmail.com
 *                       Website: www.geocities.com/entity05
 * 
 *****************************************************************************/

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.*;

public class calc extends Applet implements Runnable, ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int Counter;            //counts the number of digits entered

    double Result;          //the answer displayed, as well as the second 
                            //operator taken for an operation
    
    double Operand;         //the first number entered for an operation
    
    double Mem;             //the variable which holds whatever value the user 
                            //wants kept in "memory"
    
    boolean DecimalFlag;    //the 'flag' that will signify whether or not the 
                            //decimal button has been pressed
    
    boolean SignFlag;       //the 'flag' that will signify whether or not the 
                            //plus/minus button has been pressed
    
    boolean OperatorKey;    //the 'flag' that will signify whether or not any 
                            //operator button has been pressed
    
    boolean FunctionKey;    //the 'flag' that will signify whether or not any f
                            //function button has been pressed
    
    int Operator;           //an integer value to indicate which operator
                            //button was pressed
    
    char currchar;          //a character to hold the value of the key most 
                            //recently pressed
    
    String GrStatus;        //String to hold the status of various graphic
                            //operations of the program
    
    String Status;          //String to hold the status of various parts 
                            //of the program

    Thread timer;            //A thread that will count how long user has been
                            //working with the calculator
        
    int secs = 0;            //counts seconds, used by Thread timer
    int mins = 0;            //counts minutes, used by Thread timer
    int hrs  = 0;            //counts hours, used by Thread timer
    
    //A string to hold the currentCount as indicated by the above variables
    String currentCount = "Count started...";
    
    //A Label that will display the current count on the calculator
    Label CountTimer = new Label(" ",Label.LEFT);
    
    //A string to hold the currentTime as indicated by the above variables
    String currentTime = "Current Time";

    //A Canvas that will display the current time on the calculator
    Canvas currTime;
    
    //This label will display all error messages
    Label DisplError = new Label(" ",Label.CENTER);
    
    /* This label is just to the left of the Display Label lcdDisplay, and will 
     * indicate whether or not a value is being held in the calculator's "memory"
     */
    Label LabelMem = new Label(" ",Label.RIGHT);

    /* This is the Display Label, which is declared as a label so the user will not 
     * be able to enter any text into it to possibly crash the calculator
     */
    Label lcdDisplay = new Label("0",Label.RIGHT);
    
    // This is the declaration of all numeric buttons to be used in the applet
     
    Button button1 = new Button("1");
    Button button2 = new Button("2");
    Button button3 = new Button("3");
    Button button4 = new Button("4");
    Button button5 = new Button("5");
    Button button6 = new Button("6");
    Button button7 = new Button("7");
    Button button8 = new Button("8");
    Button button9 = new Button("9");
    Button button0 = new Button("0");

    /* This is the declaration of all operation buttons that are used in applet. 
     * MPlus, MClear and MR are all memory functions.
     */
    Button buttonMinus    = new Button("-");
    Button buttonMultiply = new Button("x");
    Button buttonPlus     = new Button("+");
    Button buttonEquals   = new Button("=");
    Button buttonDivide   = new Button("÷");
    Button buttonClear    = new Button("C");
    Button buttonDecimal  = new Button(".");
    Button buttonNegative = new Button("±");
    Button buttonMPlus    = new Button("M+");
    Button buttonMClear      = new Button("MC");
    Button buttonMR          = new Button("MR");
    Button buttonPercent  = new Button("%");
    Button buttonOneOverX = new Button("1/X");
    Button buttonSqr      = new Button("x²");
    Button buttonSqrRoot  = new Button("sqrt");
    
    TextArea ribbon = new TextArea("** END **");
    // ribbon of all (intermediate) results



    /* This the only method that is called explicitly -- every other method is 
     * called depending on the user's actions.
     */
    public void init() 
    {   
        //Allows for configuring a layout with the restraints of a grid or 
        //something similar
        setLayout(null);
        
        //This will resize the applet to the width and height provided
        resize(420,420);
        
        //This sets the default font to Helvetica, plain, size 12
        setFont(new Font("Helvetica", Font.PLAIN, 12));

        /* Display Panel, which appears at the top of the screen. The label is
         * placed and sized with the setBounds(x,y,width,height) method, and the
         * font, foreground color and background color are all set. Then the 
         * label is added to the layout of the applet.
         */
        lcdDisplay.setBounds(42,15,308,30);
        lcdDisplay.setFont(new Font("Helvetica", Font.PLAIN, 20));
        lcdDisplay.setForeground(new Color(65280));
        lcdDisplay.setBackground(new Color(0));
        add(lcdDisplay);
        
        /* Memory Panel, which appears just to the right of the Display Panel.
         * The label is placed and sized with the setBounds(x,y,width,height)
         * method, and the font, foreground color and background color are all
         * set. Then the label is added to the layout of the applet.
         */
        LabelMem.setBounds(350,15,20,30);
        LabelMem.setFont(new Font("Helvetica", Font.PLAIN, 16));
        LabelMem.setForeground(new Color(65280));
        LabelMem.setBackground(new Color(0));
        add(LabelMem);

        /* The following declarations initialize all of the Numberic Buttons
         * that will displayed on the applet. 
         * 
         * First, an ActionListener (which will capture events) is added to the 
         * button, sending the applet as the argument
         * 
         * Second, the button is placed and sized with the setBounds(x,y,width,
         * height) method.
         * 
         * Then the default font is set for the button, and the button is added
         * to the layout of the applet.
         */
        button1.addActionListener(this);
        button1.setBounds(42,65,60,34);
        button1.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button1);

        button2.addActionListener(this);
        button2.setBounds(106,65,60,34);
        button2.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button2);

        button3.addActionListener(this);
        button3.setBounds(170,65,60,34);
        button3.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button3);

        button4.addActionListener(this);
        button4.setBounds(42,104,60,34);
        button4.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button4);

        button5.addActionListener(this);
        button5.setBounds(106,104,60,34);
        button5.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button5);

        button6.addActionListener(this);
        button6.setBounds(170,104,60,34);
        button6.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button6);

        button7.addActionListener(this);
        button7.setBounds(42,142,60,34);
        button7.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button7);

        button8.addActionListener(this);
        button8.setBounds(106,142,60,34);
        button8.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button8);

        button9.addActionListener(this);
        button9.setBounds(170,142,60,34);
        button9.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button9);

        button0.addActionListener(this);
        button0.setBounds(42,180,60,34);
        button0.setFont(new Font("Dialog", Font.BOLD, 18));
        add(button0);

        /* The following declaration and initialization statements set up all of 
         * the operation buttons on the applet. 
         * 
         * First, an ActionListener is added to each button with the "this" 
         * keyword indicating that "this object" (the applet) is being referred to.
         *
         * Then the button is "reshaped" to a certain coordinate (x and y) as 
         * well as new dimensions (width, height). 
         * 
         * Then the font is set for that button, and the arguments include:
         *        - String value representing the name of the font
         *        - a member of Font called BOLD which bolds the text
         *        - the size of the font as an integer
         *
         * Lastly, the button is added to the applet
         */
        buttonDecimal.addActionListener(this);
        buttonDecimal.setBounds(106,180,60,34);
        buttonDecimal.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonDecimal);

        buttonNegative.addActionListener(this);
        buttonNegative.setBounds(170,180,60,34);
        buttonNegative.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonNegative);

        buttonMinus.addActionListener(this);
        buttonMinus.setBounds(234,104,60,34);
        buttonMinus.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonMinus);

        buttonMultiply.addActionListener(this);
        buttonMultiply.setBounds(234,142,60,34);
        buttonMultiply.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonMultiply);

        buttonPlus.addActionListener(this);
        buttonPlus.setBounds(234,65,60,34);
        buttonPlus.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonPlus);

        buttonPercent.addActionListener(this);
        buttonPercent.setBounds(42,230,60,34);
        buttonPercent.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonPercent);

        buttonOneOverX.addActionListener(this);
        buttonOneOverX.setBounds(106,230,60,34);
        buttonOneOverX.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonOneOverX);

        buttonSqr.addActionListener(this);
        buttonSqr.setBounds(170,230,60,34);
        buttonSqr.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonSqr);

        buttonSqrRoot.addActionListener(this);
        buttonSqrRoot.setBounds(234,230,60,34);
        buttonSqrRoot.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonSqrRoot);
        
        buttonEquals.addActionListener(this);
        buttonEquals.setBounds(309,230,60,34);
        buttonEquals.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonEquals);

        buttonDivide.addActionListener(this);
        buttonDivide.setBounds(234,180,60,34);
        buttonDivide.setFont(new Font("Dialog", Font.BOLD, 18));
        add(buttonDivide);

        buttonClear.addActionListener(this);
        buttonClear.setBounds(309,65,60,34);
        buttonClear.setFont(new Font("Dialog", Font.BOLD, 18));
        buttonClear.setForeground(new Color(16711680));
        buttonClear.setBackground(new Color(12632256));
        add(buttonClear);
  
        buttonMPlus.addActionListener(this);
        buttonMPlus.setBounds(309,104,60,34);
        buttonMPlus.setFont(new Font("Dialog", Font.BOLD, 18));
        buttonMPlus.setForeground(Color.blue);
        add(buttonMPlus);

        buttonMClear.addActionListener(this);
        buttonMClear.setBounds(309,142,60,34);
        buttonMClear.setFont(new Font("Dialog", Font.BOLD, 18));
        buttonMClear.setForeground(Color.blue);
        add(buttonMClear);

        buttonMR.addActionListener(this);
        buttonMR.setBounds(309,180,60,34);
        buttonMR.setFont(new Font("Dialog", Font.BOLD, 18));
        buttonMR.setForeground(Color.blue);
        add(buttonMR);

        /* This is the initialization of the DisplayError label. It is placed and 
         * sized with the setBounds(x,y,width,height) method, and then the font, 
         * foreground color and background color are all set. Finally, the label
         * is added to the layout of the applet.
         */
        DisplError.setBounds(42,278,329,15);
        DisplError.setFont(new Font("Dialog", Font.BOLD, 12));
        DisplError.setForeground(new Color(16711680));
        DisplError.setBackground(new Color(0));
        add(DisplError);
        
        /* This is the initialization of the CountTimer label. It is placed and 
         * sized with the setBounds(x,y,width,height) method, and then the font, 
         * foreground color and background color are all set. The text displayed
         * on the label is initialized to the string currentCount, and finally, 
         * the label is added to the layout of the applet.
         */
        CountTimer.setBounds(42,309,164,25);
        CountTimer.setFont(new Font("Dialog", Font.BOLD, 15));
        CountTimer.setForeground(Color.cyan);
        CountTimer.setBackground(Color.gray);
        CountTimer.setText(currentCount);
        add(CountTimer);
        
        /* This is the initialization of the currTime canvas. It is instantiated
         * and then placed and sized with the setBounds(x,y,width,height) method.
         * Next the font, foreground color and background color are all set. The 
         * Finally, the label is added to the layout of the applet.
         */
        currTime = new Canvas();
        currTime.setBounds(206,309,165,25);
        currTime.setFont(new Font("Dialog", Font.BOLD, 15));
        currTime.setForeground(Color.orange);
        currTime.setBackground(Color.gray);
        add(currTime);
        
        ribbon.setBounds(42,350,329,60);
        ribbon.setFont(new Font("Dialog", Font.PLAIN, 12));
        add(ribbon);

        
        Clicked_Clear();            //calls the Clicked_Clear method (C button)
        
    }    //end of calc init method
    
    /* The following integer values are being set as "final" because
     * they are going to be used for determining what button has been
     * pushed
     */
    public final static int OpMinus=11,
                            OpMultiply=12,
                            OpPlus=13,
                            OpDivide=15,
                            OpMPlus=19,
                            OpMClear=20,
                            OpMR=21;

    /* This method is called whenever anything needs to be displayed
     * in the error message field at the bottom of the calculator,
     * accepting a String as an argument
     */
    void DisplayError(String err_msg)
    {
        /* calls the setText method of the Label DisplError, sending
         * whatever string it received initially 
         */
        DisplError.setText(err_msg);
    }

    /* This method is called whenever anything needs to be displayed
     * in the error message field at the bottom of the calculator,
     * accepting a String as an argument
     */
    void DisplayRibbon(String msg)
    {
        /* calls the setText method of the Label DisplError, sending
         * whatever string it received initially 
         */
        ribbon.insert(msg + "\r\n", 0);
    }

    /* This method is called whenever a numeric button (0-9) is pushed. */
    public void NumericButton(int i) 
    {
        DisplayError(" ");            //Clears the error message field
   
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();

        /* Checks if an operator key has just been pressed, and if it has, 
         * then the limit of 20 digits will be reset for the user so that 
         * they can enter in up to 20 new numbers
         */
        if (OperatorKey == true)
        {
            Counter = 0;
        }
        
        Counter = Counter + 1;        //increments the counter

        /* This is a condition to see if the number currently displayed is zero OR 
         * an operator key other than +, -, *, or / has been pressed.
         */
        if ((Display == "0") || (Status == "FIRST"))
        {
            Display= "";            //Do not display any new info
        }

        if (Counter < 21)            //if more than 20 numbers are entered
        {
            //The number just entered is appended to the string currently displayed
            Display = Display + String.valueOf(i);
        }
        else
        {
            //call the DisplayError method and send it an error message string
            DisplayError("Digit Limit of 20 Digits Reached");
        }

        lcdDisplay.setText(Display);    //sets the text of the lcdDisplay                     
                                        //Label

        Status = "VALID";                //sets the Status string to valid
        OperatorKey = false;            //no operator key was pressed
        FunctionKey = false;            //no function key was pressed
    }

    /* This method is called whenever an operator button is pressed, and is    
     * sent an integer value representing the button pressed.
     */
    public void OperatorButton(int i) 
    {
        DisplayError(" ");            //Clears the error message field
   
        /* Creates a new Double object with the specific purpose of retaining
         * the string currently on the lcdDisplay label, and then immediately
         * converts that string into a double-precision real number and then 
         * gives that number to the variable Result.
         */
        Result = (new Double(lcdDisplay.getText())).doubleValue();
        
        //If no operator key has been pressed OR a function has been pressed
        if ((OperatorKey == false) || (FunctionKey = true))
        {
            switch (Operator)            //depending on the operation performed
            {
                /* if the user pressed the addition button, add the two numbers 
                 * and put them in double Result
                 */
                case OpPlus     : Result = Operand + Result;
                                  DisplayRibbon("+");
                                  break;
                                  
                /* if the user pressed the subtraction button, subtract the two
                 * numbers and put them in double Result
                 */
                case OpMinus    : Result = Operand - Result;
                                  DisplayRibbon("-");
                                  break;
                                  
                /* if the user pressed the multiplication button, multiply
                 * the two numbers and put them in double Result
                 */
                case OpMultiply : Result = Operand * Result;
                                  DisplayRibbon("*");
                                  break;
                                  
                /* if the user pressed the division button, check to see if 
                 * the second number entered is zero to avoid a divide-by-zero
                 * exception
                 */
                case OpDivide   : DisplayRibbon("/");
                                   if (Result == 0)
                                  {
                                      //set the Status string to indicate an
                                      //an error
                                      Status = "ERROR";
                                      
                                      //display the word "ERROR" on the 
                                      //lcdDisplay label
                                      lcdDisplay.setText("ERROR");
                                      DisplayRibbon("ERROR");
                                      
                                      /* call the DisplayError method and 
                                       * send it a string indicating an error 
                                       * has occured and of what type
                                       */
                                      DisplayError("ERROR: Division by Zero");
                                  }
                                  else
                                  {
                                      //divide the two numbers and put the 
                                      //answer in double Result
                                      Result = Operand / Result;
                                  }
            }
            DisplayRibbon(Result + "");
            
            //if after breaking from the switch the Status string is not set
            //to "ERROR"
            if (Status != "ERROR")
            {
                Status = "FIRST";    /* set the Status string to "FIRST" to 
                                     * indicate that a simple operation was 
                                     * not performed
                                     */
                
                Operand = Result;    //Operand holds the value of Result
                
                Operator = i;        /* the integer value representing the 
                                     * operation being performed is stored 
                                     * in the integer Operator
                                     */
                
                //The lcdDisplay label has the value of double Result 
                //displayed
                lcdDisplay.setText(String.valueOf(Result));
                ribbon.insert(String.valueOf(Result), 0);
                
                //The boolean decimal flag is set false, indicating that the 
                //decimal button has not been pressed
                DecimalFlag = false;
                
                //The boolean sign flag is set false, indicating that the sign
                //button has not been pressed
                SignFlag = false;
                
                //The boolean OperatorKey is set true, indicating that a simple
                //operation has been performed
                OperatorKey = true;
                
                //The boolean FunctionKey is set false, indicating that a 
                //function key has not been pressed
                FunctionKey = false;
                
                DisplayError(" ");        //Clears the error message field
            }

        }
   
    }            //end of OperatorButton method

    /* This is a method that is called whenever the decimal button is 
     * pressed.
     */
    public void DecimalButton()
    {
        DisplayError(" ");        //Clears the error message field
        
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();
        
        //if a simple operation was performed successfully
        if (Status == "FIRST")
        {
            Display = "0";        //set Display string to character 0
        }

        //If the decimal button has not already been pressed
        if (!DecimalFlag)
        {
            //appends a decimal to the string Display
            Display = Display + "."; 
        }
        else
        {
            /* calls the DisplayError method, sending a string 
             * indicating that the number already has a decimal 
             */
            DisplayError("Number already has a Decimal Point");
        }
        
        /* calls the setText method of the Label lcdDisplay and 
         * sends it the string Display
         */
        lcdDisplay.setText(Display);
        DecimalFlag = true;        //the decimal key has been pressed
        Status = "VALID";        /* Status string indicates a valid
                                 * operation has been performed
                                 */
        OperatorKey = false;    //no operator key has been pressed
        
    }    //end of the DecimalButton method

    /* This method is called whenever the percent button is pressed
     */
    void PercentButton()
    {
        DisplayError(" ");        //clears the error message field
        
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();

        /* if the Status string is not set to "FIRST" OR the Display string
         * does not currently hold the value "0"
         */
        if ((Status != "FIRST") || (Display != "0"))
        {
            /* Creates a new Double object with the specific purpose of retaining
             * the string currently on the lcdDisplay label, and then immediately
             * converts that string into a double-precision real number and then 
             * gives that number to the variable Result.
             */
            Result = (new Double(lcdDisplay.getText())).doubleValue();
            DisplayRibbon("%");
            
            //divide the double Result by 100, getting the percentage
            Result = Result / 100;
            
            /* call the setText method of Label lcdDisplay and send it the string
             * that represents the value in Result
             */
            lcdDisplay.setText(String.valueOf(Result));
            DisplayRibbon(Result + "");
            Status = "FIRST";        //
            OperatorKey = true;        //an operator key has been pressed
            FunctionKey = true;        //a function key has been pressed
        }
    }    //end of the PercentButton method

    /* This method is called first when the calculator is initialized 
     * with the init() method, and is called every time the "C" button 
     * is pressed
     */
    void Clicked_Clear()
    {
        Counter = 0;                //sets the counter to zero
        Status = "FIRST";            //sets Status to FIRST
        Operand = 0;                //sets Operand to zero
        Result = 0;                    //sets Result to zero
        Operator = 0;                //sets Operator integer to zero
        
        DecimalFlag = false;        //decimal button has not been 
                                    //pressed
        
        SignFlag = false;            //sign button has not been pressed
        
        OperatorKey = false;        //no operator button has been 
                                    //pressed
        
        FunctionKey = false;        //no function button has been 
                                    //pressed
        
        /* calls the setText method of Label lcdDisplay and sends 
         * it the character "0"
         */
        lcdDisplay.setText("0");    
        DisplayError(" ");            //clears the error message field
    }

    /* This method is called whenever the sign button is pressed */
    void PlusMinusButton()
    {
        DisplayError(" ");            //clears the error message field
        
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();

        /* if Status is not set to FIRST and the Display string does not
         * hold the value "0"
         */
        if ((Status != "FIRST") || (Display != "0"))
        {
            /* Creates a new Double object with the specific purpose of retaining
             * the string currently on the lcdDisplay label, and then immediately
             * converts that string into a double-precision real number and then 
             * gives that number to the variable Result.
             */
            Result = (new Double(lcdDisplay.getText())).doubleValue();
                        
            //sets the double Result to it's negative value
            Result = -Result;
            
            /* call the setText method of Label lcdDisplay and send it the string
             * that represents the value in Result
             */
            lcdDisplay.setText(String.valueOf(Result));
            ribbon.insert(String.valueOf(Result), 0);
            Status = "VALID";        //sets Status string to VALID
            SignFlag = true;        //the sign button has been pressed
            DecimalFlag = true;        //a decimal has appeared
        }
    }    //end of the PlusMinusButton method

    /* This method is called whenever the square button is pressed */
    void SqrButton()
    {
        DisplayError(" ");            //clears the error message field
        
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();

        /* if Status is not set to FIRST and the Display string does not
         * hold the value "0"
         */
        if ((Status != "FIRST") || (Display != "0"))
        {
            /* Creates a new Double object with the specific purpose of retaining
             * the string currently on the lcdDisplay label, and then immediately
             * converts that string into a double-precision real number and then 
             * gives that number to the variable Result.
             */
            Result = (new Double(lcdDisplay.getText())).doubleValue();
            
            /* multiply the double Result by itself, effectively squaring 
             * the number */
            Result = Result * Result;
            
            /* call the setText method of Label lcdDisplay and send it the string
             * that represents the value in Result
             */
            lcdDisplay.setText(String.valueOf(Result));
            ribbon.insert(String.valueOf(Result), 0);
            
            Status = "FIRST";        //indicates this is the first time
                                    //this button has been pressed
            
            OperatorKey = true;        //an operator button has been pressed
            FunctionKey = true;        //a function button has been pressed
        }
    }    //end of the SqrButton method
    
    /* This method is called whenever the square button is pressed */
    void SqrRootButton()
    {
        DisplayError(" ");            //clears the error message field
        
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();

        /* if Status is not set to FIRST and the Display string does not
         * hold the value "0"
         */
        if ((Status != "FIRST") || (Display != "0"))
        {
            /* Creates a new Double object with the specific purpose of retaining
             * the string currently on the lcdDisplay label, and then immediately
             * converts that string into a double-precision real number and then 
             * gives that number to the variable Result.
             */
            Result = (new Double(lcdDisplay.getText())).doubleValue();
            
            /* Makes a call to the Math class method "sqrt", which produced the 
             * square root and stores the value in Result
             */
            Result = Math.sqrt(Result);
            
            /* call the setText method of Label lcdDisplay and send it the string
             * that represents the value in Result
             */
            lcdDisplay.setText(String.valueOf(Result));
            
            Status = "FIRST";        //indicates this is the first time
                                    //this button has been pressed
            
            OperatorKey = true;        //an operator button has been pressed
            FunctionKey = true;        //a function button has been pressed
        }
    }    //end of the SqrRootButton method

    /* This method is called whenever the OneOverXButton is pressed */
    void OneOverXButton()
    {
        DisplayError(" ");            //clears the error message field
        
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();

        /* if Status is not set to FIRST and the Display string does not
         * hold the value "0"
         */
        if ((Status != "FIRST") || (Display != "0"))
        {
            /* Creates a new Double object with the specific purpose of retaining
             * the string currently on the lcdDisplay label, and then immediately
             * converts that string into a double-precision real number and then 
             * gives that number to the variable Result.
             */
            Result = (new Double(lcdDisplay.getText())).doubleValue();
            
            //divides one by the double Result 
            Result = 1 / Result;
            
            /* call the setText method of Label lcdDisplay and send it the string
             * that represents the value in Result
             */
            lcdDisplay.setText(String.valueOf(Result));
            
            Status = "FIRST";        //indicates that this is the first time
                                    //this button has been pressed
            
            OperatorKey = true;        //an operator key has been pressed
            FunctionKey = true;        //a function key has been pressed
        }
    }    //end of the OneOverXButton method

    /* This method is called whenever one of the three memory buttons is pressed,
     * accepting an integer representing the button pressed as an argument
     */
    void MemoryButton(int i)
    {
        DisplayError(" ");            //clears the error message field
        
        /* Declares a String called Display that will initialize to whatever 
         * is currently displayed in the lcdDisplay of the calculator
         */
        String Display = lcdDisplay.getText();
        
        //depending on the value sent representing the buttons
        switch (i)
        {
            /* if the M+ button is pressed, check if the Display string has the value 
             * "0." OR just "0", but along with the second condition check if double 
             * Mem holds the value zero
             */
            case OpMPlus : if (((Display == "0.") || (Display == "0")) && (Mem == 0))
                           {
                               //clears the LabelMem label
                               LabelMem.setText(" ");
                           }
                           else
                           {
                               /* Creates a new double variable called temp, which accepts the 
                                * double value of a new Double object retaining the string 
                                * currently on the lcdDisplay label, and then immediately 
                                * converts that string into a double-precision real number 
                                */
                               double temp = (new Double(lcdDisplay.getText())).doubleValue();
                               
                               //set the Mem variable with that the sum of what's currently in
                               //Mem and the temp variable
                               Mem = Mem + temp;
                               
                               /* calls the setText method of the Label LabelMem, sending
                                * it the character "M"
                                */
                               LabelMem.setText("M");
                           }
                           break;
                        
            /* if the MR button is pressed, call the setText method of Label lcdDisplay, 
             * sending it the string that represents what the value of Mem is
             */
            case OpMR : lcdDisplay.setText(String.valueOf(Mem));
                        break;
                        
            /* if the MC button is pressed, set the Mem variable to zero */
            case OpMClear : Mem = 0;
                            
                            //clears the LableMem field
                            LabelMem.setText(" ");
                            break;
        }
        Status = "FIRST";            //indicates that this is first time 
                                    //button has been pressed
        
        OperatorKey = true;            //an operator button has been pressed
        
        //if the Mem variable has the value zero
        if (Mem == 0)
        {
            LabelMem.setText(" ");    //clear the LabelMem field
        }
    }    //end of the MemoryButton method
    
    /* This was left commented out so that a shell would be ready if needed
    public void paint(Graphics g)
    {
        
    }*/    
    
/***************************************************************************
 * 
 * This is the overridden update method for this applet -- update is used to 
 * eliminate the flicker that happens with repainting an image from scratch.
 * 
 **************************************************************************/
    public void update(Graphics g)
    {
        /* The format of the coordinates below is:
         *        x position on the applet
         *        y position on the applet
         *        width of the component
         *        height of the component
         */
        
        //Sets the color to white and draw a rectangle around the applet
        g.setColor(Color.white);
        g.drawRect(0,0,420,368);
            
        //sets the color to black and draws lines down the east and south sides
        //of the applet to make it appear three-dimensional
        g.setColor(Color.black);
        g.drawLine(419,0,419,367);
        g.drawLine(0,367,419,367);
            
        //sets the color to gray and draws lines right next to the black lines
        //to enhance the three-dimensional effect
        g.setColor(Color.gray);
        g.drawLine(418,1,418,366);
        g.drawLine(1,366,418,366);
        
        //sets the color to gray and draws a rectangle around the lcdDisplay
        //label to give it some depth
        g.setColor(Color.gray);
        g.drawRect(41,14,329,31);
        
        //sets the color to yellow and draws a rectangle around the DisplError
        //label to give it some importance and help attract the eye to errors
        //displayed there
        g.setColor(Color.yellow);
        g.drawRect(41,277,330,16);
        
        if (GrStatus == "Timer")        //checks for the timer indicator GrStatus
        {
            ++secs;                        //increment seconds
            if(secs == 60)                //when they equal 60
            {
                mins++;                    //increment minutes
                secs = 0;                //set seconds to zero
            }    
                        
            if(mins == 60)                //when minutes are equal to 60
            {
                hrs++;                    //increment hours
                secs = 0;                //set seconds to zero
                mins = 0;                //set minutes to zero
            }
            
            //if hours are less than ten, put a zero in front of the value, 
            //otherwise display as is and assign it to string currentCount
            if(hrs < 10) { currentCount = "0" + hrs + ":"; }
            else { currentCount = hrs + ":"; }
            
            //if minutes are less than ten, put a zero in front of the value, 
            //otherwise display as is and append it to string currentCount
            if(mins < 10) { currentCount = currentCount + "0" + mins + ":"; }
            else { currentCount = currentCount + mins + ":"; }
            
            //if seconds are less than ten, put a zero in front of the value, 
            //otherwise display as is and append it to string currentCount
            if(secs < 10) {    currentCount = currentCount + "0" + secs; }
            else { currentCount = currentCount + secs; }
            
            //sets the Text on CountTimer label to a "Time Used - " portion and
            //appends the string currentCount
            CountTimer.setText("Time Used - " + currentCount);
        }
        if (GrStatus == "Error")            //when GrStatus indicates an error
        {
            CountTimer.setText("Error!");    //set the text of label CountTimer
                                            //to "Error!"
        }
    }
    
    /************************************************************************
    /*
    /* This method is called whenever an action is performed on the applet,
    /* specifically whenever one of the buttons on the applet is pressed
    /* 
    /************************************************************************/
    public void actionPerformed(ActionEvent event)
    {
        /* Declares an object named "src" to represent the event.getSource()
         * method. This shortens the code that follows.
         */
        Object src = event.getSource();
        
        /* Checks to see if the source of the event which src is representing 
         * is a Button and can behave like a Button
         */
        if (src instanceof Button)
        {
            /* Checks to see if Status string holds the value ERROR OR if the 
             * source of the event is the buttonClear ("C") button
             */
            if ((Status != "ERROR") || (src == buttonClear))
            {
                /* The following conditions check for numeric buttons that have 
                 * been pressed, and then called the method NumericButton and 
                 * send it an integer value depending on the value of the button 
                 * pressed
                 */
                if (src == button1)
                {
                    NumericButton(1);
                }
                if (src == button2)
                {
                    NumericButton(2);
                }
                if (src == button3)
                {
                    NumericButton(3);
                }
                if (src == button4)
                {
                    NumericButton(4);
                }
                if (src == button5)
                {
                    NumericButton(5);
                }
                if (src == button6)
                {
                    NumericButton(6);
                }
                if (src == button7)
                {
                    NumericButton(7);
                }
                if (src == button8)
                {
                    NumericButton(8);
                }
                if (src == button9)
                {
                    NumericButton(9);
                }
                if (src == button0)
                {
                    NumericButton(0);
                }

                /* The following conditions check for operator buttons that have 
                 * been pressed, and then call the OperatorButton method, sending 
                 * the corresponding integer value 
                 */
                if (src == buttonMinus)
                {
                    OperatorButton(11);
                }
                if (src == buttonMultiply)
                {
                    OperatorButton(12);
                }
                if (src == buttonPlus)
                {
                    OperatorButton(13);
                }
                if (src == buttonEquals)
                {
                    OperatorButton(14);
                }
                if (src == buttonDivide)
                {
                    OperatorButton(15);
                }

                /* This condition checks if the decimal button has been pressed, 
                 * and then calls the DecimalButton method
                 */
                if (src == buttonDecimal)
                {
                    DecimalButton();
                }

                /* This condition checks if the percent button has been pressed, 
                 * and then calls the PercentButton method
                 */
                if (src == buttonPercent)
                {
                    PercentButton();
                }

                /* This condition checks if the clear button has been pressed, 
                 * and then calls the Clicked_Clear method
                 */
                if (src == buttonClear)
                {
                    Clicked_Clear();
                }

                /* This condition checks if the Plus Minus Button has been 
                 * pressed, and then calls the PlusMinusButton method
                 */
                if (src == buttonNegative)
                {
                    PlusMinusButton();
                }

                /* This condition checks if the Square Button has been pressed, 
                 * and then calls the SqrButton method
                 */
                if (src == buttonSqr)
                {
                    SqrButton();
                }

                /* This condition checks if the Square Root Button has been
                 * pressed, and then calls the SqrRootButton method
                 */
                if (src == buttonSqrRoot)
                {
                    SqrRootButton();
                }
                
                /* This condition checks if the One over X Button has been 
                 * pressed, and then calls the OneOverXButton method
                 */
                if (src == buttonOneOverX)
                {
                    OneOverXButton();
                }

                /* These conditions checks to see if any of the Memory Buttons 
                 * have been pressed, and then calls the MemoryButton method and 
                 * sends them the corresponding integer values
                 */
                if (src == buttonMPlus)
                {
                    MemoryButton(19);
                }

                if (src == buttonMClear)
                {
                    MemoryButton(20);
                }

                if (src == buttonMR)
                {
                    MemoryButton(21);
                }
            }
        }
       }            //end of actionPerformed method

/****************************************************************************
 * 
 * The following method handles all of the key events that occurs when a user
 * enters values from the keyboard. It accepts an Event object and an integer
 * representing the key pressed as arguments.
 * 
 * It should be noted that in order to accept keys entered to the lcdDisplay,
 * the applet must be in focus.
 * 
 ***************************************************************************/
    public boolean keyDown(Event evt, int key) 
    {
        //assigns key value to currchar through typecasting key to a character
        currchar = (char)key;        
                
        /* The following conditions check to see if a number key has been pressed
         * and calls the NumericButton method, sending it the appropriate integer
         * as an argument
         */
        if (currchar == '0') { NumericButton(0); }
        if (currchar == '1') { NumericButton(1); }
        if (currchar == '2') { NumericButton(2); }
        if (currchar == '3') { NumericButton(3); }
        if (currchar == '4') { NumericButton(4); }
        if (currchar == '5') { NumericButton(5); }
        if (currchar == '6') { NumericButton(6); }
        if (currchar == '7') { NumericButton(7); }
        if (currchar == '8') { NumericButton(8); }
        if (currchar == '9') { NumericButton(9); }

        /* The following conditions check to see if any of the operator keys 
         * which are on the number pad have been pressed. It then calls the 
         * OperatorButton method, sending it the appropriate integer value as 
         * an argument. 
         * 
         * It should be noted that the main functions checked for are those on
         * the number pad, but if they are pressed at other occurences on the 
         * keyboard, they are still accepted.
         */
        if (currchar == '/') { OperatorButton(15); }
        if (currchar == '*') { OperatorButton(12); }
        if (currchar == '-') { OperatorButton(11); }
        if (currchar == '+') { OperatorButton(13); }
        
        if (currchar == '=') { OperatorButton(14); }
        
        //This condition is different from the others because it tests for the
        //Enter key being pressed, and this is pre-built into the Event evt 
        //object
        if (currchar == Event.ENTER) { OperatorButton(14); }
        
        /* This checks for the decimal key being pressed, either on the number
         * pad or the period key, and calls the DecimalButton method.
         */
        if (currchar == '.') { DecimalButton(); }    
        
        return true;                //indicate successful operation
    }
    
/****************************************************************************
 * 
 * The following methods are overridden for the timer Thread, as this applet
 * implements Runnable. 
 * 
 ****************************************************************************/
    //overridden start method
    public void start()
    {
        try                                    //try this code
        {
            if (timer == null)                //if the timer doesn't exist
            {
                timer = new Thread(this);    //creates a new Thread, passing the 
                                            //applet as the argument
            
                timer.start();                //starts the timer Thread
            }
        
            //creates a new Clock object, passing it the Canvas currTime
            Clock clock1 = new Clock(currTime);    
        }
        //catches a NullPointerException and stops the thread if one occurs
        catch (NullPointerException e) { stop(); }
    }
    
    //overridden stop method
    public void stop()
    {
        try                                    //try this code
        {
            if (timer != null)                //if the timer Thread exists
            {
                timer.stop();                //stop the timer thread
            }
        }
        //catches a NullPointerException 
        catch (NullPointerException e) {}
    }
    
    //overridden run method
    public void run()
    {
        while (true)                    //while the thread exists
        {
            try                            //try executing this code
            {
                GrStatus = "Timer";        //sets GrStatus 
                
                Thread.sleep(1000);        //puts the timer thread to "sleep" for
                                        //a thousand milliseconds, or one second
            }
            //catches an InteruptedException if one occurs
            catch(InterruptedException e)
            {
                GrStatus = "Error";        //indicates an error has occured
                timer.stop();            //stops the thread
            }
            //catches a NullPointerException if one occurs
            catch(java.lang.NullPointerException e)
            {
                GrStatus = "Error";        //indicates an error has occured
                timer.stop();            //stops the thread
            }
            repaint();                    //calls the repaint method
        }
    }    
    
}                //end of calc class

/****************************************************************************
 * 
 * This class is derived from the Thread class, and it's purpose is to run
 * a thread that will display the current time on the applet.
 * 
 ***************************************************************************/
class Clock extends Thread
{
    //A Canvas that will display the current time on the calculator
    Canvas Time;
    
    //A Date object that will access the current time
    private Date now;
    
    //A string to hold the current time 
    private String currentTime;
        
    //The constructor for Clock, accepting a Label as an argument
    public Clock(Canvas _Time)
    {
        Time = _Time;        //_Time is passed by reference, so Time
                            //now refers to the same Canvas
        
        start();            //start this thread
    }
    
    //The overriden run method of this thread
    public void run()
    {
        //while this thread exists
        while (true)
        {
            try
            {
                draw_clock();        //calls the draw_clock method
                
                sleep(1000);        //puts this thread to sleep for one
                                    //second
            }
            //catches an InterruptedException that the sleep() method might throw
            catch (InterruptedException e) { suspend(); }
            
            //catches a NullPointerException and suspends the thread if one occurs
            catch (NullPointerException e) { suspend(); }
        }
    }
    
    //A method to draw the current time onto the Time Canvas on the applet
    public void draw_clock()
    {
        try
        {
            //Obtains the Graphics object from the Canvas Time so that it can 
            //be manipulated directly
            Graphics g = Time.getGraphics();
        
            g.setColor(Color.gray);        //sets the color of the Graphics object
                                        //to gray for the rectangle background
            
            g.fillRect(0,0,165,25);        //fills the Canvas area with a rectangle
                                        //starting at 0,0 coordinates of the Canvas
                                        //and extending to the length and width
        
            g.setColor(Color.orange);    //sets the color of the Graphics object 
                                        //to orange for the text color
        
            get_the_time();                //calls the get_the_time() method
        
            //calls the drawString method of the Graphics object g, which will
            //draw a string to the screen
            //
            //Accepts a string and two integers to represent the coordinates
            g.drawString("Current Time - " + currentTime, 0, 17);        
        }
        //catches a NullPointerException and suspends the thread if one occurs
        catch (NullPointerException e) { suspend(); }
    }
    
    //A method to obtain the current time, accurate to the second
    public void get_the_time()
    {
        //creates a new Date object for "now" every time this is called
        now = new Date( );
        
        //integers to hold the hours, minutes and seconds of the current time
        int a = now.getHours();
        int b = now.getMinutes();
        int c = now.getSeconds();
        
        if (a == 0) a = 12;        //if hours are zero, set them to twelve
                            
        if (a > 12) a = a -12;    //if hours are greater than twelve, make a     
                                //conversion to civilian time, as opposed to 
                                //24-hour time
        
        if ( a < 10)            //if hours are less than 10
            //sets the currentTime string to 0, appends a's value and a 
            //colon
            currentTime = "0" + a + ":" ;
        else
            //otherwise set currentTime string to "a", append a colon
            currentTime = a +":";            
        
        if (b < 10)                //if minutes are less than ten
            
            //append a zero to string currentTime, then append "b" and a colon
            currentTime = currentTime + "0" + b + ":" ;
        else
            //otherwise append "b" and a colon to currentTime string 
            currentTime = currentTime + b + ":" ;
        
        if (c < 10)                //if seconds are less than ten
            
            //append a zero to string currentTime, then append "c" and a colon
            currentTime = currentTime + "0" + c ;
        else    
            //otherwise append "c" to currentTime string
            currentTime = currentTime + c;
    }
}        //end of the Clock class
