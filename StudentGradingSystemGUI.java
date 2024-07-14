import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class User {
    String username;
    String password;
    String role;

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

class Student extends User {
    Map<String, Integer> grades = new HashMap<>();
    Map<String, Boolean> requestedSupplementary = new HashMap<>();

    Student(String username, String password) {
        super(username, password, "student");
    }
}

class Lecturer extends User {
    Lecturer(String username, String password) {
        super(username, password, "lecturer");
    }

    String calculateStatus(int marks) {
        if (marks >= 75) {
            return "Pass Distinction";
        } else if (marks >= 50) {
            return "Pass";
        } else if (marks >= 47) {
            return "Pass Condoned";
        } else if (marks >= 45) {
            return "Supplementary";
        } else {
            return "Fail";
        }
    }
}

class Administrator extends User {
    Administrator(String username, String password) {
        super(username, password, "administrator");
    }

    void allowSupplementary(Student student, String module) {
        student.requestedSupplementary.put(module, true);
    }

    void declineSupplementary(Student student, String module) {
        student.requestedSupplementary.put(module, false);
    }
}

public class StudentGradingSystemGUI {
    static Map<String, User> users = new HashMap<>();
    static JFrame frame = new JFrame("Student Grading System");

    public static void main(String[] args) {
        initializeUsers();
        showLoginScreen();
    }
//these are all the users and theier credintials, you can modify them however you wish
    static void initializeUsers() {
        users.put("mulalo.ndou@mvula.univen.ac.za", new Student("mulalo.ndou@mvula.univen.ac.za", "ndou"));
        users.put("daki@mvula.univen.ac.za", new Student("daki@mvula.univen.ac.za", "daki"));
        users.put("Lufuno@mvula.univen.ac.za", new Student("Lufuno@mvula.univen.ac.za", "lufuno"));
        users.put("florance@mvula.univen.ac.za", new Student("florance@mvula.univen.ac.za", "florance"));
        users.put("admin", new Administrator("admin", "aDm!nI$tR@t0r"));
        users.put("Lecture@univen.ac.za", new Lecturer("Lecture@univen.ac.za", "Lecture@2024"));

        // Initializing themarks of all the students and thier modules as well as thier scores
        ((Student) users.get("mulalo.ndou@mvula.univen.ac.za")).grades.put("com2301", 55);
        ((Student) users.get("mulalo.ndou@mvula.univen.ac.za")).grades.put("com2202", 47);
        ((Student) users.get("daki@mvula.univen.ac.za")).grades.put("com2301", 46);
        ((Student) users.get("daki@mvula.univen.ac.za")).grades.put("com2202", 52);
        ((Student) users.get("Lufuno@mvula.univen.ac.za")).grades.put("com2301", 44);
        ((Student) users.get("Lufuno@mvula.univen.ac.za")).grades.put("com2202", 51);
    }
//Login UI
    static void showLoginScreen() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
//all the components
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginButton);
//you can modify the size and the look however you see fit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                User user = authenticate(username, password);
                if (user != null) {
                    frame.getContentPane().removeAll();
                    frame.repaint();
                    if (user.role.equals("student")) {
                        showStudentScreen((Student) user);
                    } else if (user.role.equals("lecturer")) {
                        showLecturerScreen((Lecturer) user);
                    } else if (user.role.equals("administrator")) {
                        showAdminScreen((Administrator) user);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials. Try again.");
                }
            }
        });
    }
//Checking if the credintials  valid
    static User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            return user;
        }
        return null;
    }

    static void showStudentScreen(Student student) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Module", "Score", "Status"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);

        for (Map.Entry<String, Integer> entry : student.grades.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue(), new Lecturer("", "").calculateStatus(entry.getValue())});
        }

        JButton requestSupplementaryButton = new JButton("Request Supplementary");
        JButton logoutButton = new JButton("Logout");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(requestSupplementaryButton);
        buttonPanel.add(logoutButton);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.revalidate();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                showLoginScreen();
            }
        });

        requestSupplementaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean requested = false;
                for (Map.Entry<String, Integer> entry : student.grades.entrySet()) {
                    if (entry.getValue() >= 45 && entry.getValue() <= 47) {
                        student.requestedSupplementary.put(entry.getKey(), true);
                        requested = true;
                    }
                }
                if (requested) {
                    JOptionPane.showMessageDialog(frame, "Supplementary request(s) sent.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No eligible marks for supplementary request.");
                }
            }
        });
    }

    static void showLecturerScreen(Lecturer lecturer) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel studentLabel = new JLabel("Select Student:");
        JComboBox<String> studentDropdown = new JComboBox<>();

        for (User user : users.values()) {
            if (user.role.equals("student")) {
                studentDropdown.addItem(user.username);
            }
        }

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Module", "Score"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);

        studentDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudent = (String) studentDropdown.getSelectedItem();
                Student student = (Student) users.get(selectedStudent);
                model.setRowCount(0); // Clear existing rows
                for (Map.Entry<String, Integer> entry : student.grades.entrySet()) {
                    model.addRow(new Object[]{entry.getKey(), entry.getValue()});
                }
            }
        });

        JButton updateButton = new JButton("Update");
        JButton logoutButton = new JButton("Logout");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String module = (String) model.getValueAt(selectedRow, 0);
                    String newScoreStr = JOptionPane.showInputDialog(frame, "Enter new score for " + module + ":");
                    if (newScoreStr != null && !newScoreStr.trim().isEmpty()) {
                        int newScore = Integer.parseInt(newScoreStr.trim());
                        String selectedStudent = (String) studentDropdown.getSelectedItem();
                        Student student = (Student) users.get(selectedStudent);
                        student.grades.put(module, newScore);
                        model.setValueAt(newScore, selectedRow, 1);
                        frame.repaint();
                    }
                }
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        inputPanel.add(new JLabel("Select Student:"));
        inputPanel.add(studentDropdown);
        inputPanel.add(updateButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logoutButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.revalidate();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                showLoginScreen();
            }
        });
    }

    static void showAdminScreen(Administrator admin) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Student", "Module", "Score", "Request Status"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);

        for (User user : users.values()) {
            if (user.role.equals("student")) {
                Student student = (Student) user;
                for (Map.Entry<String, Boolean> entry : student.requestedSupplementary.entrySet()) {
                    if (entry.getValue() == true) {
                        model.addRow(new Object[]{student.username, entry.getKey(), student.grades.get(entry.getKey()), "Pending"});
                    }
                }
            }
        }

        JButton acceptButton = new JButton("Accept");
        JButton declineButton = new JButton("Decline");
        JButton logoutButton = new JButton("Logout");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.add(declineButton);
        buttonPanel.add(logoutButton);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.revalidate();

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String studentUsername = (String) model.getValueAt(selectedRow, 0);
                    String module = (String) model.getValueAt(selectedRow, 1);
                    Student student = (Student) users.get(studentUsername);
                    admin.allowSupplementary(student, module);
                    model.setValueAt("Accepted", selectedRow, 3);
                    System.out.println("Accepted");
                }
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String studentUsername = (String) model.getValueAt(selectedRow, 0);
                    String module = (String) model.getValueAt(selectedRow, 1);
                    Student student = (Student) users.get(studentUsername);
                    admin.declineSupplementary(student, module);
                    model.setValueAt("Declined", selectedRow, 3);
                    System.out.println("Declined");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                showLoginScreen();
            }
        });
    }
}
