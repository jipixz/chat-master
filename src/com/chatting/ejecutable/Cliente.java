package com.chatting.ejecutable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chatting.controlador.ControladorCliente;
import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaCliente;

import com.chatting.controlador.ControladorClientePrivado;
import com.chatting.vista.VistaPrivado;

/**
 *  Clase principal del usuario cliente de chat.
 * 
 *
 */
public class Cliente {
	
	private static JFrame ventana;
	private static VistaCliente vista;
	//private static VistaPrivado vistaprivado;
	private static ControladorCliente controlador;
	//private static ControladorClientePrivado controladorprivado;
	private static Socket cliente;
	private static UtilidadesCliente utilidades;
	private static String nickname;
	
	/* ======================== Main ========================== */

	public static void main(String[] args) {
		
		configurarVentana();
		
		try {
			
			iniciarCliente();
			
			while(!cliente.isClosed()) {
				utilidades.handleMessage();
			}
			
			while(true) {}
		} catch (SocketTimeoutException e) {
			vista.setEnabled(false);
			JOptionPane.showMessageDialog(ventana, "Conexión perdida (connection timeout)", "Error de conexión", JOptionPane.ERROR_MESSAGE);
		} catch (SocketException e) {
			vista.setEnabled(false);
			JOptionPane.showMessageDialog(ventana, "Servidor no alcanzado. Apagado o fuera de covertura.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			// El mensaje que saldra es servidor lleno
			JOptionPane.showMessageDialog(ventana, e.getMessage(), "Error de conexion", JOptionPane.ERROR_MESSAGE);
		} 
		
	}
	
	/* ======================== Metodos ========================== */

	private static void configurarVentana() {
		/* --------------- Inicializaciones --------------- */
        ventana = new JFrame("");
        vista = new VistaCliente(ventana);
        controlador = new ControladorCliente(vista);
        
        /* --------------- Configuraciones --------------- */
        ventana.setContentPane(vista);
        vista.setControlador(controlador);
        ventana.pack();
        ventana.setResizable(false);
	}
    
	/**
	 * Lanza la ventana e inicia la conexion con el servidor.
	 * @throws NumberFormatException
	 * @throws IOException
	 */
    public static void iniciarCliente() throws NumberFormatException, IOException {
    	String host = JOptionPane.showInputDialog(ventana, "Introduce la ip del host (nada = localhost)", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	String puerto = JOptionPane.showInputDialog(ventana, "Introduce el puerto (nada = 42455)", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	nickname = JOptionPane.showInputDialog(ventana, "Introduce tu nickname", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	
    	if(puerto.equals(""))
    		puerto = "42455";
    	if(host.equals(""))
    		host = "localhost";
		
    	
    	
    	try {
    		if(nickname.equals(""))
    			throw new IOException("Nickname no valido.");
    		// Conectamos estableciendo un TIMEOUT
    		cliente = new Socket();
    		cliente.connect(new InetSocketAddress(host, Integer.parseInt(puerto)), 5000);
    		utilidades = new UtilidadesCliente(cliente, vista, controlador);
    		
    		// Si no esta lleno entramos, si esta lleno lanzaremos el error.
    		if(utilidades.recibirTCP().trim().equals("aceptado")) {
    			iniciarChat(nickname);
    		}else {
    			utilidades = null;
    			throw new IOException("Servidor lleno");
    		}
    	}catch(NumberFormatException e) {
    		JOptionPane.showMessageDialog(ventana, "Debes introducir un numero de puerto valido.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    /**
     * Activa la ventana hacemos asociaciones correspondientes al conectar por primera vez.
     * @param nick
     * @throws IOException
     */
    private static void iniciarChat(String nick) throws IOException {
    	ventana.setVisible(true);
		vista.setEnabled(true);
		controlador.setCliente(utilidades);
		utilidades.enviarTCP(nick);
		ventana.setTitle("Chat - Usuario: " + nickname);
		vista.lblBienvenido.setText("Bienvenido: " + nickname);
    }
    
    /*public static void configurarVentanaPrivado(){
		/* --------------- Inicializaciones --------------- 
        ventana = new JFrame("");
        vistaprivado = new VistaPrivado(ventana);
        controladorprivado = new ControladorClientePrivado(vistaprivado);
        
        /* --------------- Configuraciones --------------- 
        ventana.setContentPane(vistaprivado);
        vistaprivado.setControlador(controladorprivado);
        ventana.pack();
        ventana.setResizable(false);
    }
    
    public static void iniciarChatPrivado(String emisor, String receptor)  throws IOException{
    	ventana.setVisible(true);
		vistaprivado.setEnabled(true);
		controladorprivado.setCliente(utilidades);
		utilidades.enviarTCP(emisor);
		ventana.setTitle("Chat privado con: " + receptor);
    }*/
    
    
}
