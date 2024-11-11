import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App extends JFrame {
    private JPanel pnlMain;
    private JRadioButton rbCustomer;
    private JRadioButton rbClerk;
    private JRadioButton rbManager;
    private JTextField tfName;
    private JTextArea taPersons;
    private JButton btnSave;
    private JTextField tfAge;
    private JTextField tfMonths;
    private JTextField tfSalary;
    private JButton btnClear;
    private JTextField tfLoad;
    private JButton btnLoad;
    private JButton btnSayHi;
    private JButton btnSavePerson;
    private JButton btnLoadPerson;
    private JButton btnReward;

    private List<Person> persons;

    // customized fields by me
    private JRadioButton[] rbPersons = { rbCustomer, rbClerk, rbManager };
    ButtonGroup btnGPersons = new ButtonGroup();


    public App() {
        persons = new ArrayList<>();
        btnGPersons.add(rbCustomer);
        btnGPersons.add(rbClerk);
        btnGPersons.add(rbManager);

        // TODO add implementations for all milestones here

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // create person
                for (int i = 0; i < rbPersons.length; i++) {
                    if (rbPersons[i].isSelected()) {
                        Person p = (createPerson(i));
                        if (p == null) break;

                        // add person to list
                        persons.add(p);

                        // show created person
                        int n = persons.size();
                        taPersons.append(n + ". " + persons.get(n-1).getClass().getSimpleName() + " - " + persons.get(n -1).getName() + " (" + persons.get(n -1).getAge() + ")\n");

                        // reset fields
                        resetFields();
                    }
                }



            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                taPersons.setText("");
            }
        });

        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int n = Integer.parseInt(tfLoad.getText());
                    Person p = persons.get(n -1);

                    tfName.setText(p.getName());
                    tfAge.setText(String.valueOf(p.getAge()));
                    tfMonths.setText("");
                    tfSalary.setText("");

                    if (p instanceof Employee) {
                        tfMonths.setText(String.valueOf(((Employee)p).getMonths_worked()));
                        tfSalary.setText(String.valueOf(((Employee)p).getSalary()));
                    }

                    if (p instanceof Customer) rbCustomer.setSelected(true);
                    else if (p instanceof Clerk) rbClerk.setSelected(true);
                    else if (p instanceof Manager) rbManager.setSelected(true);

                } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid load input");
                }
                finally {
                    tfLoad.setText("");
                }
            }
        });

    }

    public static void main(String[] args) {
        // add here how to make GUI visible
        App app = new App();
        app.setContentPane(app.pnlMain);
        app.setSize(500, 500);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.setResizable(false);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
        app.setTitle("I love CS <3");
    }

    static void giveReward(int n) {

    }

    // customized methods by me
    private Person createPerson(int i) {

        // obtain input fields
       String name;
       int age;
       int months;
       double salary;

        name = validateName();
        age = validateAge();

        if (i == 0) {
            if (name.isBlank() || age == -1) return null;

            return new Customer(name, age);
        }
        else {
            months = validateMonths();
            salary = validateSalary();

            if (name.isBlank() || age == -1 || months == -1 || salary == -1) return null;

            if (i == 1) {
                return new Clerk(name, age, months, salary);
            }
            else if (i == 2) {
                return new Manager(name, age, months, salary);
            }
        }

        return null;
    }

    private boolean errorCheck(String var, String input) {

        if (input.isBlank()) {
            JOptionPane.showMessageDialog(null, var + " field is empty");
            return true;
        }

        for (char c : input.toCharArray()) {
            if (c == '-') {
                JOptionPane.showMessageDialog(null, var + " is a negative integer");
                return true;
            }

            if (!Character.isDigit(c)) {
                JOptionPane.showMessageDialog(null, var + " is a String input");
                return true;
            }
        }
        return false;
    }
    private String validateName() {

        if (tfName.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Name field is blank");
            return "";
        }

        return tfName.getText();
    }
    private int validateAge() {
        if (errorCheck("Age", tfAge.getText())) {
            tfAge.setText("");
            return -1;
        }

        return Integer.parseInt(tfAge.getText());
    }
    private int validateMonths() {
        if (errorCheck("Months", tfMonths.getText())) {
            tfMonths.setText("");
            return -1;
        }

        return Integer.parseInt(tfMonths.getText());
    }
    private double validateSalary() {
        if (errorCheck("Salary", tfSalary.getText())) {
            tfSalary.setText("");
            return -1;
        }

        return Double.parseDouble(tfSalary.getText());
    }

    private void resetFields() {
        btnGPersons.clearSelection();
        tfName.setText("");
        tfAge.setText("");
        tfMonths.setText("");
        tfSalary.setText("");
        tfLoad.setText("");
    }

}