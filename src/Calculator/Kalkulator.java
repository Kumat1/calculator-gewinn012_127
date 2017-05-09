package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Kalkulator extends JFrame implements ActionListener
{
    final int MAX_INPUT_LENGTH=25;
    final int INPUT_MODE=0;
    final int RESULT_MODE=1;
    final int ERROR_MODE=2;
    //untuk yang variabel
    int displayMode;
    boolean clearOnNextDigit, percent;
    double lastNumber;
    String lastOperator;
    //menentukan font
    Font f1=new Font("Consolas", 0, 13);
    Font f12=new Font("Consolas", 1, 13);
    private JMenu jmenuFile, jmenuHelp;
    private JMenuItem jmenuitemExit, jmenuitemAbout;
    private JLabel jlbOutput;
    private JButton jbnButtons[];
    private JPanel jplMaster, jplBackSpace, jplControl;

    //constructornya
    public Kalkulator()
    {
        //masukkan JMenuBar
        //persediaan JMenu

        jmenuFile=new JMenu("File");
        jmenuFile.setFont(f12);
        jmenuFile.setMnemonic(KeyEvent.VK_F);

        jmenuitemExit=new JMenuItem("Exit");
        jmenuitemExit.setFont(f1);
        jmenuitemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        jmenuFile.add(jmenuitemExit);

        jmenuHelp=new JMenu("Help");
        jmenuHelp.setFont(f12);
        jmenuHelp.setMnemonic(KeyEvent.VK_H);

        jmenuitemAbout=new JMenuItem("Sebuah Kalkulator");
        jmenuitemAbout.setFont(f1);

        jmenuHelp.add(jmenuitemAbout);

        JMenuBar mb=new JMenuBar();
        mb.add(jmenuFile);
        mb.add(jmenuHelp);
        setJMenuBar(mb);

        //masukkan frame layout
        setBackground(Color.darkGray);
        jplMaster=new JPanel();
        jlbOutput=new JLabel("0");
        jlbOutput.setHorizontalTextPosition(JLabel.RIGHT);
        jlbOutput.setBackground(Color.pink);
        jlbOutput.setOpaque(true);

        //memperlengkap frame
        getContentPane().add(jlbOutput, BorderLayout.NORTH);
        jbnButtons=new JButton[24];

        JPanel jplButtons=new JPanel();
        for(int i=0; i<=9; i++)
        {
            jbnButtons[i]=new JButton(String.valueOf(i));
        }

        //membuat tombol buttons
        jbnButtons[10]=new JButton("+/-");
        jbnButtons[11]=new JButton(".");
        jbnButtons[12]=new JButton("=");
        jbnButtons[13]=new JButton("/");
        jbnButtons[14]=new JButton("*");
        jbnButtons[15]=new JButton("-");
        jbnButtons[16]=new JButton("+");
        jbnButtons[17]=new JButton("sqrt");
        jbnButtons[18]=new JButton("√"); // dan pada akhirnya kami mengambil simbol dari word bang :')
        jbnButtons[19]=new JButton("%");

        jplBackSpace=new JPanel();
        jplBackSpace.setLayout(new GridLayout(1,1,2,2));
        jbnButtons[20]=new JButton("Backspace");
        jplBackSpace.add(jbnButtons[20]);

        jplControl=new JPanel();
        jplControl.setLayout(new GridLayout(1,2,2,2));
        jbnButtons[21]=new JButton("CE");
        jplControl.add(jbnButtons[21]);
        jplButtons.setLayout(new GridLayout(4,5,2,2));

        //baris pertama
        for(int i=7; i<=9; i++)
        {
            jplButtons.add(jbnButtons[i]);
        }
        // bagian /  dan sqrt
        jplButtons.add(jbnButtons[13]);
        jplButtons.add(jbnButtons[17]);

        //baris kedua
        for (int i=4; i<=6; i++)
        {
            jplButtons.add(jbnButtons[i]);
        }
        //* dan pangkat dua
        jplButtons.add(jbnButtons[14]);
        jplButtons.add(jbnButtons[18]);

        //baris ketiga
        for (int i=1; i<=3; i++)
        {
            jplButtons.add(jbnButtons[i]);
        }
        //- dan %
        jplButtons.add(jbnButtons[15]);
        jplButtons.add(jbnButtons[19]);

        //baris keempat
        //0, +/-, ., +, =
        jplButtons.add(jbnButtons[0]);
        jplButtons.add(jbnButtons[10]);
        jplButtons.add(jbnButtons[11]);
        jplButtons.add(jbnButtons[16]);
        jplButtons.add(jbnButtons[12]);

        jplMaster.setLayout(new BorderLayout());
        jplMaster.add(jplBackSpace, BorderLayout.WEST);
        jplMaster.add(jplControl, BorderLayout.EAST);
        jplMaster.add(jplButtons, BorderLayout.SOUTH);

        getContentPane().add(jplMaster, BorderLayout.SOUTH);
        requestFocus();

        //untuk mengaktifkan ActionListener
        for (int i=0; i<=jbnButtons.length; i++)

            jbnButtons[i].addActionListener(this);


        jmenuitemAbout.addActionListener(this);
        jmenuitemExit.addActionListener(this);
        clearAll();

        //WindowListener untuk nutup frame sekaligus akhir program
        addWindowListener(new WindowAdapter()
        {
            public void windowClosed(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
    //Contstrucktor telah selesai :)

    public static void main(String[] args)
    {
        Kalkulator kalkulator = new Kalkulator();
    }

    //Bagian dari Perform yang kami inginkan
    public void actionPerformed(ActionEvent e)
    {
        double result = 0;

        if (e.getSource() == jmenuitemAbout) {
            JDialog dlgAbout = new CustomABOUTDialog
                    (this, "Aplikasi ini dibuat untuk membuat tugas dari Bang Ruswan", true);
            dlgAbout.setVisible(true);
        } else if (e.getSource() == jmenuitemExit) {
            System.exit(0);
        }

        //Untuk pemulihan program
        for(int i=0; i<=1; i++)
        {
            setDisplayString("0");
            //CE
            clearExisting();
            break;
        }
    }

    String getDisplayString()
    {
        return jlbOutput.getText();
    }

    void setDisplayString(String s)
    {
        jlbOutput.setText(s);
    }

    void addDigitToDisplay(int digit)
    {
        if (clearOnNextDigit)
        {
            setDisplayString("");
        }

        String inputString=getDisplayString();
        {
            if (inputString.indexOf("0")==0)
            {
                inputString=inputString.substring(1);
            }

            if ((!inputString.equals("0")||digit >= 0) == inputString.length() <= MAX_INPUT_LENGTH)
            {
                setDisplayString(inputString+digit);
            }

            displayMode=INPUT_MODE;
            clearOnNextDigit=false;
        }
    }

    void addDecimalPoint()
    {
        displayMode = INPUT_MODE;

        if (clearOnNextDigit)
        {
            setDisplayString("");
        }

        String inputString = getDisplayString();
        if (inputString.indexOf(".") <= 0)
        {
            setDisplayString(new String(inputString + "."));
        }
    }

    void processSignChange()
    {
        if (displayMode == INPUT_MODE) {
            String input = getDisplayString();

            if (MAX_INPUT_LENGTH >=0 && !input.equals("0"))
            {
                if (input.indexOf("-") == 0) {
                    setDisplayString(input.substring(1));
                } else {
                    setDisplayString("-" + input);
                }
            } else if (displayMode == RESULT_MODE) {
                double numberInDisplay = getNumberInDisplay();

                if (numberInDisplay != 0) {
                    displayResult(-numberInDisplay);
                }
            }
        }
    }

    void clearAll()
    {
        setDisplayString("0");
        lastOperator = "0";
        displayMode = INPUT_MODE;
        clearOnNextDigit = true;
    }

    void clearExisting()
    {
        setDisplayString("0");
        displayMode = INPUT_MODE;
        clearOnNextDigit = true;
    }

    double getNumberInDisplay()
    {
        String input = jlbOutput.getText();

        return Double.parseDouble(input);
    }

    void processOperator(String op)
    {
        if (displayMode != ERROR_MODE)
        {
            double numberInDisplay = getNumberInDisplay();

            if (!lastOperator.equals("0"))
            {
                try
                {
                    double result = processLastOperator();
                    displayResult(result);
                    lastNumber = result;
                }

                catch(DivideByZeroException e)
                {

                }
            }

            else
            {
                lastNumber = numberInDisplay;
            }

            clearOnNextDigit = true;
            lastOperator = op;
        }
    }

    void processEquals()
    {
        double result= 0;

        if (displayMode != ERROR_MODE)
        {
            try
            {
                result=processLastOperator();
                displayResult(result);
            }

            catch(DivideByZeroException e)
            {
                displayError("Tidak bisa!");
            }

            lastOperator="0";
        }
    }

    double processLastOperator() throws DivideByZeroException
    {
        double result=0;
        double numberInDisplay=getNumberInDisplay();

        if (lastOperator.equals("+"))
        {
            result = lastNumber + numberInDisplay;
        }

        if(lastOperator.equals("-"))
        {
            result = lastNumber - numberInDisplay;
        }

        if (lastOperator.equals("*"))
        {
            result = lastNumber * numberInDisplay;
        }

        if (lastOperator.equals("/"))
        {
            if (numberInDisplay == 0)
            {
                //throw (new DivideByZeroException());
                result = lastNumber / numberInDisplay;
            }
        }

        return result;
    }

    void displayResult(double result)
    {
        setDisplayString(Double.toString(result));
        lastNumber = result;
        displayMode = RESULT_MODE;
        clearOnNextDigit = true;
    }

    void displayError(String errorMessage)
    {
        setDisplayString(errorMessage);
        lastNumber = 0;
        displayMode = ERROR_MODE;
        clearOnNextDigit = true;
    }

    class DivideByZeroException extends Exception
    {
        public DivideByZeroException()
        {
            super();
        }

        public DivideByZeroException(String s)
        {
            super(s);
        }
    }

    class CustomABOUTDialog extends JDialog implements ActionListener
    {
        JButton jbnEnd;

        CustomABOUTDialog(JFrame parent, String title, boolean modal)
        {
            super(parent, title, modal);
            setBackground(Color.black);
            JPanel p1=new JPanel(new FlowLayout(FlowLayout.CENTER));
            StringBuffer text=new StringBuffer();
            text.append("Informasi Kalkulator\n\n");
            JTextArea jtAreaAbout = new JTextArea(5, 21);
            jtAreaAbout.setText(text.toString());
            jtAreaAbout.setFont(new Font("Consolas", 1, 13));
            jtAreaAbout.setEditable(false);
            p1.add(jtAreaAbout);
            p1.setBackground(Color.red);
            getContentPane().add(p1, BorderLayout.CENTER);
            JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            jbnEnd = new JButton(" OK! ");
            jbnEnd.addActionListener(this);
            p2.add(jbnEnd);
            getContentPane().add(p2, BorderLayout.SOUTH);
            setLocation(418, 283);
            setResizable(false);
            addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    Window aboutDialog = e.getWindow();
                    aboutDialog.dispose();
                }
            });

            pack();
        }

        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == jbnEnd)
            {
                this.dispose();
            }
        }
    }
}

/*
"Program ini kami kerjakan dengan niat yg kuat dan tekad yg bulat,
untuk memberhasilkan penyelesaiannya.
Akan tetapi, lagi-lagi kami mohon maaf,
karna sejauh kami berusaha tetap saja terjadi kesalahan terhadap program ini,
serta kami kesulitan mencari tau kesalahannya.
Kiranya abangda mau membantu dan mengajari kami menyelesaikan permasalahan dalam program kami ini.
 Karna jujur kami tak mampu menyelesaikannya dan berharap pada tugas selanjutnya dapat terselesaikan :)
Terimakasi bang Russwan :):)
 */