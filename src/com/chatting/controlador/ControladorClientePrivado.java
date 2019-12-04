package com.chatting.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.chatting.Constantes;
import com.chatting.modelo.UtilidadesCliente;
import com.chatting.vista.VistaPrivado;

/**
 * Clase que tratara los eventos sobre los botones en la vista.
 * 
 *
 */
public class ControladorClientePrivado implements ActionListener {

	private UtilidadesCliente cliente;
	private VistaPrivado vista;
	
	public ControladorClientePrivado(VistaPrivado vista) {
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
				vista.vaciarTextoCampo();
			break;
			case "limpiar":
				vista.limpiarChat();
			break;
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
		vista.setEnabled(false);
		return 0;
	}
	


}

