package model.graphic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.entites.Register;
import model.exception.DBException;

public class RigisterGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private Register registerService;
	public RigisterGUI() {
		registerService = new Register();

        setTitle("Cadastro de doadores de sangue.");
        setSize(400, 400); // Aumentei o tamanho para acomodar a lista
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Campos de entrada
        JTextField nameField = new JTextField(20);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Masculino", "Feminino", "Outro"});
        JTextField ageField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField bloodTypeField = new JTextField(20);
        JTextField phoneNumberField = new JTextField(20);

        // Botões
        JButton registerButton = new JButton("Cadastrar");
        JButton listButton = new JButton("Listar Doador");
        JButton deleteButton = new JButton("Excluir Doador");

        // Campo para exibir a lista de doadores
        JTextArea donorListArea = new JTextArea(10, 30);  // 10 linhas e 30 colunas
        donorListArea.setEditable(false);  // Não permitirá edição direta no JTextArea
        JScrollPane scrollPane = new JScrollPane(donorListArea);  // Para permitir rolar a lista
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Barra de rolagem sempre visível

        // Layout dos componentes
        add(new JLabel("Nome:"));
        add(nameField);
        add(new JLabel("Gênero:"));
        add(genderComboBox);
        add(new JLabel("Idade:"));
        add(ageField);
        add(new JLabel("Endereço:"));
        add(addressField);
        add(new JLabel("Tipo Sanguíneo:"));
        add(bloodTypeField);
        add(new JLabel("Telefone:"));
        add(phoneNumberField);
        add(registerButton);
        add(listButton);
        add(deleteButton);
        add(scrollPane);  // Adiciona o JScrollPane para mostrar a lista de doadores

        // Ação do botão cadastrar
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    String gender = (String) genderComboBox.getSelectedItem();
                    String address = addressField.getText().trim();
                    String bloodType = bloodTypeField.getText().trim();
                    String phoneNumber = phoneNumberField.getText().trim();

                    // Validação dos campos
                    if (name.isEmpty() || gender.isEmpty() || address.isEmpty() || bloodType.isEmpty() || phoneNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int age = Integer.parseInt(ageField.getText().trim());  // Pode lançar exceção se idade não for numérica
                    registerService.registerPeople(name, gender, age, address, bloodType, phoneNumber);
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Idade inválida! Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão listar doador
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Captura a lista de doadores e exibe no JTextArea
                    StringBuilder donorList = new StringBuilder();
                    String donors = registerService.getDonorsList(); // Método que retorna a lista de doadores em formato String
                    donorList.append(donors);
                    donorListArea.setText(donorList.toString()); // Atualiza o JTextArea com a lista de doadores
                } catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão excluir doador
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    String address = addressField.getText().trim();
                    String phoneNumber = phoneNumberField.getText().trim();

                    if (name.isEmpty() || address.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nome e endereço são obrigatórios para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    registerService.deletePerson(name, address, phoneNumber.isEmpty() ? null : phoneNumber);
                    JOptionPane.showMessageDialog(null, "Doador excluído com sucesso.");
                } catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}