package com.chatting.ejecutable;

import java.io.IOException;

import javax.swing.JFrame;


import com.chatting.controlador.ControladorClientePrivado;
import com.chatting.modelo.UtilidadesCliente;

import com.chatting.vista.VistaPrivado;
import com.chatting.vista.VistaCliente;

public class ClientePrivado {
	private static JFrame ventana;
	private static VistaPrivado vista;
	private static ControladorClientePrivado controlador;
	private static UtilidadesCliente utilidades;
	private static String nickname;
	
	public static void main() throws IOException {
		
		configurarVentana();
		
		iniciarClientePrivado();
		
		
	}
	
	private static void configurarVentana() {
		/* --------------- Inicializaciones --------------- */
        ventana = new JFrame("");
        vista = new VistaPrivado(ventana);
        controlador = new ControladorClientePrivado(vista);
        
        /* --------------- Configuraciones --------------- */
        ventana.setContentPane(vista);
        vista.setControlador(controlador);
        ventana.pack();
        ventana.setResizable(false);
	}
	
	public static void iniciarClientePrivado()  throws IOException{
		
	}
	
	 private static void iniciarChat(String receptor) throws IOException {
	    	ventana.setVisible(true);
			vista.setEnabled(true);
			controlador.setCliente(utilidades);
			utilidades.enviarTCP(receptor);
			ventana.setTitle("Chat Privado con: " + receptor);
	   }
}
