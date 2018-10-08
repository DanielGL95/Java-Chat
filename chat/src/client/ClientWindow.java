package client;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class ClientWindow {
    private JFrame frame;
    private JTextField textField;

    private static JTextArea textArea = new JTextArea();

    private Client client;

    public static void main(String[] args) {
        try {
            EventQueue.invokeAndWait(() -> {
                try {
                    ClientWindow window = new ClientWindow();
                    window.frame.setVisible(true);
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private ClientWindow() {
        initialize();
        String name = JOptionPane.showInputDialog("Enter your name");
        
        client = new Client(name, "localhost", 3000);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setResizable(true);
        frame.setBounds(250, 300, 330, 550);
        frame.setTitle("Chat system");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();


        textArea.setEditable(false);
        textArea.setBackground(Color.getHSBColor(141, 74, 98));
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane);

        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        panel.setBackground(Color.darkGray);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        textField = new JTextField();
        Font current = textField.getFont();
        textField.setFont(current.deriveFont(Font.ITALIC));


        textField.setName("messageField");
        panel.add(textField);

        textField.setColumns(20);
        textField.setName("messageField");
        panel.add(textField);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(e -> {
            if (!textField.getText().equals("")) {
                client.send(textField.getText());
                textField.setText("");
            }
        });
        btnSend.setBackground(Color.PINK);
        panel.add(btnSend);



    }

    static void printToConsole(String message) {
        Font current = textArea.getFont();

        if (message.startsWith("User")){

            textArea.setFont(current.deriveFont(Font.BOLD));
            textArea.setText(textArea.getText() + message + "\n\n");
            return;
        }

        Font font1 = new Font("Courier", Font.ITALIC, 12);
        textArea.setFont(font1);
        textArea.setText(textArea.getText() + message + "\n");


    }
}
