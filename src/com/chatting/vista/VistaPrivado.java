package com.chatting.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import com.chatting.controlador.ControladorClientePrivado;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class VistaPrivado extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JFrame ventana;
	WindowListener exitListener;
	
	private JLabel labelUsuarioPrivado;
	private JTextArea chat;
	private JTextField campo;
	private JButton botonEnviar, botonSalir, botonLimpiar, botonScroll;
	DefaultCaret caret;
	
	/* ============================| Constructores |============================ */
	
	public VistaPrivado(JFrame ventana) {
		this.ventana = ventana;
		setLayout(new BorderLayout());
		JPanel panelNorte = new JPanel();
		JPanel panelSur = new JPanel(new GridLayout(1,3));
		
		/* --------------------- Inicializaciones --------------------- */
		chat = new JTextArea();
		campo = new JTextField();
		campo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		botonEnviar = new JButton("Enviar");
		botonLimpiar = new JButton("Limpiar chat");
		JScrollPane scroll = new JScrollPane(chat);
		/* --------------------- Asignaciones --------------------- */
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[]{71, 53, 0};
		gbl_panelNorte.rowHeights = new int[]{23, 0};
		gbl_panelNorte.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelNorte.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelNorte.setLayout(gbl_panelNorte);
		labelUsuarioPrivado = new JLabel("Chat con: ");
		labelUsuarioPrivado.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelUsuarioPrivado.setHorizontalAlignment(SwingConstants.RIGHT);
		labelUsuarioPrivado.setHorizontalTextPosition(SwingConstants.RIGHT);
		GridBagConstraints gbc_labelClientes = new GridBagConstraints();
		gbc_labelClientes.anchor = GridBagConstraints.WEST;
		gbc_labelClientes.insets = new Insets(0, 0, 0, 5);
		gbc_labelClientes.gridx = 0;
		gbc_labelClientes.gridy = 0;
		panelNorte.add(labelUsuarioPrivado, gbc_labelClientes);
		panelSur.add(botonLimpiar);
		panelSur.add(campo);
		panelSur.add(botonEnviar);
 		
		add(panelNorte, BorderLayout.NORTH);
		botonSalir = new JButton("Salir");
		botonSalir.setHorizontalTextPosition(SwingConstants.RIGHT);
		botonSalir.setHorizontalAlignment(SwingConstants.RIGHT);
		botonSalir.setVerticalTextPosition(SwingConstants.TOP);
		botonSalir.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_botonSalir = new GridBagConstraints();
		gbc_botonSalir.anchor = GridBagConstraints.NORTHEAST;
		gbc_botonSalir.gridx = 1;
		gbc_botonSalir.gridy = 0;
		panelNorte.add(botonSalir, gbc_botonSalir);
		add(panelSur, BorderLayout.SOUTH);
		add(scroll, BorderLayout.CENTER);
		
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret = (DefaultCaret)chat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setPreferredSize(new Dimension(480, 360));
		chat.setLineWrap(true);
		
		chat.setEditable(false);
		setEnabled(false);
	}
	
	/* ============================| Metodos |============================ */
	
	public String getTextoCampo() {
		return "/PRIVATE" +  campo.getText().toString();
	}
	
	public void vaciarTextoCampo() {
		campo.setText("");
	}
	
	public void limpiarChat() {
		chat.setText("");
	}
	
	public void setEnabled(boolean activado) {
		campo.setEnabled(activado);
		chat.setEnabled(activado);
		botonEnviar.setEnabled(activado);
		botonLimpiar.setEnabled(activado);
		botonSalir.setEnabled(activado);
		botonScroll.setEnabled(activado);
	}
	
	public void setControlador(ControladorClientePrivado l) {
		botonEnviar.setActionCommand("enviar");
		campo.setActionCommand("enviar");
		botonSalir.setActionCommand("salir");
		botonLimpiar.setActionCommand("limpiar");
		botonScroll.setActionCommand("scroll");
		
		botonEnviar.addActionListener(l);
		campo.addActionListener(l);
		botonSalir.addActionListener(l);
		botonLimpiar.addActionListener(l);
		botonScroll.addActionListener(l);
		
		// Controlador de cierre de ventana (para que se desconecte bien al cerrar)
		exitListener = new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		        l.salir();
		        System.exit(0);
		    }
		};
		ventana.addWindowListener(exitListener);
		
	}	
	
}