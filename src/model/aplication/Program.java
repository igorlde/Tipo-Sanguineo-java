package model.aplication;

import model.entites.Register;

public class Program {
	    public static void main(String[] args) {
	        Register register = new Register();

	        // Cadastrando doador
	        register.registerPeople("João Silva", "Masculino", 30, "Rua A, 123", "O+");

	        // Listando tipos sanguíneos disponíveis
	        register.listbloodTypeAvailable();
	    }
	}


