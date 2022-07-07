// Display the results of queries against the bikes table in the bikedb database.
import com.mysql.cj.jdbc.MysqlDataSource;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
Name: Omar Ashry
Course: CNT 4714 Summer 2022
Assignment title: Project 2 – A Two-tier Client-Server Application
Date:  June 26, 2022
*/


public class DisplayQueryApp extends JFrame
{

    private ResultSetTableModel tableModel;
    private TableModel Empty;

    private Connection connect;

    // create ResultSetTableModel and GUI
    public DisplayQueryApp()
    {
        super( "SQL Client App" );


        // create JTable delegate for tableModel
        JTable resultTable = new JTable();
        Empty = new DefaultTableModel();
        resultTable.setGridColor(Color.BLACK);
        resultTable.setBounds(0,320, 670,300);
        resultTable.setBorder(new LineBorder(Color.black, 2));
        resultTable.setEnabled(true);

        // label for JTable
        JLabel tableLabel = new JLabel("SQL Execution Result Window");
        tableLabel.setForeground(Color.blue);
        tableLabel.setBackground(Color.lightGray);
        tableLabel.setOpaque(true);
        tableLabel.setBounds(2,285,200,25);

        // set up JTextArea in which user types queries
        //	queryArea = new JTextArea( 3, 100);
        JTextArea textArea = new JTextArea( " ", 10, 30 );
        textArea.setWrapStyleWord( true );
        textArea.setLineWrap( true );


        //label for scrollPane
        JLabel rightLabel = new JLabel("Enter an SQL Command");
        rightLabel.setForeground(Color.blue);
        JScrollPane scrollPane = new JScrollPane( textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

        // set up JButton for submitting queries
        JButton submitButton = new JButton( "Execute SQL command" );
        submitButton.setBackground(Color.BLUE);
        submitButton.setForeground(Color.yellow);
        submitButton.setBorderPainted(false);
        submitButton.setOpaque(true);

        //set up clear button
        JButton clearButton = new JButton( "clear SQL command" );
        clearButton.setBackground(Color.white);
        clearButton.setForeground(Color.red);
        clearButton.setBorderPainted(false);
        clearButton.setOpaque(true);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });


        // create Box to manage placement of queryArea and
        // submitButton in GUI
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout(10,10));

        //everything in the top right side of window
        JPanel topRightPanel = new JPanel();
        topRightPanel.setBounds(270, 0, 400,235);
        topRightPanel.setBorder(new LineBorder(Color.black, 3));
        topRightPanel.setLayout(new FlowLayout(10));
        topRightPanel.add(rightLabel);
        topRightPanel.add(scrollPane);
        topRightPanel.add(submitButton);
        topRightPanel.add(clearButton);

        //ALL FEATURES FOR LEFT PANEL

        //label for combobox
        JLabel propertyLabel = new JLabel("Property Files");
        propertyLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        propertyLabel.setBackground(Color.lightGray);
        propertyLabel.setOpaque(true);

        //properties drop down combobox
        String[] propertyOptions= {"root.properties", "client.properties"};
        JComboBox properties = new JComboBox(propertyOptions);

        //username
        JTextArea userText = new JTextArea( "", 1, 13 );
        userText.setWrapStyleWord( true );
        userText.setLineWrap( true );
        JScrollPane userPane = new JScrollPane( userText );
        JLabel userLabel = new JLabel(" Username ");
        userLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        userLabel.setBackground(Color.lightGray);
        userLabel.setOpaque(true);

        //password
        JPasswordField passText = new JPasswordField( "", 13 );
        JScrollPane passPane = new JScrollPane( passText );
        JLabel passLabel = new JLabel(" Password ");
        passLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        passLabel.setBackground(Color.lightGray);
        passLabel.setOpaque(true);


        // connection status
        JLabel connectionStatus = new JLabel("No Connection Now");
        connectionStatus.setFont(new Font("Serif", Font.PLAIN, 18));
        connectionStatus.setBorder(new LineBorder(Color.black, 1));
        connectionStatus.setBackground(Color.black);
        connectionStatus.setForeground(Color.red);
        connectionStatus.setOpaque(true);
        //panel for connection status
        JPanel connectionPanel= new JPanel();
        connectionPanel.setBounds(0,240,670,35);
        connectionPanel.add(connectionStatus);
        connectionPanel.setBackground(Color.BLACK);
        connectionPanel.setLayout(new FlowLayout(10,10,10));

        // connect button
        JButton connectButton = new JButton("Connect to database");
        connectButton.setBackground(Color.BLUE);
        connectButton.setForeground(Color.yellow);
        connectButton.setBorderPainted(false);
        connectButton.setOpaque(true);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    if(connect != null)
                        connect.close();
                    connectionStatus.setText("No Connection Now");

                    Properties property = new Properties();
                    FileInputStream fileIn = null;
                    MysqlDataSource dataSource = null;
                    try{
                        fileIn = new FileInputStream((String) Objects.requireNonNull(properties.getSelectedItem()));
                        property.load(fileIn);
                        dataSource = new MysqlDataSource();
                        dataSource.setURL(property.getProperty("MYSQL_DB_URL"));

                        if(!userText.getText().equals("")){
                            dataSource.setUser(userText.getText());
                            dataSource.setPassword(String.valueOf(passText.getPassword()));
                            connect = dataSource.getConnection();
                            connectionStatus.setText("Connected to " + (String)property.getProperty("MYSQL_DB_URL"));
                        } else {
                            connectionStatus.setText("NOT CONNECTED ~~ User creds do not match");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "DataBase error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "DataBase error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //everything in the top left side of window
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setBounds(0, 0, 270,235);
        JLabel leftLabel = new JLabel("Connection details                ");
        leftLabel.setForeground(Color.blue);
        topLeftPanel.setBorder(new LineBorder(Color.black, 3));
        topLeftPanel.setLayout(new FlowLayout(10));
        topLeftPanel.add(leftLabel);
        topLeftPanel.add(propertyLabel);
        topLeftPanel.add(properties);
        topLeftPanel.add(userLabel);
        topLeftPanel.add(userPane);
        topLeftPanel.add(passLabel);
        topLeftPanel.add(passPane);
        topLeftPanel.add(connectButton);


        // clear button for table
        JButton clearTable = new JButton("Clear Result Window");
        clearTable.setBounds(2,630,200,25);
        clearTable.setBackground(Color.yellow);
        clearTable.setForeground(Color.black);
        clearTable.setBorder(new LineBorder(Color.black, 1));
        clearTable.setOpaque(true);
        // action for clearing the table

        clearTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultTable.setModel(Empty);
            }
        });


        // add everything to frame
        mainContainer.add(clearTable);
        mainContainer.add(tableLabel);
        mainContainer.add(topLeftPanel);
        mainContainer.add(connectionPanel);
        mainContainer.add(topRightPanel, BorderLayout.NORTH);
        mainContainer.add(resultTable);


        // create event listener for submitButton

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try{
                    resultTable.setEnabled(true);
                    resultTable.setAutoscrolls(true);
                    tableModel = new ResultSetTableModel(connect, textArea.getText());

                    if(textArea.getText().toUpperCase().contains("SELECT")){
                        resultTable.setModel(tableModel);
                    } else {
                        tableModel.setUpdate(textArea.getText());
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "DataBase error", JOptionPane.ERROR_MESSAGE);

                } catch (ClassNotFoundException notFoundException) {
                    JOptionPane.showMessageDialog(null,"MySQL driver not found", "driver not found", JOptionPane.ERROR_MESSAGE);

                }

            }
        });


        setLayout(null);
        setSize( 670, 700 ); // set window size
        setResizable(false);
        setVisible( true ); // display window

        // dispose of window when user quits application (this overrides
        // the default of HIDE_ON_CLOSE)
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        // ensure database connection is closed when user quits application
        addWindowListener(new WindowAdapter()
                          {
                              // disconnect from database and exit when window has closed
                              public void windowClosed( WindowEvent event )
                              {
                                  tableModel.disconnectFromDatabase();
                                  System.exit( 0 );
                              } // end method windowClosed
                          } // end WindowAdapter inner class
        ); // end call to addWindowListener
    } // end DisplayQueryResults constructor

    // execute application
    public static void main( String args[] )
    {
        new DisplayQueryApp();
    } // end main
} // end class DisplayQueryResults

