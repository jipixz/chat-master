package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import com.chatting.Constantes;
import java.awt.Font;
import javax.swing.border.BevelBorder;

/**
 * Ventana del servidor.
 * 
 *
 */
public class VistaServidor extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelConexiones, labelPuerto;
	private JButton botonSalir, botonScroll;
	private JTextArea texto;
	
	DefaultCaret caret;
	
	/* ============================| Constructores |============================ */
	
	public VistaServidor() {
		setLayout(new BorderLayout());
		JPanel panelNorte = new JPanel(new GridLayout(1, 2));
		JPanel panelSur = new JPanel(new GridLayout(1, 2));
		
		/* --------------------- Inicializaciones --------------------- */
		
		labelConexiones = new JLabel("Clientes conectados: 0/"+Constantes.MAX_CONEXIONES);
		labelConexiones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelPuerto = new JLabel("Puerto: " + Constantes.PUERTO_SERVIDOR);
		labelPuerto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		botonSalir = new JButton("Apagar servidor");
		botonSalir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		botonScroll = new JButton("Alternar Auto-scroll");
		botonScroll.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		texto = new JTextArea();
		texto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		texto.setEditable(false);
		JScrollPane scroll = new JScrollPane(texto);
		
		/* --------------------- Asignaciones --------------------- */
		panelNorte.add(labelConexiones);
		panelNorte.add(labelPuerto);
		panelSur.add(botonScroll);
		panelSur.add(botonSalir);
		this.add(panelNorte, BorderLayout.NORTH);
		this.add(panelSur, BorderLayout.SOUTH);
		add(scroll, BorderLayout.CENTER);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret = (DefaultCaret)texto.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		texto.setLineWrap(true);
		
		setPreferredSize(new Dimension(650, 360));
	}
	
	/* ============================| Metodos |============================ */
	
	public void setControlador(ActionListener l) {
		botonSalir.setActionCommand("apagar");
		botonScroll.setActionCommand("scroll");
		botonSalir.addActionListener(l);
		botonScroll.addActionListener(l);
	}
	
	public void setClientesConectados(int clientesConectados) {
		labelConexiones.setText("Clientes conectados: " + clientesConectados+"/"+Constantes.MAX_CONEXIONES);
	}
	
	public void addText(String linea) {
		texto.append(linea+ "\n");
	}
	
	public void apagar() {
		botonSalir.setEnabled(false);
		texto.setEnabled(false);
		botonScroll.setEnabled(false);
		labelConexiones.setText("Servidor apagado.");
		labelPuerto.setText("Puerto: -");
	}
	
	public void alternarAutoScroll() {
		if(caret.getUpdatePolicy() != DefaultCaret.NEVER_UPDATE)
			caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		else {
			texto.setCaretPosition(texto.getDocument().getLength() );
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}
	
}
