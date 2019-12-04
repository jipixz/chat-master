package com.chatting.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.chatting.Constantes;
import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaCliente;

/**
 * Clase que tratara los eventos sobre los botones en la vista.
 * 
 *
 */
public class ControladorCliente implements ActionListener {

	private UtilidadesCliente cliente;
	private VistaCliente vista;
	
	public ControladorCliente(VistaCliente vista) {
		this.vista = vista;
	}

	public void setCliente(UtilidadesCliente cliente) {
		this.cliente = cliente;
	}
	
	/**
	 * Interpreta las acciones realizadas sobre el cliente.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "salir":
				salir();
			break;
			case "enviar":
				cliente.enviarTCP(vista.getTextoCampo());
				Boolean temp = vista.isPrivateChat; // initial state for private
				vista.isPrivateChat = false; // set off to get normal message
				vista.appendChat(vista.getTextoCampo());
				vista.isPrivateChat = temp; // set the current state again
				vista.vaciarTextoCampo();
			break;
			case "listado":
				cliente.enviarTCP(Constantes.CODIGO_LISTAR);
			break;
			case "limpiar":
				vista.limpiarChat();
			break;
			case "scroll":
				vista.alternarAutoScroll();
			break;
			/*case "privado":
				privado();
			break;*/
			default:
			break;
		}
	}
	
	/**
	 * Metodo que desconecta y apaga el cliente.
	 * @return
	 */
	public int salir() {
		
		cliente.enviarTCP(Constantes.CODIGO_SALIDA);
		cliente.cerrarConexion();
		vista.setClientes("Unknown");
		//vista.setUsuarios();
		vista.addText("<CLIENT> Has abandonado la sala de chat.");
		vista.setEnabled(false);
		return 0;
	}
	


}
